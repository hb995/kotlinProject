package rit.edu.gjs8472.finalproject.data.api.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import rit.edu.gjs8472.finalproject.data.api.APIService
import kotlinx.coroutines.launch
import java.lang.Exception

class AlbumsViewModel: ViewModel(){
    private val _albumList = mutableStateListOf<AlbumX>()
    var errorMessage: String by mutableStateOf("")
    var loading by mutableStateOf(false)
    val albumList: List<AlbumX>
        get() = _albumList

    fun getAlbumById(idAlbum: String): AlbumX? {
        return _albumList.find { it.idAlbum == idAlbum }
    }

    fun fetchAlbums() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()

            try {
                loading = true
                val response = apiService.getAlbums()
                _albumList.clear()
                _albumList.addAll(response.album)
                loading = false
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                loading = false
            }
        }
    }
}