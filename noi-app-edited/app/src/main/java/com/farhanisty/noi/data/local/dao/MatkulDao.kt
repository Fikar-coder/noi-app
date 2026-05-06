package com.farhanisty.noi.data.local.dao

import androidx.room.*
import com.farhanisty.noi.data.local.entity.MatkulEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatkulDao {
    @Query("SELECT * FROM matkul ORDER BY semester ASC, nama ASC")
    fun getAllMatkul(): Flow<List<MatkulEntity>>

    @Query("SELECT * FROM matkul WHERE id = :id")
    suspend fun getMatkulById(id: Int): MatkulEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatkul(matkul: MatkulEntity)

    @Update
    suspend fun updateMatkul(matkul: MatkulEntity)

    @Delete
    suspend fun deleteMatkul(matkul: MatkulEntity)

    @Query("SELECT * FROM matkul WHERE nama LIKE '%' || :query || '%' OR kode LIKE '%' || :query || '%'")
    fun searchMatkul(query: String): Flow<List<MatkulEntity>>
}
