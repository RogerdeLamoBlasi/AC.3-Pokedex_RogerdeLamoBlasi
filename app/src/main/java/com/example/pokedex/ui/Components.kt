package com.example.pokedex.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TypeStyle(val label: String, val color: Color)

fun typeStyle(type: String): TypeStyle = when (type) {
    "normal" -> TypeStyle("Normal", Color(0xFFA8A77A))
    "fighting" -> TypeStyle("Lucha", Color(0xFFC22E28))
    "flying" -> TypeStyle("Volador", Color(0xFFA98FF3))
    "poison" -> TypeStyle("Veneno", Color(0xFFA33EA1))
    "ground" -> TypeStyle("Tierra", Color(0xFFE2BF65))
    "rock" -> TypeStyle("Roca", Color(0xFFB6A136))
    "bug" -> TypeStyle("Bicho", Color(0xFFA6B91A))
    "ghost" -> TypeStyle("Fantasma", Color(0xFF735797))
    "steel" -> TypeStyle("Acero", Color(0xFFB7B7CE))
    "fire" -> TypeStyle("Fuego", Color(0xFFEE8130))
    "water" -> TypeStyle("Agua", Color(0xFF6390F0))
    "grass" -> TypeStyle("Planta", Color(0xFF7AC74C))
    "electric" -> TypeStyle("Eléctrico", Color(0xFFF7D02C))
    "psychic" -> TypeStyle("Psíquico", Color(0xFFF95587))
    "ice" -> TypeStyle("Hielo", Color(0xFF96D9D6))
    "dragon" -> TypeStyle("Dragón", Color(0xFF6F35FC))
    "dark" -> TypeStyle("Siniestro", Color(0xFF705746))
    "fairy" -> TypeStyle("Hada", Color(0xFFD685AD))
    else -> TypeStyle(type.replaceFirstChar { it.uppercase() }, Color.Gray)
}

@Composable
fun TypeChip(type: String, small: Boolean = false) {
    val style = typeStyle(type)
    Surface(color = style.color, shape = RoundedCornerShape(50)) {
        Text(
            text = style.label,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = if (small) 11.sp else 14.sp,
            modifier = Modifier.padding(
                horizontal = if (small) 8.dp else 14.dp,
                vertical = if (small) 2.dp else 4.dp
            )
        )
    }
}

@Composable
fun pokemonPainter(name: String): Painter {
    val context = LocalContext.current
    val resId = remember(name) { context.resources.getIdentifier(name, "drawable", context.packageName) }
    return painterResource(resId)
}
