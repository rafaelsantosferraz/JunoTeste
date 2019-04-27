package rafaelsantosferraz.com.base.data.datasource.artists


import androidx.paging.PageKeyedDataSource
import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.presentation.db.RoomDB
import javax.inject.Inject

class LocalItemDataSource @Inject constructor(
    private val roomDB: RoomDB
): PageKeyedDataSource<Int, Any>(), ItemDataSource {

    override fun getDataSource(query: String): PageKeyedDataSource<Int, Any> {
        return this
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Any>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Any>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Any>) {

    }

    override suspend fun getItems(query: String, page: Int): List<Item> {
        return roomDB.itemDao().getAllItem()
    }

    override suspend fun addItems(items: List<Item>): Boolean {
        roomDB.itemDao().deleteAll()
        roomDB.itemDao().insertAll(items)
        return true
    }
}
