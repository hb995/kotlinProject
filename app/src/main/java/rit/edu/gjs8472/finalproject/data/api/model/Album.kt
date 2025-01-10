package rit.edu.gjs8472.finalproject.data.api.model


import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("album")
    val album: List<AlbumX>
)