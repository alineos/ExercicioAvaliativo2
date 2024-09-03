package br.edu.ifsp.dmo.mydiary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifsp.dmo.mydiary.data.dao.EntryDao
import br.edu.ifsp.dmo.mydiary.data.model.Entry
//classe responsavel por definir as propriedades do banco de dados e obter a instancia atual para edição dos dados
@Database(entities = [Entry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "app_diary.db"
        private lateinit var instance: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (!::instance.isInitialized) {
                synchronized(AppDatabase::class) {
                    instance = Room
                        .databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        )
                        .build()
                }
            }
            return instance
        }
    }
    abstract fun getEntryDao(): EntryDao
}