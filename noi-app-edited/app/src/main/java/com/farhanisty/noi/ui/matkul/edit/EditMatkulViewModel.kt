package com.farhanisty.noi.ui.matkul.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanisty.noi.domain.model.Matkul
import com.farhanisty.noi.domain.usecase.GetMatkulByIdUseCase
import com.farhanisty.noi.domain.usecase.UpdateMatkulUseCase
import com.farhanisty.noi.ui.matkul.add.MatkulFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class EditMatkulEvent {
    object Success : EditMatkulEvent()
    data class Error(val message: String) : EditMatkulEvent()
}

@HiltViewModel
class EditMatkulViewModel @Inject constructor(
    private val getMatkulByIdUseCase: GetMatkulByIdUseCase,
    private val updateMatkulUseCase: UpdateMatkulUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val matkulId: Int = checkNotNull(savedStateHandle["matkulId"])
    private var originalMatkul: Matkul? = null

    private val _state = MutableStateFlow(MatkulFormState(isLoading = true))
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<EditMatkulEvent>()
    val event = _event.asSharedFlow()

    init {
        loadMatkul()
    }

    private fun loadMatkul() {
        viewModelScope.launch {
            val matkul = getMatkulByIdUseCase(matkulId)
            if (matkul == null) {
                _event.emit(EditMatkulEvent.Error("Mata kuliah tidak ditemukan"))
                return@launch
            }
            originalMatkul = matkul
            _state.value = MatkulFormState(
                nama = matkul.nama,
                kode = matkul.kode,
                sks = matkul.sks.toString(),
                semester = matkul.semester.toString(),
                dosen = matkul.dosen,
                deskripsi = matkul.deskripsi,
                isLoading = false
            )
        }
    }

    fun onNamaChange(v: String) = _state.apply { value = value.copy(nama = v, namaError = null) }
    fun onKodeChange(v: String) = _state.apply { value = value.copy(kode = v, kodeError = null) }
    fun onSksChange(v: String) = _state.apply { value = value.copy(sks = v, sksError = null) }
    fun onSemesterChange(v: String) = _state.apply { value = value.copy(semester = v, semesterError = null) }
    fun onDosenChange(v: String) = _state.apply { value = value.copy(dosen = v, dosenError = null) }
    fun onDeskripsiChange(v: String) = _state.apply { value = value.copy(deskripsi = v) }

    fun updateMatkul() {
        val s = _state.value
        var valid = true
        var st = s.copy()

        if (s.nama.isBlank()) { st = st.copy(namaError = "Nama tidak boleh kosong"); valid = false }
        if (s.kode.isBlank()) { st = st.copy(kodeError = "Kode tidak boleh kosong"); valid = false }
        if (s.sks.toIntOrNull() == null || s.sks.toInt() !in 1..6) { st = st.copy(sksError = "SKS harus 1-6"); valid = false }
        if (s.semester.toIntOrNull() == null || s.semester.toInt() !in 1..14) { st = st.copy(semesterError = "Semester harus 1-14"); valid = false }
        if (s.dosen.isBlank()) { st = st.copy(dosenError = "Nama dosen tidak boleh kosong"); valid = false }

        _state.value = st
        if (!valid) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                updateMatkulUseCase(Matkul(
                    id = matkulId,
                    nama = s.nama.trim(),
                    kode = s.kode.trim().uppercase(),
                    sks = s.sks.toInt(),
                    semester = s.semester.toInt(),
                    dosen = s.dosen.trim(),
                    deskripsi = s.deskripsi.trim()
                ))
                _event.emit(EditMatkulEvent.Success)
            } catch (e: Exception) {
                _event.emit(EditMatkulEvent.Error(e.message ?: "Terjadi kesalahan"))
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
