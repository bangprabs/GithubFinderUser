package net.prabowoaz.consumerfavorite.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemUsers(
    var id: Int = 0,
    var avatar: String = "",
    var login: String = "",
    var url: String = "",
    var name: String = "",
    var company: String = "",
    var location: String = "",
    var followers: Int = 0,
    var following: Int = 0,
    var repos: Int = 0
) : Parcelable