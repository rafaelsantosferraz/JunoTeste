package rafaelsantosferraz.com.base.presentation.network

import io.reactivex.Single
import rafaelsantosferraz.com.base.domain.GetRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubApi {

    companion object {
        val BASE_URL="https://api.github.com/search/"
    }




    // REPOSITORIES ----------------------------------------------------------------------------------------------------
    @GET("repositories")
    fun getRepositories(
        @Query("q") termo: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = 20
    ): Single<GetRepositoriesResponse>




}