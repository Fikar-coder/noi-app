package com.farhanisty.noi.domain.usecase

import com.farhanisty.noi.domain.model.Matkul
import com.farhanisty.noi.domain.repository.MatkulRepository
import javax.inject.Inject

class GetMatkulByIdUseCase @Inject constructor(
    private val repository: MatkulRepository
) {
    suspend operator fun invoke(id: Int): Matkul? = repository.getMatkulById(id)
}
