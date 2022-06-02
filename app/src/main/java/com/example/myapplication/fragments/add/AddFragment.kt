package com.example.myapplication.fragments.add

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.TaskViewModel
import com.example.myapplication.data.Tasks
import com.example.myapplication.databinding.FragmentAddBinding
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.LocalDate
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import coil.Coil
import coil.load


@InternalCoroutinesApi
class AddFragment : Fragment() {
    private val GALLERY_REQUEST = 1
    private  var _binding: FragmentAddBinding?=null
    private  val binding get() = _binding!!
    private lateinit var mTaskViewModel:TaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentAddBinding.inflate(inflater, container, false)
        mTaskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        setHasOptionsMenu(true)
        val toolbar = binding.toolbar

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        binding.btnAddImage.setOnClickListener {
            addImage()
        }

        binding.addButton.setOnClickListener {
            addTask()
        }

        return binding.root

    }

    private fun addImage() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val bitmap:Bitmap
        val imageView = binding.image

        if (requestCode== GALLERY_REQUEST && resultCode == Activity.RESULT_OK){
            val uri: Uri? = data?.data

/*
            bitmap=MediaStore.Images.Media.getBitmap( context?.contentResolver, uri)
*/

            imageView.load(uri)

/*
            imageView.setImageBitmap(bitmap)
*/
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }


    private fun addTask() {
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        val date = LocalDate.now()
        val image = binding.image.tag as String

        if (inputCheck(title, description)){
            val task = Tasks(0, title, description, date, false, image)
            mTaskViewModel.addTask(task)
            Toast.makeText(requireContext(), "Данные сохранены", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
        Toast.makeText(requireContext(), "Мне нечего сохранять, добавьте данные",
            Toast.LENGTH_SHORT).show()}
    }

    private  fun inputCheck(title:String, description:String):Boolean{
        return  !(TextUtils.isEmpty(title)) && !(TextUtils.isEmpty(description))
    }


}

