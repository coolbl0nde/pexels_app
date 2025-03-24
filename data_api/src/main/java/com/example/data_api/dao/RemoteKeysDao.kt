package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data_api.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun remoteKeysById(id: String): RemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: RemoteKeys)

    @Query("DELETE FROM remote_keys WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}