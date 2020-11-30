package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

import com.example.midexam.Task

@Database(entities = [ Task::class ], version=1)
@TypeConverters(TaskTypeConverter::class)

abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

