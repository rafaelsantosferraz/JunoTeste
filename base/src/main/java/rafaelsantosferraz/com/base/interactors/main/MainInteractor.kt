package rafaelsantosferraz.com.base.interactors.main

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import rafaelsantosferraz.com.base.data.repositories.item.ItemRepository
import rafaelsantosferraz.com.base.domain.Item
import javax.inject.Inject

class MainInteractor  @Inject constructor(
    private val itemRepository: ItemRepository
){

    suspend fun getPagedListAsync(query: String): Deferred<PagedList<Any>> = coroutineScope {
        async(Dispatchers.IO){

            val dataSourceFactory = object : DataSource.Factory<Int, Any>() {
                override fun create(): DataSource<Int, Any> {
                    return itemRepository.getRemoteDataSource(query)
                }
            }

            val config = PagedList.Config.Builder()
                .setPageSize(50)
                .setEnablePlaceholders(false)
                .build()
            RxPagedListBuilder<Int, Any>(dataSourceFactory, config).buildObservable().blockingFirst()
            //itemRepository.getRemoteDataSource(query)
        }
    }

    suspend fun getListAsync(query: String, page: Int): Deferred<List<Any>> = coroutineScope {
        async(Dispatchers.IO){
            itemRepository.getItems(query, page)
        }
    }

    suspend fun saveListAsync(items: List<Item>): Deferred<Boolean> = coroutineScope {
        async(Dispatchers.IO){
            itemRepository.saveItems(items)
        }
    }

    suspend fun getSavedListAsync(): Deferred<List<Any>> = coroutineScope {
        async(Dispatchers.IO){
            itemRepository.getSavedItems()
        }
    }
}