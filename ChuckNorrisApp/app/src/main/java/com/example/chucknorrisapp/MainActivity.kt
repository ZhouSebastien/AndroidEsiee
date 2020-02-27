package com.example.chucknorrisapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import values.jokesList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chuckJoke = jokesList.jokes
        Log.i("Jokes Object", chuckJoke.toString())
    }

}
