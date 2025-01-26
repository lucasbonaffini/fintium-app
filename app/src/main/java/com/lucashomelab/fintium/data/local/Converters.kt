package com.lucashomelab.fintium.data.local

import androidx.room.TypeConverter
import com.lucashomelab.fintium.domain.model.AssetType

class Converters {
    @TypeConverter
    fun fromAssetType(value: AssetType): String = value.name

    @TypeConverter
    fun toAssetType(value: String): AssetType = AssetType.valueOf(value)
}
