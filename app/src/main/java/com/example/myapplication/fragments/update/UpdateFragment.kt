package com.example.myapplication.fragments.update

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.AnyRes
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.myapplication.R
import com.example.myapplication.data.TaskViewModel
import com.example.myapplication.data.Tasks
import com.example.myapplication.databinding.FragmentUpdateBinding
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.coroutines.InternalCoroutinesApi
import android.graphics.drawable.Drawable
import android.net.Uri


@InternalCoroutinesApi
class UpdateFragment : Fragment() {
    private  val args by navArgs<UpdateFragmentArgs>()
    private  var _binding: FragmentUpdateBinding?=null
    private  val binding get() = _binding!!
    private lateinit var mTaskViewModel: TaskViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        mTaskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        binding.updateDescriptions.setText(args.currentTask.description)
        binding.updateTitles.setText(args.currentTask.title)
        binding.updateImage.setImageBitmap(args.currentTask.image as Bitmap)

        setHasOptionsMenu(true)
        val toolbar = binding.toolbar

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        toolbar.inflateMenu(R.menu.delete)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> deleteTask()
            }
            true
        }
        toolbar.inflateMenu(R.menu.options_menu)
        /*toolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.changeImage ->
            }
        }*/

        binding.updateButton.setOnClickListener {
            updateItem()
        }

        return binding.root
    }


    private fun updateItem(){
        val title = updateTitles.text.toString()
        val description = updateDescriptions.text.toString()
        val image = updateImage.tag as String
        if(inputCheck(title, description)){
            val updateTask = Tasks(args.currentTask.id, title, description, args.currentTask.date, args.currentTask.isDone, image)
            mTaskViewModel.updateTask(updateTask)
            Toast.makeText(requireContext(), "Данные обновлены", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
        }
    }

    private  fun inputCheck(title:String, description:String):Boolean{
        return  !(TextUtils.isEmpty(title)) && !(TextUtils.isEmpty(description))
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