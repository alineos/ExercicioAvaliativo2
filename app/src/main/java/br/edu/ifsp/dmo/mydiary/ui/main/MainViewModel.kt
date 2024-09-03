package br.edu.ifsp.dmo.mydiary.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.dmo.mydiary.data.model.Entry
import br.edu.ifsp.dmo.mydiary.data.repository.EntryRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: EntryRepository) : ViewModel() {
    private val _entrys = MutableLiveData<List<Entry>>()
    val entry: LiveData<List<Entry>> = _entrys

    init {
        checkDatabase()
    }

     fun checkDatabase() {
         //seta a lista do banco para uma lista "observavel" no codigo para que possa ser manipulada
        viewModelScope.launch {
            val list = repository.findAll()
            _entrys.postValue(list)
        }
    }

    fun deleteEntry(id: Long) {
        //deleta o registro do banco e atualiza a lista local
        viewModelScope.launch {
            val task= repository.findById(id)
            if (task!=null){
                repository.remove(task)
                checkDatabase()
            }
        }
    }

}
