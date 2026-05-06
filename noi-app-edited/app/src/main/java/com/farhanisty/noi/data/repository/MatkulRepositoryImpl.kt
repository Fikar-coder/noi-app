package com.farhanisty.noi.data.repository

import com.farhanisty.noi.data.local.dao.MatkulDao
import com.farhanisty.noi.data.local.entity.MatkulEntity
import com.farhanisty.noi.domain.model.Matkul
import com.farhanisty.noi.domain.repository.MatkulRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MatkulRepositoryImpl @Inject constructor(
    private val dao: MatkulDao
) : MatkulRepository {

    override fun getAllMatkul(): Flow<List<Matkul>> =
        dao.getAllMatkul().map { list -> list.map { it.toDomain() } }

    override suspend fun getMatkulById(id: Int): Matkul? =
        dao.getMatkulById(id)?.toDomain()

    override suspend fun insertMatkul(matkul: Matkul) =
        dao.insertMatkul(matkul.toEntity())

    override suspend fun updateMatkul(matkul: Matkul) =
        dao.updateMatkul(matkul.toEntity())

    override suspend fun deleteMatkul(matkul: Matkul) =
        dao.deleteMatkul(matkul.toEntity())

    override fun searchMatkul(query: String): Flow<List<Matkul>> =
        dao.searchMatkul(query).map { list -> list.map { it.toDomain() } }

    private fun MatkulEntity.toDomain() = Matkul(id, nama, kode, sks, semester, dosen, deskripsi)
    private fun Matkul.toEntity() = MatkulEntity(id, nama, kode, sks, semester, dosen, deskripsi)
}
