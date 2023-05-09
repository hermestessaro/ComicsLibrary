package hermes.comicslibrary

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import hermes.comicslibrary.api.ApiService
import hermes.comicslibrary.api.MarvelApiRepo

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)
}