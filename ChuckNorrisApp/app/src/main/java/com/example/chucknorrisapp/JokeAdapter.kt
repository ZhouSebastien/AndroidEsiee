package com.example.chucknorrisapp

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(private var items: List<Joke>,
                  private val context: Context,
                  val onBottomReached: (JokeAdapter) -> Unit?):  RecyclerView.Adapter<JokeAdapter.JokeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(JokeView(context))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.tvJoke.setUpView(JokeView.Model(items[position].value))
    }

    class JokeViewHolder(jokeView: JokeView): RecyclerView.ViewHolder(jokeView) {
        val tvJoke: JokeView = jokeView
    }

    fun addJoke(item: Joke) {
        val tempList = items.toMutableList()
        tempList.add(item)
        items = tempList.toList()
    }

    fun getJokes(): List<Joke> {
        return items
    }
}
