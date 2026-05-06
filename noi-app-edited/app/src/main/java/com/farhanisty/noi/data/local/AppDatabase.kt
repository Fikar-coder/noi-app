package com.farhanisty.noi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farhanisty.noi.data.local.dao.MatkulDao
import com.farhanisty.noi.data.local.entity.MatkulEntity

@Database(entities = [MatkulEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matkulDao(): MatkulDao
}
