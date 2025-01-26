package com.lucashomelab.fintium.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lucashomelab.fintium.data.local.dao.AssetDao
import com.lucashomelab.fintium.data.local.entity.AssetEntity

@Database(
    entities = [AssetEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao

    companion object {
        const val DATABASE_NAME = "fintium_db"
    }
}

