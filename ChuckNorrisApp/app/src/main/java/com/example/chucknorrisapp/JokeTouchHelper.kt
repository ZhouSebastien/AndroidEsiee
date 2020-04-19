package com.gopro.chucknorrisjokes

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class JokeTouchHelper(
    private val onJokeRemoved: (pos: Int) -> Unit,
    private val onItemMoved: (pos: Int, dest: Int) -> Unit
) : ItemTouchHelper(
    object : ItemTouchHelper.SimpleCallback(
        UP or DOWN,
        LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val pos = viewHolder.adapterPosition
            val dest = target.adapterPosition
            onItemMoved(pos, dest)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val pos = viewHolder.adapterPosition
            onJokeRemoved(pos)
        }
    }
)