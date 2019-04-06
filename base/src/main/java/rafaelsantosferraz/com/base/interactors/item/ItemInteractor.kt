package rafaelsantosferraz.com.base.interactors.item

import android.util.Base64
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import rafaelsantosferraz.com.base.data.repositories.readme.ReadmeRepository
import javax.inject.Inject

class ItemInteractor  @Inject constructor(
    private val readmeRepository: ReadmeRepository
){
    suspend fun getReadmeAsync(ownerLogin: String, repoName: String): Deferred<String> = coroutineScope {
        async(Dispatchers.IO){
            val base64Readme = readmeRepository.getReadme(ownerLogin, repoName)
            val readme =  Base64.decode(base64Readme, Base64.DEFAULT).toString(charset("UTF-8"))
            readme
        }
    }
}