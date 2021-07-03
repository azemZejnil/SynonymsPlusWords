package com.example.wordsnsynonyms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.synonymspluswords.R
import com.example.wordsnsynonyms.contracts.Contract
import com.example.wordsnsynonyms.presenter.WordsPresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class FindSynonymsFragment
    : Fragment(), Contract.WordsViewContract {

    @Inject
    lateinit var wordsPresenter: WordsPresenter

    private var synonymsList: MutableList<String>? = null
    private val args: FindSynonymsFragmentArgs by navArgs()

    private lateinit var synonymsTxtV : TextView
    private lateinit var keywordTxtV : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_synonyms, container, false)
        val addWordBtn = view.findViewById(R.id.addWordBtn) as Button
        val searchWordBtn = view.findViewById(R.id.searchWordBtn) as Button

        synonymsTxtV = view.findViewById(R.id.synonyms)
        keywordTxtV = view.findViewById(R.id.keyword)

        addWordBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.findSynonyms_newWord)
        }

        searchWordBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.findSynonyms_wordList)
        }

        val keyword = args.askedWord
        getWordsAndSynonyms(keyword)

        return view
    }

    override fun getWordsAndSynonyms(word: String) {
        CoroutineScope(IO).launch {
            synonymsList = wordsPresenter.getWordsAndSynonyms(word)
            val synonymsString: String = synonymsList!!.joinToString(separator = ", ")
            withContext(Main){
                synonymsTxtV.text= synonymsString
                keywordTxtV.text = word
            }
        }
    }
}