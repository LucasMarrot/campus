package fr.cnam.campus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.cnam.campus.ui.theme.CampusTheme
import androidx.compose.material3.Card

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ActionLine(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CampusTheme {
        Greeting("Android")
    }
}

@Composable
fun EventHeader(title: String, category: String, location: String?){
    Column {
        Text(title)
        Text(category)
        Text(
            text = location?.takeIf { it.isNotBlank() }?.let { "$it!" } ?: "Lieu à confirmer"
        )
    }
}

@Preview
@Composable
fun ActionLine(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Atelier Kotlin : les coroutines sur Android")
        Button(onClick = { /*TODO*/ }) {
            Text("Voir")
        }
    }
}

data class Event(
    val id: String,
    val title: String,
    val category: String,
    val location: String?,
    val capacity: Int
)

val sampleEvents = listOf(
    Event(
        id = "1",
        title = "Atelier Kotlin & Coroutines",
        category = "Développement",
        location = "Amphi A",
        capacity = 25
    ),
    Event(
        id = "2",
        title = "Découverte de Jetpack Compose",
        category = "Développement",
        location = "Salle 204",
        capacity = 0 // Événement complet
    ),
    Event(
        id = "3",
        title = "Conférence Sécurité Android",
        category = "Sécurité",
        location = null, // Sans lieu précisé
        capacity = 50
    ),
    Event(
        id = "4",
        title = "Hackathon Campus 2026",
        category = "Innovation",
        location = "Hall principal",
        capacity = 100
    ),
    Event(
        id = "5",
        title = "Meetup Design System & Accessibilité",
        category = "Design",
        location = "Espace Coworking",
        capacity = 15
    )
)

@Composable
fun EventCard(
    event: Event,
    onBook: () -> Unit,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EventHeader(
                title = event.title,
                category = event.category,
                location = event.location
            )

            Text(
                text = if (event.capacity > 0) "${event.capacity} places disponibles" else "Complet"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onBook,
                    enabled = event.capacity > 0
                ) {
                    Text("Réserver")
                }
            }
        }
    }
}