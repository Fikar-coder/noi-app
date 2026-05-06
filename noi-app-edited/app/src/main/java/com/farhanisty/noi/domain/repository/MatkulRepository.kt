package com.farhanisty.noi.domain.repository

import com.farhanisty.noi.domain.model.Matkul
import kotlinx.coroutines.flow.Flow

interface MatkulRepository {
    fun getAllMatkul(): Flow<List<Matkul>>
    suspend fun getMatkulById(id: Int): Matkul?
    suspend fun insertMatkul(matkul: Matkul)
    suspend fun updateMatkul(matkul: Matkul)
    suspend fun deleteMatkul(matkul: Matkul)
    fun searchMatkul(query: String): Flow<List<Matkul>>
}
