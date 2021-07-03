package com.example.wordsnsynonyms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.Navigation
import com.example.synonymspluswords.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SearchWordsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_word_list, container, false)
        val searchBar = view.findViewById(R.id.searchView) as SearchView

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val action = SearchWordsFragmentDirections.wordListFindSynonym(query.toString())
                Navigation.findNavController(view).navigate(action)
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

        val addWordBtn = view.findViewById(R.id.addWordBtn) as FloatingActionButton
        addWordBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.wordList_addword)
        }

        return view
    }



}