package com.example.data_api.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true) val photoId: Long = 0,
    val prevKey: Int?,
    val nextKey: Int?,
)
