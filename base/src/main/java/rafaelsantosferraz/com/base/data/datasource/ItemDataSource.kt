package rafaelsantosferraz.com.base.data.datasource.artists

import rafaelsantosferraz.com.base.domain.Item

interface ItemDataSource{
    suspend fun getItems(query: String, page: Int): List<Item>
}
