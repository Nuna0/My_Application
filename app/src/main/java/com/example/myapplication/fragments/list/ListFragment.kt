package com.example.myapplication.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.SwipeGesture
import com.example.myapplication.data.TaskViewModel
import com.example.myapplication.data.Tasks
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.fragments.update.UpdateFragmentArgs
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
class ListFragment : Fragment() {
    private  var _binding: FragmentListBinding?=null
    private  val binding get() = _binding!!
    @InternalCoroutinesApi
    private lateinit var mTaskViewModel: TaskViewModel
    private  val args by navArgs<ListFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentListBinding.inflate(inflater, container, false)

        val adapter = ListAdapter()
        mTaskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        mTaskViewModel.getAllTasks.observe(viewLifecycleOwner, Observer { task ->
            adapter.setData(task)
        })

        val recycler = binding.recyclerNewTasks

        recycler.adapter = adapter

        val swipeGesture = object : SwipeGesture(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        deleteTask()
                    }
                }
            }
        }


        val recyclerDone = binding.recyclerDoneTasks


        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recycler)

        recycler.layoutManager = LinearLayoutManager(requireContext())



        binding.buttonFloat.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }


        return binding.root
    }

    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Да"){_,_ ->
            mTaskViewModel.deleteTask(args.currentTask)
            Toast.makeText(requireContext(), "Заметка успешно удалена", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)}
        builder.setNegativeButton("Нет"){_,_ ->
        }
        builder.setTitle("Удалить заметку")
        builder.setMessage("Вы уверены, что хотите удалить заметку")
        builder.create().show()
    }


}
