package rafaelsantosferraz.com.base.data.repositories.readme

import rafaelsantosferraz.com.base.data.datasource.artists.ItemDataSource
import rafaelsantosferraz.com.base.data.datasource.artists.ReadmeDataSource
import rafaelsantosferraz.com.base.di.qualifiers.Local
import rafaelsantosferraz.com.base.di.qualifiers.Remote
import rafaelsantosferraz.com.base.domain.Item
import javax.inject.Inject

class ReadmeRepository @Inject constructor(
    @Remote private val remoteReadmeDataSource: ReadmeDataSource
) {

    suspend fun getReadme(ownerLogin: String, repoName: String): String {
        return remoteReadmeDataSource.getReadme(ownerLogin, repoName)
    }
}