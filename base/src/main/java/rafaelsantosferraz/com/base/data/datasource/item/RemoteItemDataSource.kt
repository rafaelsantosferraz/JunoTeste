package rafaelsantosferraz.com.base.data.datasource.artists


import androidx.paging.PageKeyedDataSource
import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.presentation.network.GithubApi
import javax.inject.Inject

class RemoteItemDataSource @Inject constructor(
        private val githubApi: GithubApi
): PageKeyedDataSource<Int, Any>(), ItemDataSource {

    private var query = ""

    override fun getDataSource(query: String): PageKeyedDataSource<Int, Any>{
        this.query = query
        return this
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Any>) {

        val items = githubApi.getRepositories(query, per_page = params.requestedLoadSize).blockingGet().items ?: listOf()
        callback.onResult(
            items,null, 1
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Any>) {
        val items = githubApi.getRepositories(query, page = params.key, per_page = params.requestedLoadSize).blockingGet().items ?: listOf()
        callback.onResult(
            items, params.key.plus(1)
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Any>) {
        val items = githubApi.getRepositories(query, page = params.key, per_page = params.requestedLoadSize).blockingGet().items ?: listOf()
        callback.onResult(
            items, params.key.plus(-1)
        )
    }

    override suspend fun getItems(query: String, page: Int): List<Item> {
        return githubApi.getRepositories(query, page).blockingGet().items ?: listOf()
    }

    override suspend fun addItems(items: List<Item>): Boolean {
        return false
    }
}