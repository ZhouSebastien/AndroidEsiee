package com.example.chucknorrisapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

import retrofit2.http.GET
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.create



interface JokeApiService {
    @GET("jokes/random")
    fun giveMeAJoke(): Single<Joke>
}

object JokeApiServiceFactory {
    fun make(): JokeApiService {
        val builder = Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
        return builder.create<JokeApiService>()
    }
}

class MainActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = JokeAdapter(mutableListOf(), this)
        val layoutManager = LinearLayoutManager(this)
        val jokeRecycler = findViewById<RecyclerView>(R.id.CN_jokes_list)
        jokeRecycler.layoutManager = layoutManager
        jokeRecycler.adapter = adapter

        val jokeService = JokeApiServiceFactory.make()
        val jokeSubscriber = jokeService
            .giveMeAJoke()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = {throwable: Throwable -> Log.e("jokeSubscribeError", "Joke not found: ($throwable)") },
                onSuccess = {joke: Joke -> Log.i("JokeSubscribeSucces", "joke added: ($joke)"); adapter.addJoke(joke)}
            )
        this.disposable.add(jokeSubscriber)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.disposable.clear()
    }


}
