package com.farhanisty.noi.ui.matkul

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.farhanisty.noi.ui.matkul.add.MatkulFormState

@Composable
fun MatkulForm(
    state: MatkulFormState,
    onNamaChange: (String) -> Unit,
    onKodeChange: (String) -> Unit,
    onSksChange: (String) -> Unit,
    onSemesterChange: (String) -> Unit,
    onDosenChange: (String) -> Unit,
    onDeskripsiChange: (String) -> Unit,
    onSave: () -> Unit,
    saveLabel: String = "Simpan",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = state.nama,
            onValueChange = onNamaChange,
            label = { Text("Nama Mata Kuliah *") },
            isError = state.namaError != null,
            supportingText = state.namaError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true
        )
        OutlinedTextField(
            value = state.kode,
            onValueChange = onKodeChange,
            label = { Text("Kode Matkul *") },
            isError = state.kodeError != null,
            supportingText = state.kodeError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Contoh: CS101") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
            singleLine = true
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = state.sks,
                onValueChange = onSksChange,
                label = { Text("SKS *") },
                isError = state.sksError != null,
                supportingText = state.sksError?.let { { Text(it) } },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            OutlinedTextField(
                value = state.semester,
                onValueChange = onSemesterChange,
                label = { Text("Semester *") },
                isError = state.semesterError != null,
                supportingText = state.semesterError?.let { { Text(it) } },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }
        OutlinedTextField(
            value = state.dosen,
            onValueChange = onDosenChange,
            label = { Text("Nama Dosen *") },
            isError = state.dosenError != null,
            supportingText = state.dosenError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true
        )
        OutlinedTextField(
            value = state.deskripsi,
            onValueChange = onDeskripsiChange,
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Text(saveLabel)
            }
        }
    }
}
