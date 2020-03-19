package com.example.chucknorrisapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import values.jokesList
import androidx.recyclerview.widget.LinearLayoutManager

import retrofit2.http.GET
import io.reactivex.Single


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chuckJoke = mutableListOf<Joke>()
        for (joke in jokesList.jokes) {
            val jokeObj = Joke(
                categories = emptyList(),
                createdAt = "",
                iconUrl = "",
                id = "",
                updatedAt = "",
                url = "",
                value = joke)
            chuckJoke.add(jokeObj)
        }
        //Log.i("Jokes Object", chuckJoke.toString())
        CN_jokes_list.layoutManager = LinearLayoutManager(this)
        CN_jokes_list.adapter = JokeAdapter(chuckJoke, this)
    }

    interface JokeApiService {
        @GET("my/api/path")
        fun giveMeAJoke(): Single<Joke>
    }



}
