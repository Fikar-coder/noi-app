package com.farhanisty.noi.domain.model

data class Matkul(
    val id: Int = 0,
    val nama: String,
    val kode: String,
    val sks: Int,
    val semester: Int,
    val dosen: String,
    val deskripsi: String = ""
)
