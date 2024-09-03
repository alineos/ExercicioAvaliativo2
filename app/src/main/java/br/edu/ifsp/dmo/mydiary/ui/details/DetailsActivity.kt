package br.edu.ifsp.dmo.mydiary.ui.details

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.mydiary.R
import br.edu.ifsp.dmo.mydiary.data.repository.EntryRepository
import br.edu.ifsp.dmo.mydiary.databinding.ActivityEntryDatailsBinding
import br.edu.ifsp.dmo.mydiary.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.mydiary.util.Constant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DetailsActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityEntryDatailsBinding
    private lateinit var viewModel: DetailsViewModel
    private var dateEntry: LocalDate = LocalDate.now()
    private var timeEntry: LocalTime = LocalTime.now()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryDatailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = DetailsViewModelFactory(EntryRepository(applicationContext))
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
        //lê os registros do Bundle passado da tela main para a tela atual
        handleBundle()
        //define os dados para os compontes da tela
        setupUi()
        //escuta as ações dos botões da tela
        setupListeners()
        //observa os objetos de dados do banco para que quando haja alteração no banco reflita nos objetos e vice versa
        setupObservers()

    }

    private fun setupObservers() {
        //observa se os dados foram incluidos no banco de dados
        viewModel.saved.observe(this, Observer {
            if(it){
                Toast.makeText(this,"Entrada salva com sucesso!",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Erro ao  salvar entrada!",Toast.LENGTH_SHORT).show()
            }
        })
        //observa se o registro é um registro sendo editado, caso sim altera o texto do botão
        viewModel.isUpdate.observe(this,Observer{
            if(it){
                binding.buttonSave.text = "Salvar Alterações"
            }
        })
        //observa se o objeto possui titulo e altera o texto no componte da tela
        viewModel.title.observe(this,Observer{
            binding.editTitle.setText(it)
        })
        //observa se o objeto possui nota  e altera o texto no componte da tela
        viewModel.note.observe(this, Observer {
            binding.editNote.setText(it)
        })
        //observa se o objeto possui local e altera o texto no componte da tela
        viewModel.local.observe(this, Observer {
            binding.editLocal.setText(it)
        })
        //observa se o objeto possui data e altera o texto no componte da tela
        viewModel.dateEntryString.observe(this, Observer {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            dateEntry=LocalDate.parse(it,formatter)
            setupUi()
        })
        //observa se o objeto possui hora e altera o texto no componte da tela
        viewModel.timeEntryString.observe(this, Observer {
            val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
            timeEntry = LocalTime.parse(it,formatterTime)
            setupUi()
        })


    }

    private fun setupListeners() {
        //inicia o componente dataPicker quando o botão é clicado para selecionar a data
        binding.buttonEntryDate.setOnClickListener{
            initDatePickerDialog()
        }
        //inicia o componente timePicker quando o botão é clicado para selecionar a hora
        binding.buttonEntryDateTime.setOnClickListener{
            initiTimePickerDialog()

        }
        //salva os dados no banco
        binding.buttonSave.setOnClickListener{
            salveEntry()

        }



    }

    private fun salveEntry() {
        //obtem os dados dos compontentes visuais da tela
        val title = binding.editTitle.text.toString()
        val note = binding.editNote.text.toString()
        val local = binding.editLocal.text.toString()

        //valida se os dados foram preenchidos
        if (title.isEmpty()||title.isBlank()){
            Toast.makeText(this, "Título da entrada é obrigatório",
                Toast.LENGTH_SHORT).show()
        }else {
            if(note.isEmpty()||note.isBlank())
                Toast.makeText(this, "Nota da entrada é obrigatório",
                    Toast.LENGTH_SHORT).show()
            else {
                if (local.isBlank()||local.isEmpty()){
                    Toast.makeText(this, "local da entrada é obrigatório",
                        Toast.LENGTH_SHORT).show()
                }else{
                    //salva os dados
                    viewModel.saveEntry(title,note,dateEntry,timeEntry,local)
                }

            }
        }
    }

    private fun initiTimePickerDialog() {
        //inicia o timePicker para selecionar hora
        TimePickerDialog(this,this ,timeEntry.hour,timeEntry.minute,true).show()
    }

    private fun initDatePickerDialog() {
        //inicia do datePicker para selecionar a data
        DatePickerDialog(this,this,dateEntry.year,dateEntry.monthValue,dateEntry.dayOfMonth).show()
    }

    private fun setupUi() {
        //inicia o visual dos botões de data e hora de acordo com o que esta no objeto
        val str = "${dateEntry.dayOfMonth}/${dateEntry.monthValue}/${dateEntry.year}"
        val strTime = "${timeEntry.hour}:${timeEntry.minute}"
        binding.buttonEntryDate.text = str
        binding.buttonEntryDateTime.text = strTime
    }

    private fun handleBundle() {
        //verifica os dados do bundle e caso esteja preenchido define o objeto de entrada
        if (intent.hasExtra(Constant.ENTRY_ID)) {
            val id = intent.getLongExtra(Constant.ENTRY_ID, -1)
            viewModel.showEvent(id)
        }
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, mounth: Int, dayOfMounth: Int) {
        //define a data para a entrada no objeto
        dateEntry = LocalDate.of(year,mounth,dayOfMounth)
        setupUi()
    }

    override fun onTimeSet(timePicker: TimePicker, hour: Int, minute: Int) {
        //define a hora para a entrada no objeto
        timeEntry = LocalTime.of(hour,minute)
    }
}