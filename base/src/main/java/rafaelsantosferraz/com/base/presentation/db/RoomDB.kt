package rafaelsantosferraz.com.base.presentation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rafaelsantosferraz.com.base.domain.Item

@Database(entities = [Item::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun itemDao(): ItemDAO

    companion object {

        private var INSTANCE: RoomDB? = null

        internal fun getDatabase(context: Context): RoomDB? {
            if (INSTANCE == null) {
                synchronized(RoomDB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RoomDB::class.java, "item_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
