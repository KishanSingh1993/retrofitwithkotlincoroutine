package com.kishan.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retService: AlbumService = RetrofirInstance
                                      .getRetrofitInstance()
                                      .create(AlbumService::class.java)

        val responseData:LiveData<Response<Album>> = liveData {

            val response = retService.getAlbums()

            emit(response)
        }

        responseData.observe(this, Observer {

            val albumList: MutableListIterator<AlbumItem>? = it.body()?.listIterator()

            if (albumList!=null){

                while (albumList.hasNext()){

                    val albumItem: AlbumItem = albumList.next()

                    Log.i("MyTag",albumItem.title)
                }
            }


        })
    }
}
