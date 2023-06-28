package hermes.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

class CollectionDbRepoImpl(private val characterDao: CharacterDao): CollectionDbRepo {
    override suspend fun getCharactersFromRepo(): Flow<List<DbCharacter>> {
        return characterDao.getCharacters()
    }

    override suspend fun getCharacterFromRepo(characterId: Int): Flow<DbCharacter> {
        return characterDao.getCharacter(characterId)
    }

    override suspend fun addCharacterToRepo(character: DbCharacter) {
        characterDao.addCharacter(character)
    }

    override suspend fun updateCharacterInRepo(character: DbCharacter) {
        characterDao.updateCharacter(character)
    }

    override suspend fun deleteCharacterFromRepo(character: DbCharacter) {
        characterDao.deleteCharacter(character)
    }
}