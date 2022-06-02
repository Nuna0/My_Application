package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Tasks::class],version = 5,exportSchema = false)
@TypeConverters(Converters::class)
  abstract  class TaskRoomDatabase : RoomDatabase() {

    abstract  fun taskDao(): TasksDao

    companion object{
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null
        @InternalCoroutinesApi
        fun getDatabase(context: Context): TaskRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance!= null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java,
                    "task_table"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }



}