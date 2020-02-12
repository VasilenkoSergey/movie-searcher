package io.vasilenko.otus.moviesearcher.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val title: String,
    val description: String,
    val rating: String,
    var posterPath: String? = null,
    var backdropPath: String? = null
) : Parcelable