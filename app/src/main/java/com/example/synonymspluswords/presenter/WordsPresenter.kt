package com.example.synonymspluswords.presenter

import com.example.wordsnsynonyms.contracts.Contract
import com.example.synonymspluswords.model.WordsModel
import javax.inject.Inject

class WordsPresenter
@Inject
constructor(private val wordsModel: WordsModel<MutableList<String>>)
    : Contract.WordsPresenterContract
    {
        override suspend fun addWordToStorage(word: String, synonyms: MutableList<String>) {
            wordsModel.addWord(word,synonyms)
        }

        override suspend fun getWordsAndSynonyms(word: String): MutableList<String>? {
            return wordsModel.returnWordsAndSynonyms(word)
        }

    }