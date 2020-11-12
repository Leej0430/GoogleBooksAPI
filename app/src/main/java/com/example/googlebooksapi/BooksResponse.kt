package com.example.googlebooksapi

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class BooksResponse (
    val items: List<ItemsDescription>
):Parcelable

@Parcelize
data class ItemsDescription(
        val volumeInfo: VolumeInfo
):Parcelable

@Parcelize
data class VolumeInfo(
        val title: String,
        val subtitle: String
):Parcelable


//Java Serializeable
//convert into bytes, and its going to identify this bytes with
//UID
//Uses Relection...

//Android Parcelable