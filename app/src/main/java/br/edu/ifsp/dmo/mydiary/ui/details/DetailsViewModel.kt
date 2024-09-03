package br.edu.ifsp.dmo.mydiary.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import br.edu.ifsp.dmo.mydiary.data.model.Entry
import br.edu.ifsp.dmo.mydiary.data.repository.EntryRepository
import java.time.LocalDateTime
import java.time.LocalTime

class DetailsViewModel(private val repository: EntryRepository):ViewModel() {
    private var entryId: Long = -1
    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved
    private val _isUpdate = MutableLiveData<Boolean>()
    val isUpdate: LiveData<Boolean> = _isUpdate
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private val _note = MutableLiveData<String>()
    val note: LiveData<String> = _note
    private val _dateEntryString = MutableLiveData<String>()
    val dateEntryString: LiveData<String> = _dateEntryString
    private val _timeEntryString = MutableLiveData<String>()
    val timeEntryString: LiveData<String> = _timeEntryString
    private val _local = MutableLiveData<String>()
    val local: LiveData<String> = _local



    init {
        _isUpdate.value = false
    }

    fun saveEntry(title: String, note: String, dateEntryDate: LocalDate, dateEntryTime: LocalTime, local: String){
        //cria um objeto novo para salvar ou alterar
        val entry = Entry(title = title, note = note, dateEntryDate = dateEntryDate, dateEntryTime = dateEntryTime, local = local)
        //verifica se é um update ou insert para salvar os dados no banco
        if (_isUpdate.value == false) {
            viewModelScope.launch {
                _saved.value = repository.insert(entry)
            }
        } else {
            entry.id = entryId
            viewModelScope.launch {
                _saved.value = repository.update(entry)
                entryId = -1
            }
        }
    }
    fun showEvent(id: Long) {
        viewModelScope.launch {
            //obtem os dados do banco
            val entry = repository.findById(id)
            if (entry != null){//caso não seja um novo insert seta os dados para os campos observaveis para alterar os compontentes visuais da tela
                entryId = entry.id
                _note.value = entry.note
                _title.value = entry.title
                _dateEntryString.value = entry.date
                _timeEntryString.value = entry.time
                _local.value = entry.local
                _isUpdate.value = true
            }
        }
    }





}