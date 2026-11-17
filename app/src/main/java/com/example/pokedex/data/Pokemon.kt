package com.example.pokedex.data
import kotlinx.serialization.Serializable

@Serializable
data class Stats(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<String>,
    val height: Double,
    val weight: Double,
    val baseExperience: Int,
    val stats: Stats,
    val descriptionEs: String,
    val descriptionEn: String,
    val image: String
)
