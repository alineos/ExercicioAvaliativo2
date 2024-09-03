package br.edu.ifsp.dmo.mydiary.data.model


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


//classe padrão para definir os atributos da classe e campos da tabela do banco
@Entity(tableName = "tb_entrys")
class Entry (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "note")
    var note: String = "",

    @ColumnInfo(name = "local")
    var local: String = "",

    dateEntryDate: LocalDate = LocalDate.now(),
    dateEntryTime: LocalTime = LocalTime.now()
    )
{
    @ColumnInfo(name = "date")
    var date: String = ""

    @ColumnInfo(name = "time")
    var time: String = ""

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    @Ignore
   private val formatterTime = DateTimeFormatter.ofPattern("HH:mm")



    init {
        date = formatter.format(dateEntryDate)
        time =formatterTime.format(dateEntryTime)
    }

}