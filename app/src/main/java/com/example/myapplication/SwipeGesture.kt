package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.fragments.list.ListFragment
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

abstract class SwipeGesture(context: Context): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean { return false   }

     override fun onChildDraw(
         c: Canvas,
         recyclerView: RecyclerView,
         viewHolder: RecyclerView.ViewHolder,
         dX: Float,
         dY: Float,
         actionState: Int,
         isCurrentlyActive: Boolean
     ) {

         RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
             .addSwipeLeftActionIcon(R.drawable.ic_delete).addSwipeLeftBackgroundColor(R.color.red).create().decorate()

         super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
     }

}