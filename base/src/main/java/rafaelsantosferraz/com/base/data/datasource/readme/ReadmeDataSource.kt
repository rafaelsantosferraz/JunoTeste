package rafaelsantosferraz.com.base.data.datasource.artists

import rafaelsantosferraz.com.base.domain.Item

interface ReadmeDataSource{
    suspend fun getReadme(ownerLogin: String, repoName: String): String
}
