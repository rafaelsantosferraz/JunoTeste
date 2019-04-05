package rafaelsantosferraz.com.base.interactors.main

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import rafaelsantosferraz.com.base.data.repositories.main.ItemRepository
import javax.inject.Inject

class MainInteractor  @Inject constructor(
    private val itemRepository: ItemRepository
){
    suspend fun getListAsync(query: String, page: Int): Deferred<List<Any>> = coroutineScope {
        async(Dispatchers.IO){
            itemRepository.getItems(query, page)
        }
    }
}