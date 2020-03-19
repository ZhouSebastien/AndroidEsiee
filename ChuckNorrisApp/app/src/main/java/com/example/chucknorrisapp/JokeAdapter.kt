package com.example.chucknorrisapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.joke_layout.view.*

class JokeAdapter(private val items: List<Joke>, private val context: Context):  RecyclerView.Adapter<JokeAdapter.JokeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(LayoutInflater.from(context).inflate(R.layout.joke_layout, parent, false) as LinearLayout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.tvJoke.tv_joke_item.text = items[position].value
    }

    class JokeViewHolder(linearView: LinearLayout): RecyclerView.ViewHolder(linearView) {
        val tvJoke: LinearLayout = linearView
    }
}