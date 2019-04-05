package rafaelsantosferraz.com.base.data.repositories.main

import rafaelsantosferraz.com.base.data.datasource.artists.ItemDataSource
import rafaelsantosferraz.com.base.di.qualifiers.Remote
import rafaelsantosferraz.com.base.domain.Item
import javax.inject.Inject

class ItemRepository @Inject constructor(
    @Remote private val remoteItemDataSource: ItemDataSource
) {

    suspend fun getItems(query: String, page: Int): List<Item> {
        return remoteItemDataSource.getItems(query, page)
    }

}