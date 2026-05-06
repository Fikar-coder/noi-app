package com.farhanisty.noi.ui.matkul.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanisty.noi.domain.model.Matkul
import com.farhanisty.noi.domain.usecase.AddMatkulUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatkulFormState(
    val nama: String = "",
    val kode: String = "",
    val sks: String = "",
    val semester: String = "",
    val dosen: String = "",
    val deskripsi: String = "",
    val namaError: String? = null,
    val kodeError: String? = null,
    val sksError: String? = null,
    val semesterError: String? = null,
    val dosenError: String? = null,
    val isLoading: Boolean = false
)

sealed class AddMatkulEvent {
    object Success : AddMatkulEvent()
    data class Error(val message: String) : AddMatkulEvent()
}

@HiltViewModel
class AddMatkulViewModel @Inject constructor(
    private val addMatkulUseCase: AddMatkulUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MatkulFormState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<AddMatkulEvent>()
    val event = _event.asSharedFlow()

    fun onNamaChange(v: String) = _state.apply { value = value.copy(nama = v, namaError = null) }
    fun onKodeChange(v: String) = _state.apply { value = value.copy(kode = v, kodeError = null) }
    fun onSksChange(v: String) = _state.apply { value = value.copy(sks = v, sksError = null) }
    fun onSemesterChange(v: String) = _state.apply { value = value.copy(semester = v, semesterError = null) }
    fun onDosenChange(v: String) = _state.apply { value = value.copy(dosen = v, dosenError = null) }
    fun onDeskripsiChange(v: String) = _state.apply { value = value.copy(deskripsi = v) }

    fun saveMatkul() {
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
                addMatkulUseCase(Matkul(
                    nama = s.nama.trim(),
                    kode = s.kode.trim().uppercase(),
                    sks = s.sks.toInt(),
                    semester = s.semester.toInt(),
                    dosen = s.dosen.trim(),
                    deskripsi = s.deskripsi.trim()
                ))
                _event.emit(AddMatkulEvent.Success)
            } catch (e: Exception) {
                _event.emit(AddMatkulEvent.Error(e.message ?: "Terjadi kesalahan"))
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
