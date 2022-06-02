package com.example.myapplication.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class TaskViewModel (application:Application): AndroidViewModel(application){
    val getAllTasks: LiveData<List<Tasks>>
    private val repository: TasksRepository

    init {
        val tasksDao = TaskRoomDatabase.getDatabase(application).taskDao()
        repository = TasksRepository(tasksDao)
        getAllTasks = repository.getAllTasks
    }

    fun  addTask(tasks: Tasks){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTasks(tasks)
        }
    }

    fun updateTask(tasks: Tasks){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTask(tasks)
        }
    }

    fun deleteTask(tasks: Tasks){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTask(tasks)
        }
    }

}