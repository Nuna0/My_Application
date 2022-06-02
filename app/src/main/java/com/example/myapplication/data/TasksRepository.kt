package com.example.myapplication.data

import androidx.lifecycle.LiveData

class TasksRepository (private val tasksDao: TasksDao){
    val getAllTasks: LiveData<List<Tasks>> = tasksDao.getAllTasks()

    suspend fun addTasks(tasks: Tasks){
        tasksDao.addTasks(tasks)
    }

    suspend fun updateTask(tasks: Tasks){
        tasksDao.updateTask(tasks)
    }

    suspend fun deleteTask(tasks: Tasks){
        tasksDao.deleteTask(tasks)
    }

}