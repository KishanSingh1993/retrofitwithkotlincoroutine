package com.kishan.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var retService : AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retService = RetrofirInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)



        //getRequestWithPath()
        //getRequestWithQuery()
        uploadAlbum()


    }


    private fun getRequestWithQuery(){
        val responseData:LiveData<Response<Album>> = liveData {

            //val response = retService.getAlbums()
            val response = retService.getSortedAlbums(3)

            emit(response)
        }

        responseData.observe(this, Observer {

            val albumList: MutableListIterator<AlbumItem>? = it.body()?.listIterator()

            if (albumList!=null){

                while (albumList.hasNext()){

                    val albumItem: AlbumItem = albumList.next()

                    Log.i("MyTag",albumItem.title)
                    val result =" "+"Album Title : ${albumItem.title}"+"\n"+
                            " "+"Album id : ${albumItem.id}"+"\n"+
                            " "+"User id : ${albumItem.userId}"+"\n\n\n"
                    textView.append(result)
                }
            }


        })
    }

    private fun getRequestWithPath(){
        // path parameter example

        val pathResponse : LiveData<Response<AlbumItem>> = liveData {

            val response = retService.getAlbum(3)

            emit(response)
        }

        pathResponse.observe(this, Observer {

            val title : String? = it.body()?.title

            Toast.makeText(applicationContext,title,Toast.LENGTH_LONG).show()
        })
    }

    private fun uploadAlbum(){

        val album = AlbumItem(0,"My Album",3)

        val postResponse : LiveData<Response<AlbumItem>> = liveData {

            val response : Response<AlbumItem> = retService.uploadAlbum(album)

            emit(response)
        }

        postResponse.observe(this, Observer {

            val receivedALbum : AlbumItem? = it.body()
            val result =" "+"Album Title : ${receivedALbum?.title}"+"\n"+
                    " "+"Album id : ${receivedALbum?.id}"+"\n"+
                    " "+"User id : ${receivedALbum?.userId}"+"\n\n\n"
            textView.append(result)
        })
    }
}
