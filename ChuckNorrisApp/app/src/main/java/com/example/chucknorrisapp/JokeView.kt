package com.example.chucknorrisapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class JokeView @JvmOverloads constructor(
    context: Context, attributeS: AttributeSet? = null
): ConstraintLayout(context, attributeS) {
    init {
        LayoutInflater.from(context).inflate(R.layout.joke_layout, this, true)
        this.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    data class Model(val value: String)
    fun setUpView(model: Model) {
        var tv_joke_item = findViewById<TextView>(R.id.tv_joke_item)
        tv_joke_item.text = model.value
    }
}