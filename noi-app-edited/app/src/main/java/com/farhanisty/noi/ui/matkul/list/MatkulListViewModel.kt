package com.farhanisty.noi.ui.matkul.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanisty.noi.domain.model.Matkul
import com.farhanisty.noi.domain.usecase.DeleteMatkulUseCase
import com.farhanisty.noi.domain.usecase.GetAllMatkulUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatkulListUiState(
    val matkulList: List<Matkul> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

@HiltViewModel
class MatkulListViewModel @Inject constructor(
    private val getAllMatkulUseCase: GetAllMatkulUseCase,
    private val deleteMatkulUseCase: DeleteMatkulUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val uiState: StateFlow<MatkulListUiState> = combine(
        getAllMatkulUseCase(),
        _searchQuery
    ) { list, query ->
        val filtered = if (query.isBlank()) list
        else list.filter {
            it.nama.contains(query, ignoreCase = true) ||
            it.kode.contains(query, ignoreCase = true) ||
            it.dosen.contains(query, ignoreCase = true)
        }
        MatkulListUiState(matkulList = filtered, searchQuery = query)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MatkulListUiState(isLoading = true)
    )

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }

    fun deleteMatkul(matkul: Matkul) {
        viewModelScope.launch { deleteMatkulUseCase(matkul) }
    }
}
