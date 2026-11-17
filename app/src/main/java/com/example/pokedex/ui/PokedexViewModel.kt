package com.example.pokedex.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.PokedexRepository
import com.example.pokedex.data.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class CaptureFilter(val label: String) {
    ALL("Todos"), CAPTURED("Capturados"), MISSING("Pendientes")
}

class PokedexViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = PokedexRepository(app)

    val allPokemon: List<Pokemon> = repository.loadPokemon()

    val captured: StateFlow<Set<Int>> = repository.captured
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    val query = MutableStateFlow("")
    val filter = MutableStateFlow(CaptureFilter.ALL)

    // lista que se ve en pantalla: mezcla la búsqueda, el filtro y los capturados
    val visible: StateFlow<List<Pokemon>> = combine(query, filter, captured) { q, f, caught ->
        allPokemon.filter { p ->
            val matchesText = q.isBlank() ||
                p.name.contains(q.trim(), ignoreCase = true) ||
                p.id.toString().padStart(3, '0').contains(q.trim())
            val matchesFilter = when (f) {
                CaptureFilter.ALL -> true
                CaptureFilter.CAPTURED -> p.id in caught
                CaptureFilter.MISSING -> p.id !in caught
            }
            matchesText && matchesFilter
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), allPokemon)

    fun setCaptured(id: Int, value: Boolean) {
        viewModelScope.launch { repository.setCaptured(id, value) }
    }
    fun byId(id: Int): Pokemon = allPokemon.first { it.id == id }
}
