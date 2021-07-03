package com.example.wordsnsynonyms.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.synonymspluswords.R
import com.example.wordsnsynonyms.presenter.WordsPresenter
import com.example.wordsnsynonyms.view.adapters.SynonymsInputListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AddNewWordFragment : Fragment(), SynonymsInputListAdapter.OnItemClickListener {

    @Inject
    lateinit var wordsPresenter: WordsPresenter

    private val synonymsList: MutableList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_new_word, container, false)

        val newWordEditText = view.findViewById(R.id.keywordInputLayoutET) as EditText
        val synonymEditText = view.findViewById(R.id.synonymInputLayoutET) as EditText
        val addSynonymBtn = view.findViewById(R.id.addSynonymBtn) as Button
        val btnAddWord = view.findViewById(R.id.submitBtn) as Button

        val mRecyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        val mLayoutManager = GridLayoutManager(activity,3)
        val adapter = SynonymsInputListAdapter(synonymsList, this)

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.layoutManager = mLayoutManager;
        mRecyclerView.adapter = adapter;

        btnAddWord.setOnClickListener {
            if(synonymEditText.text.length>1){
                synonymsList.add(synonymEditText.text.toString())
            }
            if(synonymsList.size > 0 && newWordEditText.text.length > 1){
                CoroutineScope(Dispatchers.IO).launch {
                    wordsPresenter.addWordToStorage(newWordEditText.text.toString(), synonymsList.distinct() as MutableList)
                }
                Navigation.findNavController(view).navigate(R.id.addWord_wordList)
            }
            else {
                Toast.makeText(activity, "Please enter a word and at least one synonym", Toast.LENGTH_SHORT).show()
            }
        }

        addSynonymBtn.setOnClickListener {
            if(synonymEditText.text.length > 1){
                if(synonymsList.size < 9){
                    synonymsList.add(synonymEditText.text.toString())
                    adapter.notifyDataSetChanged()
                    synonymEditText.text.clear()
                }
                else{
                    Toast.makeText(activity, "Maximum 9 synonyms allowed", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(activity, "Please enter a proper synonym", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(activity, "Item clicked", Toast.LENGTH_SHORT).show()
    }


}