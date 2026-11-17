package com.example.pokedex.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedex.data.Pokemon
import java.util.Locale

@Composable
fun DetailScreen(vm: PokedexViewModel, id: Int, onBack: () -> Unit) {
    val p = remember(id) { vm.byId(id) }
    val captured by vm.captured.collectAsState()
    val isCaptured = id in captured
    val mainColor = typeStyle(p.types.first()).color
    val isSpanish = Locale.getDefault().language == "es"

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(mainColor, mainColor.copy(alpha = 0.55f))))
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                }
                Spacer(Modifier.weight(1f))
                Text(
                    "Nº %03d".format(p.id),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }

            Image(
                painter = pokemonPainter(p.image),
                contentDescription = p.name,
                modifier = Modifier.size(220.dp)
            )

            Text(
                p.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                p.types.forEach { TypeChip(it) }
            }

            Spacer(Modifier.height(20.dp))
        }

        Column(
            Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Text(
                    if (isSpanish) p.descriptionEs else p.descriptionEn,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                InfoBox("Altura", "%.1f m".format(p.height), Modifier.weight(1f))
                InfoBox("Peso", "%.1f kg".format(p.weight), Modifier.weight(1f))
                InfoBox("Exp. base", p.baseExperience.toString(), Modifier.weight(1f))
            }

            StatsCard(p, mainColor)

            CaptureButton(isCaptured) { vm.setCaptured(id, !isCaptured) }

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun InfoBox(label: String, value: String, modifier: Modifier = Modifier) {
    Card(shape = RoundedCornerShape(16.dp), modifier = modifier) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun StatsCard(p: Pokemon, color: Color) {
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Estadísticas base", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            StatBar("PS", p.stats.hp, color)
            StatBar("Ataque", p.stats.attack, color)
            StatBar("Defensa", p.stats.defense, color)
            StatBar("At. especial", p.stats.specialAttack, color)
            StatBar("Def. especial", p.stats.specialDefense, color)
            StatBar("Velocidad", p.stats.speed, color)
        }
    }
}

@Composable
private fun StatBar(label: String, value: Int, color: Color) {
    val fraction = (value / 255f).coerceAtMost(1f)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, modifier = Modifier.width(100.dp), style = MaterialTheme.typography.bodyMedium)
        Text(value.toString(), modifier = Modifier.width(40.dp), fontWeight = FontWeight.Bold)
        Box(
            Modifier
                .weight(1f)
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color.copy(alpha = 0.2f))
        ) {
            Box(
                Modifier
                    .fillMaxWidth(fraction)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(5.dp))
                    .background(color)
            )
        }
    }
}

@Composable
private fun CaptureButton(isCaptured: Boolean, onToggle: () -> Unit) {
    val container = if (isCaptured) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
    Button(
        onClick = onToggle,
        colors = ButtonDefaults.buttonColors(containerColor = container, contentColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            if (isCaptured) "¡Capturado!" else "Capturar",
            fontWeight = FontWeight.SemiBold
        )
    }
}
