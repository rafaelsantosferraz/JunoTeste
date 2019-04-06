package rafaelsantosferraz.com.base.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetReadmeResponse {

    @SerializedName("name")
    @Expose
     val name: String? = null
    @SerializedName("path")
    @Expose
     val path: String? = null
    @SerializedName("sha")
    @Expose
     val sha: String? = null
    @SerializedName("size")
    @Expose
     val size: Int? = null
    @SerializedName("url")
    @Expose
     val url: String? = null
    @SerializedName("html_url")
    @Expose
     val htmlUrl: String? = null
    @SerializedName("git_url")
    @Expose
     val gitUrl: String? = null
    @SerializedName("download_url")
    @Expose
     val downloadUrl: String? = null
    @SerializedName("type")
    @Expose
     val type: String? = null
    @SerializedName("content")
    @Expose
     val content: String? = null
    @SerializedName("encoding")
    @Expose
     val encoding: String? = null
}
