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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.height
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.pluralStringResource
import android.content.res.Configuration.UI_MODE_NIGHT_YES

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

@Preview(name = "Liste - Mode Clair", showBackground = true)
@Preview(name = "Liste - Mode Sombre", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
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
            text = location?.takeIf { it.isNotBlank() } ?: stringResource(R.string.location_to_confirm),
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
    val capacity: Int,
    val description: String = ""
)

val sampleEvents = listOf(
    Event(
        id = "1",
        title = "Atelier Kotlin & Coroutines",
        category = "Développement",
        location = "Amphi A",
        capacity = 25,
        description = "Venez découvrir la puissance des Coroutines en Kotlin pour gérer l'asynchronisme dans vos applications Android modernes.\n\nAu programme : Dispatchers, Flow, Structured Concurrency, gestion des annulations et des erreurs. Des exercices pratiques sur machines virtuelles et smartphones seront proposés tout au long de la session."
    ),
    Event(
        id = "2",
        title = "Découverte de Jetpack Compose",
        category = "Développement",
        location = "Salle 204",
        capacity = 0, // Complet
        description = "Plongez dans le futur du développement d'interfaces Android avec Jetpack Compose. Apprenez à concevoir des écrans déclaratifs, dynamiques et performants sans écrire une seule ligne de XML."
    ),
    Event(
        id = "3",
        title = "Conférence Sécurité Android",
        category = "Sécurité",
        location = null,
        capacity = 50,
        description = "Tour d'horizon des bonnes pratiques de sécurité : stockage sécurisé avec EncryptedSharedPreferences, authentification biométrique et Credential Manager, et sécurisation des communications réseau."
    ),
    Event(
        id = "4",
        title = "Hackathon Campus 2026",
        category = "Innovation",
        location = "Hall principal",
        capacity = 100,
        description = "48 heures non-stop pour concevoir, prototyper et présenter un projet innovant à impact social ou environnemental en équipe. Nombreux lots à gagner et mentors d'entreprises partenaires présents !"
    ),
    Event(
        id = "5",
        title = "Meetup Design System & Accessibilité",
        category = "Design",
        location = "Espace Coworking",
        capacity = 15,
        description = "Comment concevoir des composants Material 3 accessibles à tous les utilisateurs (talkback, contraste, taille de police dynamique) tout en respectant une identité visuelle forte."
    )
)

val sampleCategories = listOf("Tous", "Développement", "Sécurité", "Innovation", "Design")

@Composable
fun EventCard(
    modifier: Modifier = Modifier,
    event: Event,
    onBook: () -> Unit,
    onClick: () -> Unit = {}
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
                text = if (event.capacity > 0) {
                    pluralStringResource(
                        id = R.plurals.available_places,
                        count = event.capacity,
                        event.capacity
                    )
                } else {
                    stringResource(R.string.status_full)
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onBook,
                    enabled = event.capacity > 0
                ) {
                    Text(stringResource(R.string.btn_book))
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
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(sampleCategories) { category ->
                        FilterChip(
                            selected = (category == "Tous"),
                            onClick = { /* Filtrage à venir */ },
                            label = { Text(category) }
                        )
                    }
                }
            }

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

@Composable
fun EventDetailScreen(
    modifier: Modifier = Modifier,
    event: Event,
    onBookClick: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            Button(
                onClick = onBookClick,
                enabled = event.capacity > 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    if (event.capacity > 0) {
                        stringResource(R.string.btn_book)
                    } else {
                        stringResource(R.string.status_full)
                    }
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.cd_event_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EventHeader(
                    title = event.title,
                    category = event.category,
                    location = event.location
                )

                Text(
                    text = stringResource(R.string.section_description),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(name = "Détail - Mode Clair", showBackground = true)
@Preview(name = "Détail - Mode Sombre", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EventDetailScreenPreview() {
    CampusTheme {
        EventDetailScreen(
            event = sampleEvents.first(),
            onBookClick = {}
        )
    }
}