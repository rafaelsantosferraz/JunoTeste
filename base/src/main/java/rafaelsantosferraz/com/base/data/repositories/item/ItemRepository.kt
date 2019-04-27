package rafaelsantosferraz.com.base.data.repositories.item

import androidx.paging.PageKeyedDataSource
import rafaelsantosferraz.com.base.data.datasource.artists.ItemDataSource
import rafaelsantosferraz.com.base.di.qualifiers.Local
import rafaelsantosferraz.com.base.di.qualifiers.Remote
import rafaelsantosferraz.com.base.domain.Item
import javax.inject.Inject

class ItemRepository @Inject constructor(
    @Remote private val remoteItemDataSource: ItemDataSource,
    @Local private val localItemDataSource: ItemDataSource
) {

    fun getRemoteDataSource(query: String): PageKeyedDataSource<Int, Any>{
        return remoteItemDataSource.getDataSource(query)
    }

    suspend fun getItems(query: String, page: Int): List<Item> {
        return remoteItemDataSource.getItems(query, page)
    }

    suspend fun saveItems(items: List<Item>): Boolean {
        return localItemDataSource.addItems(items)
    }

    suspend fun getSavedItems(): List<Item> {
        return localItemDataSource.getItems()
    }
}