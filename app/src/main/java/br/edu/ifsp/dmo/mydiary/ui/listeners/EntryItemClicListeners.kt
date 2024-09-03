package br.edu.ifsp.dmo.mydiary.ui.listeners

interface EntryItemClicListeners {
    //define a estrutura para o click do botão Open
    fun clickOpen(position: Int)
    //define a estrutura para o click do botão delete
    fun clickDelete(position: Int)

}