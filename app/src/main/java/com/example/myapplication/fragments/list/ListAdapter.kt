package com.example.myapplication.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.TaskViewModel
import com.example.myapplication.data.Tasks
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.new_header.view.*
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var taskList = mutableListOf<Tasks>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent,
            false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    fun setData(task: List<Tasks>){
        this.taskList = task as MutableList<Tasks>
        notifyDataSetChanged()
    }
    

    override fun getItemCount(): Int {
        return taskList.size
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title)
        val date:TextView = itemView.findViewById(R.id.date)
        private val checkBox:CheckBox = itemView.findViewById(R.id.checkbox)
        private val image: ImageView = itemView.findViewById(R.id.image)

        fun bind(tasks: Tasks) {
            title.text = tasks.title
            date.text = tasks.date.toString()
            checkBox.tag=tasks.isDone
            image.tag= tasks.image

            checkBox.setOnClickListener{
                checkBox.tag=!tasks.isDone}


            itemView.recLayout.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(tasks)
                itemView.findNavController().navigate(action)
            }
        }


    }
}