package net.prabowoaz.consumerfavorite.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemFavorite(
    var id: Int = 0,
    var avatar: String = "",
    var login: String = "",
    var url: String = "",
    var favorite: Int = 0
) : Parcelable