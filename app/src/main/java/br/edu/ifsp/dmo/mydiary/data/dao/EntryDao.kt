package br.edu.ifsp.dmo.mydiary.data.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.dmo.mydiary.data.model.Entry
//define o padrão para os metodos de manipulação de dados
@Dao
interface EntryDao {
    @Insert
    suspend fun create(entry: Entry):Long
    @Query("SELECT * FROM tb_entrys ORDER BY date")
    suspend fun getAll():List<Entry>
    @Query("SELECT* FROM tb_entrys WHERE id = :id ORDER BY date")
    suspend fun getEntry(id: Long): Entry
    @Update
    suspend fun update(entry: Entry): Int
    @Delete
    suspend fun delete(entry: Entry): Int

}