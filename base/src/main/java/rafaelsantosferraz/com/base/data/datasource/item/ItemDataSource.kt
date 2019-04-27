package rafaelsantosferraz.com.base.data.datasource.artists

import androidx.paging.PageKeyedDataSource
import rafaelsantosferraz.com.base.domain.Item

interface ItemDataSource{
    fun getDataSource(query: String): PageKeyedDataSource<Int, Any>
    suspend fun getItems(query: String = "", page: Int = 0): List<Item>
    suspend fun addItems(items: List<Item>): Boolean
}
