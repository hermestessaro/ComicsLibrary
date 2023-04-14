package hermes.comicslibrary.utils

sealed class Destination(val route: String) {
    object Library: Destination("Library")
    object Collection: Destination("Collection")
    object CharacterDetail: Destination("character/{characterId}"){
        fun createRoute(characterId: Int?): String {
            return "character/$characterId"
        }
    }
}