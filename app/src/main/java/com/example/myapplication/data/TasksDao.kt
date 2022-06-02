package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTasks(vararg tasks: Tasks)

    @Delete
    suspend fun deleteTask(tasks: Tasks)

    @Query("SELECT * FROM task_table ORDER BY date ASC")
     fun getAllTasks(): LiveData<List<Tasks>>

    @Update
    suspend fun updateTask(tasks: Tasks)
}