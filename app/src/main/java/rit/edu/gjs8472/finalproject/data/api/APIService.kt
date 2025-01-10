package rit.edu.gjs8472.finalproject.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import rit.edu.gjs8472.finalproject.data.api.model.Album

const val BASE_URL = "https://www.theaudiodb.com/api/v1/json/2/"

interface APIService {
    @GET("album.php?i=112024")
    suspend fun getAlbums(): Album

    companion object {
        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        private val client: OkHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()

        private var apiService: APIService? = null

        fun getInstance(): APIService{
            if(apiService == null){
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build().create(APIService::class.java)
            }
            return  apiService!!
        }
    }
}