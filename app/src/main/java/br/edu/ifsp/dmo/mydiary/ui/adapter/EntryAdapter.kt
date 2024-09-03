package br.edu.ifsp.dmo.mydiary.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo.mydiary.R
import br.edu.ifsp.dmo.mydiary.data.model.Entry
import br.edu.ifsp.dmo.mydiary.databinding.ItemEntryBinding
import br.edu.ifsp.dmo.mydiary.ui.listeners.EntryItemClicListeners

class EntryAdapter(private val listener: EntryItemClicListeners) :
    RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    //define a lista do recycler view como uma lista vazia
    private var dataset: List<Entry> = emptyList()

//implementa os metodos necessarios para o uso do recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entry, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = dataset[position]

        holder.binding.imageEdit.setOnClickListener{
            listener.clickOpen(position)
            true
        }
        holder.binding.imageDelete.setOnClickListener{
            listener.clickDelete(position)
        }
        holder.binding.txtTitle.text = entry.title
       }
//atualiza a lista que sera observada pelo recycler view
    fun submitDataset(data: List<Entry>){
        dataset= data
        this.notifyDataSetChanged()
    }
    //retorna o dado de um item especifico da lista
    fun getDatasetItem(position: Int): Entry{
        return dataset[position]
    }


    //implementa o componente do item
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemEntryBinding = ItemEntryBinding.bind(view)
    }
}