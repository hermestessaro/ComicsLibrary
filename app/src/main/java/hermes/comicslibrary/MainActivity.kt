package hermes.comicslibrary

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hermes.comicslibrary.ui.theme.ComicsLibraryTheme
import hermes.comicslibrary.utils.Destination
import hermes.comicslibrary.view.CharacterDetailScreen
import hermes.comicslibrary.view.CharactersBottomNav
import hermes.comicslibrary.view.CollectionScreen
import hermes.comicslibrary.view.LibraryScreen
import hermes.comicslibrary.viewmodel.CollectionDbViewModel
import hermes.comicslibrary.viewmodel.LibraryApiViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val libraryViewModel by viewModels<LibraryApiViewModel>()
    private val collectionViewModel by viewModels<CollectionDbViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsLibraryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    CharactersScaffold(
                        navController = navController,
                        libraryViewModel,
                        collectionViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun CharactersScaffold(
    navController: NavHostController,
    libraryApiViewModel: LibraryApiViewModel,
    collectionViewModel: CollectionDbViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { CharactersBottomNav(navController = navController) }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                LibraryScreen(navController, libraryApiViewModel, paddingValues)
            }
            composable(Destination.Collection.route) {
                CollectionScreen(collectionViewModel)
            }
            composable(Destination.CharacterDetail.route) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("characterId")?.toIntOrNull()
                if (id == null) {
                    Toast.makeText(context, "Character id is required", Toast.LENGTH_SHORT).show()
                } else {
                    libraryApiViewModel.retrieveSingleCharacter(id)
                    CharacterDetailScreen(
                        libraryViewModel = libraryApiViewModel,
                        collectionViewModel = collectionViewModel,
                        paddingValues = paddingValues,
                        navController = navController
                    )
                }
            }
        }
    }
}