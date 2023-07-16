package hermes.comicslibrary

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import hermes.comicslibrary.api.ApiService
import hermes.comicslibrary.api.MarvelApiRepo
import hermes.comicslibrary.model.connectivity.ConnectivityMonitor
import hermes.comicslibrary.model.db.CharacterDao
import hermes.comicslibrary.model.db.CollectionDb
import hermes.comicslibrary.model.db.CollectionDbRepo
import hermes.comicslibrary.model.db.CollectionDbRepoImpl
import hermes.comicslibrary.model.db.Constants.DB
import hermes.comicslibrary.model.db.NoteDao

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)

    @Provides
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionDb: CollectionDb) = collectionDb.characterDao()

    @Provides
    fun provideNoteDao(collectionDb: CollectionDb) = collectionDb.noteDao()

    @Provides
    fun provideDbRepoImpl(characterDao: CharacterDao, noteDao: NoteDao): CollectionDbRepo =
        CollectionDbRepoImpl(characterDao, noteDao)

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        ConnectivityMonitor.getInstance(context)

}