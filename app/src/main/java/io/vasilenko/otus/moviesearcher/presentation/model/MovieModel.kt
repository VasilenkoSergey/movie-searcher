package io.vasilenko.otus.moviesearcher.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val title: String,
    val description: String,
    val rating: String,
    val imageName: String,
    var imgId: Int = 0
) : Parcelable