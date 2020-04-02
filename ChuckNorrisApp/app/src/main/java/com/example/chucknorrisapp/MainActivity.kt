package com.example.chucknorrisapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewTreeObserver
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

import retrofit2.http.GET
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


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

        val progressBar: ProgressBar = findViewById(R.id.progress_bar)

        val adapter = JokeAdapter(mutableListOf(), this){
            val jokeService = JokeApiServiceFactory.make()
            val jokeSubscriber = jokeService
                .giveMeAJoke()
                .delay(250, TimeUnit.MILLISECONDS)
                .repeat(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{progressBar.visibility = VISIBLE}
                .doOnTerminate{
                    val jokeRecycler = findViewById<RecyclerView>(R.id.CN_jokes_list)
                    jokeRecycler.adapter?.notifyDataSetChanged()
                    progressBar.visibility = INVISIBLE
                }
                .subscribeBy(
                    onError = {throwable: Throwable -> Log.e("jokeSubscribeError", "Joke not found: ($throwable)") },
                    onNext = {joke: Joke ->
                        Log.i("JokeSubscribeSuccess", "joke added: ($joke)")
                        it.addJoke(joke)
                    }
                )
            this.disposable.add(jokeSubscriber)
            Unit
        }

        val layoutManager = LinearLayoutManager(this)
        val jokeRecycler = findViewById<RecyclerView>(R.id.CN_jokes_list)
        jokeRecycler.layoutManager = layoutManager
        jokeRecycler.adapter = adapter

        adapter.onBottomReached(adapter)
        jokeRecycler.viewTreeObserver.addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
            if(!jokeRecycler.canScrollVertically(1)){
                adapter.onBottomReached(adapter)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        this.disposable.clear()
    }


}
