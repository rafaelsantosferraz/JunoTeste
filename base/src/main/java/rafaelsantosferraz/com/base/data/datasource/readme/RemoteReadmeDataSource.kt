package rafaelsantosferraz.com.base.data.datasource.artists


import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.presentation.network.GithubApi
import javax.inject.Inject

class RemoteReadmeDataSource @Inject constructor(
        private val githubApi: GithubApi
): ReadmeDataSource {

    override suspend fun getReadme(ownerLogin: String, repoName: String): String {
        return githubApi.getReadme(ownerLogin, repoName).blockingGet().content ?: ""
    }
}