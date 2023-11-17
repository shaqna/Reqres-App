package com.maou.reqresapp.presentation.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReqresUserParcel(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
) : Parcelable


class ReqresUserArgType : NavType<ReqresUserParcel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ReqresUserParcel? {
        return bundle.getString(key)?.let {
            parseValue(it)
        }
    }

    override fun parseValue(value: String): ReqresUserParcel {
        return Gson().fromJson(value, ReqresUserParcel::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: ReqresUserParcel) {
        bundle.putString(key, Gson().toJson(value))
    }

}