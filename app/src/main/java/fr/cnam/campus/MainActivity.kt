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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.cnam.campus.ui.theme.CampusTheme
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.layout.PaddingValues
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusTheme {
                EventListScreen(
                    events = sampleEvents,
                    onEventClick = { event ->
                        Log.d("Campus", "Clic sur l'événement : ${event.title}")
                    },
                    onBookClick = { event ->
                        Log.d("Campus", "Réservation effectuée pour : ${event.title}")
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventListScreenPreview() {
    CampusTheme {
        EventListScreen(
            events = sampleEvents,
            onEventClick = {},
            onBookClick = {}
        )
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
fun EventHeader(
    title: String,
    category: String,
    location: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = category.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = location?.takeIf { it.isNotBlank() } ?: "Lieu à confirmer",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
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

@Preview(showBackground = true)
@Composable
fun EventCardFullPreview() {
    CampusTheme {
        EventCard(
            event = Event(
                id = "preview_full",
                title = "Découverte de Jetpack Compose",
                category = "Développement",
                location = "Salle 204",
                capacity = 0 // Événement complet
            ),
            onBook = {},
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    events: List<Event>,
    onEventClick: (Event) -> Unit,
    onBookClick: (Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus") }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = events,
                key = { event -> event.id }
            ) { event ->
                EventCard(
                    event = event,
                    onClick = { onEventClick(event) },
                    onBook = { onBookClick(event) }
                )
            }
        }
    }
}