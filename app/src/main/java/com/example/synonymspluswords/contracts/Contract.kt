package com.example.wordsnsynonyms.contracts


interface Contract {

    interface WordsViewContract {
        fun getWordsAndSynonyms(word: String)
    }

    interface WordsPresenterContract {
        suspend fun addWordToStorage(word: String, synonyms: MutableList<String>)
        suspend fun getWordsAndSynonyms(word: String) : MutableList<String>?
    }

    interface WordsModelContract {
        suspend fun addWord(word: String, synonyms: MutableList<String>)
        suspend fun returnWordsAndSynonyms(word: String) : MutableList<String>?
    }

}