package br.edu.ifsp.dmo.mydiary.data.repository

import android.content.Context
import br.edu.ifsp.dmo.mydiary.data.database.AppDatabase
import br.edu.ifsp.dmo.mydiary.data.model.Entry

class EntryRepository(context:Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getEntryDao()

    //classe responsavel pelos comandos para o banco de dados

    suspend fun insert(entry: Entry): Boolean{
        return dao.create(entry) > 0
    }
    suspend fun update(entry: Entry):Boolean{
        return dao.update(entry)>0
    }
    suspend fun remove(entry: Entry):Boolean{
        return dao.delete(entry) > 0
    }
    suspend fun findById (id:Long): Entry{
        return dao.getEntry(id)
    }
    suspend fun findAll(): List<Entry>{
        return dao.getAll()
    }
}