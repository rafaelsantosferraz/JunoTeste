package rafaelsantosferraz.com.base.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import rafaelsantosferraz.com.base.App
import rafaelsantosferraz.com.base.data.datasource.artists.*
import rafaelsantosferraz.com.base.di.qualifiers.Local
import rafaelsantosferraz.com.base.di.qualifiers.Remote
import rafaelsantosferraz.com.base.presentation.db.RoomDB
import rafaelsantosferraz.com.base.presentation.network.GithubApi
import javax.inject.Singleton

@Module
class DataSourceModule {

    // ITEMS
    @Remote
    @Provides
    @Singleton
    fun providesRemoteItemsDataSource(githubApi: GithubApi) : ItemDataSource{
        return RemoteItemDataSource(githubApi)
    }

    @Local
    @Provides
    @Singleton
    fun providesLocalItemsDataSource(application: Application) : ItemDataSource{
        return LocalItemDataSource(RoomDB.getDatabase(application)!!)
    }



    // README
    @Remote
    @Provides
    @Singleton
    fun providesRemoteReadmeDataSource(githubApi: GithubApi) : ReadmeDataSource {
        return RemoteReadmeDataSource(githubApi)
    }
}