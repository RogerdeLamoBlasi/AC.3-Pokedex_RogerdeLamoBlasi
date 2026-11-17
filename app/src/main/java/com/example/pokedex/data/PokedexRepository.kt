package com.example.pokedex.data
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "pokedex")

class PokedexRepository(context: Context) {

    private val appContext = context.applicationContext
    private val capturedKey = stringSetPreferencesKey("captured_ids")
    private val json = Json { ignoreUnknownKeys = true }

    fun loadPokemon(): List<Pokemon> =
        appContext.assets.open("pokedex.json").bufferedReader().use {
            json.decodeFromString(ListSerializer(Pokemon.serializer()), it.readText())
        }

    val captured: Flow<Set<Int>> = appContext.dataStore.data.map { prefs ->
        prefs[capturedKey].orEmpty().mapNotNull { it.toIntOrNull() }.toSet()
    }

    suspend fun setCaptured(id: Int, value: Boolean) {
        appContext.dataStore.edit { prefs ->
            val current = prefs[capturedKey].orEmpty()
            prefs[capturedKey] = if (value) current + id.toString() else current - id.toString()
        }
    }
}
