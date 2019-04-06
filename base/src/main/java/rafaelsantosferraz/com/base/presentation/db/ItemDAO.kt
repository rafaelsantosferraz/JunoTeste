package rafaelsantosferraz.com.base.presentation.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rafaelsantosferraz.com.base.domain.Item

@Dao
interface ItemDAO {

    @Insert
    fun insertAll(items: List<Item>)

    @Query("DELETE FROM Item")
    fun deleteAll()

    @Query("SELECT * FROM Item ORDER BY item.score DESC" )
    fun getAllItem() : List<Item>
}