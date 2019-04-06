package rafaelsantosferraz.com.base.data.datasource.artists

import rafaelsantosferraz.com.base.domain.Item

interface ItemDataSource{
    suspend fun getItems(query: String = "", page: Int = 0): List<Item>
    suspend fun addItems(items: List<Item>): Boolean
}
