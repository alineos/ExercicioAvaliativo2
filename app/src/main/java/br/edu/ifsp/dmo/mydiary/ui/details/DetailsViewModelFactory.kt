package br.edu.ifsp.dmo.mydiary.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.mydiary.data.repository.EntryRepository

class DetailsViewModelFactory (private val repository: EntryRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("View model desconhecido")
    }
}