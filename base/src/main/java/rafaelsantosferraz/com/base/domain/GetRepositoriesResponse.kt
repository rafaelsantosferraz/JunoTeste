package rafaelsantosferraz.com.base.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class GetRepositoriesResponse {

    @SerializedName("total_count")
    @Expose
    val totalCount: Long? = null
    @SerializedName("incomplete_results")
    @Expose
    val  incompleteResults: Boolean? = null
    @SerializedName("items")
    @Expose
    val items: List<Item>? = null

}