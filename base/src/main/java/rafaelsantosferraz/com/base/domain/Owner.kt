package rafaelsantosferraz.com.base.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner (

    @SerializedName("login")
    @Expose
    var login: String? = null,
    @SerializedName("id")
    @Expose
    var ownerId: Long? = null,
    @SerializedName("node_id")
    @Expose
    var ownerNodeId: String? = null,
    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null,
    @SerializedName("gravatar_id")
    @Expose
    var gravatarId: String? = null,
    @SerializedName("url")
    @Expose
    var ownerUrl: String? = null,
    @SerializedName("html_url")
    @Expose
    var ownerHtmlUrl: String? = null,
    @SerializedName("followers_url")
    @Expose
    var followersUrl: String? = null,
    @SerializedName("following_url")
    @Expose
    var followingUrl: String? = null,
    @SerializedName("gists_url")
    @Expose
    var gistsUrl: String? = null,
    @SerializedName("starred_url")
    @Expose
    var starredUrl: String? = null,
    @SerializedName("subscriptions_url")
    @Expose
    var subscriptionsUrl: String? = null,
    @SerializedName("organizations_url")
    @Expose
    var organizationsUrl: String? = null,
    @SerializedName("repos_url")
    @Expose
    var reposUrl: String? = null,
    @SerializedName("events_url")
    @Expose
    var ownerEventsUrl: String? = null,
    @SerializedName("received_events_url")
    @Expose
    var receivedEventsUrl: String? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("site_admin")
    @Expose
    var siteAdmin: Boolean? = null

): Parcelable