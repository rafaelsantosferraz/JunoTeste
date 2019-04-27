package rafaelsantosferraz.com.base.presentation.network

import io.reactivex.Single
import rafaelsantosferraz.com.base.domain.GetReadmeResponse
import rafaelsantosferraz.com.base.domain.GetRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubApi {

    companion object {
        val BASE_URL="https://api.github.com/"
    }




    // REPOSITORIES ----------------------------------------------------------------------------------------------------
    @Headers("'User-Agent': 'request'")
    @GET("search/repositories")


    fun getRepositories(
        @Query("q") termo: String = "",
        @Query("page") page: Int = 0,
        @Query("per_page") per_page: Int = 20
    ): Single<GetRepositoriesResponse>


    // CONTENT ---------------------------------------------------------------------------------------------------------
    @GET("repos/{ownerLogin}/{repoName}/contents/README.md")
    fun getReadme(
        @Path("ownerLogin") ownerLogin: String,
        @Path("repoName") repoName: String
    ): Single<GetReadmeResponse>




}