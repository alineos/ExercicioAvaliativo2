package br.edu.ifsp.dmo.mydiary.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo.mydiary.data.repository.EntryRepository
import br.edu.ifsp.dmo.mydiary.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.mydiary.ui.adapter.EntryAdapter
import br.edu.ifsp.dmo.mydiary.ui.details.DetailsActivity
import br.edu.ifsp.dmo.mydiary.ui.listeners.EntryItemClicListeners
import br.edu.ifsp.dmo.mydiary.util.Constant

class MainActivity : AppCompatActivity(), EntryItemClicListeners {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = EntryAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = MainViewModelFactory(EntryRepository(applicationContext))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        //Configura o Recycler View principal
        setupRecyclerView()
        //observa os objetos de dados do banco para que quando haja alteração no banco reflita nos objetos e vice versa
        setupObservers()
        //escuta as ações dos botões da tela
        setupListeners()

    }
    override fun onResume() {
        super.onResume()
        //carrega as informações do banco para uma lista de controle interna
        viewModel.checkDatabase()
    }

    private fun setupListeners() {
        //listener palra o botão add para abrir a tela de detalhes e permitir a edição ou criação de uma nova entrada para o diario
        binding.buttonAdd.setOnClickListener{
            val mIntent = Intent(this,DetailsActivity::class.java)
            startActivity(mIntent)
        }

    }

    private fun setupObservers() {
        //observa a lista de dados do banco e atualiza na lista da tela
        viewModel.entry.observe(this, Observer { adapter.submitDataset(it) })


    }

    private fun setupRecyclerView() {
        //adiciona o layout ao recycler view
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        //adiciona o adapter ao recyclerview
        binding.recyclerview.adapter = adapter

    }

    override fun clickOpen(position: Int) {
        //define a variavel entrada para a edição
        val entry = adapter.getDatasetItem(position)
        //define a activity de edição de entradas
        val mIntent = Intent(this,DetailsActivity::class.java)
        //define o Id da entrada para que possa ser carregada para edição na tela de detalhes
        mIntent.putExtra(Constant.ENTRY_ID,entry.id)
        //inicia os detalhes para edição
        startActivity(mIntent)
    }

    override fun clickDelete(position: Int) {
        //define a variavel de entrada para ser removida de base
        val entry = adapter.getDatasetItem(position)
        //chama o metodo para exlusão do banco de dados
        viewModel.deleteEntry(entry.id)
    }
}