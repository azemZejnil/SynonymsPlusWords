package com.example.wordsnsynonyms.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.synonymspluswords.R

class SynonymsInputListAdapter(
    private val wordList: MutableList<String>,
    private var listener: OnItemClickListener
) :
    RecyclerView.Adapter<SynonymsInputListAdapter.WordListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.word_item,
            parent, false)
        return WordListViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        val currentItem = wordList[position]
        holder.textView1.text = currentItem
    }
    override fun getItemCount() = wordList.size
    inner class WordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val textView1: TextView = itemView.findViewById(R.id.word_text)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}