package com.farhanisty.noi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matkul")
data class MatkulEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val kode: String,
    val sks: Int,
    val semester: Int,
    val dosen: String,
    val deskripsi: String = ""
)
