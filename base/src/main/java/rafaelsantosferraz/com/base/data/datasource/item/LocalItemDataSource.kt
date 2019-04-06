package rafaelsantosferraz.com.base.data.datasource.artists


import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.presentation.db.RoomDB
import javax.inject.Inject

class LocalItemDataSource @Inject constructor(
    private val roomDB: RoomDB
): ItemDataSource {

    override suspend fun getItems(query: String, page: Int): List<Item> {
        return roomDB.itemDao().getAllItem()
    }

    override suspend fun addItems(items: List<Item>): Boolean {
        roomDB.itemDao().deleteAll()
        roomDB.itemDao().insertAll(items)
        return true
    }
}
