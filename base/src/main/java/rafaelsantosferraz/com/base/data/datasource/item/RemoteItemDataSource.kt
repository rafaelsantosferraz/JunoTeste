package rafaelsantosferraz.com.base.data.datasource.artists


import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.presentation.network.GithubApi
import javax.inject.Inject

class RemoteItemDataSource @Inject constructor(
        private val githubApi: GithubApi
): ItemDataSource {

    override suspend fun getItems(query: String, page: Int): List<Item> {
        return githubApi.getRepositories(query, page).blockingGet().items ?: listOf()
    }

    override suspend fun addItems(items: List<Item>): Boolean {
        return false
    }
}