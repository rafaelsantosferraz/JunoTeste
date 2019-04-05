package rafaelsantosferraz.com.base.di.module

import dagger.Module
import dagger.Provides
import rafaelsantosferraz.com.base.data.datasource.artists.ItemDataSource
import rafaelsantosferraz.com.base.data.datasource.artists.RemoteItemDataSource
import rafaelsantosferraz.com.base.di.qualifiers.Remote
import rafaelsantosferraz.com.base.presentation.network.GithubApi
import javax.inject.Singleton

@Module
class DataSourceModule {

    // ITEMS
    @Remote
    @Provides
    @Singleton
    fun providesRemoteShowsDataSource(githubApi: GithubApi) : ItemDataSource{
        return RemoteItemDataSource(githubApi)
    }

}