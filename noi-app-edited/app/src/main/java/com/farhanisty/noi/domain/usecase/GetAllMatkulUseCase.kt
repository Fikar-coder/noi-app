package com.farhanisty.noi.domain.usecase

import com.farhanisty.noi.domain.model.Matkul
import com.farhanisty.noi.domain.repository.MatkulRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMatkulUseCase @Inject constructor(
    private val repository: MatkulRepository
) {
    operator fun invoke(): Flow<List<Matkul>> = repository.getAllMatkul()
}
