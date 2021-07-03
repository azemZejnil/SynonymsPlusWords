package com.example.wordsnsynonyms.model

import com.example.wordsnsynonyms.contracts.Contract
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WordsModel<T : MutableList<String>>
@Inject
constructor() : Contract.WordsModelContract
{
    private val wordsToSynonyms = HashMap<String, MutableList<String>>()
    private val transitiveSynonymsToWords = HashMap<String, MutableList<String>>()
    private val synonymsToWords = HashMap<String, String>()


    override suspend fun addWord(word: String, synonyms: MutableList<String>) {
        if(synonymsToWords.containsKey(word)){
            val secondWord = synonymsToWords[word]
            synonyms.forEach{
                transitiveSynonymsToWords.put(it, mutableListOf(word, secondWord!!))
            }
        }
        else {
            synonyms.forEach() {
                synonymsToWords.put(it, word)
            }
        }
        wordsToSynonyms.put(word, synonyms)
    }

    override suspend fun returnWordsAndSynonyms(word: String): MutableList<String>? {
        return when {
            wordsToSynonyms.containsKey(word) && transitiveSynonymsToWords.containsKey(word) -> {
                val wordsToSynonymsList = wordsToSynonyms[word]
                val transitiveSynonymsToWords = transitiveSynonymsToWords[word]
                val comb = wordsToSynonymsList!!.plus(transitiveSynonymsToWords!!) as MutableList
                comb
            }
            wordsToSynonyms.containsKey(word) && synonymsToWords.containsKey(word) -> {
                val wordsToSynonymsList = wordsToSynonyms[word]
                val synonymsToWords = synonymsToWords[word]
                val comb = wordsToSynonymsList!!.plus(synonymsToWords!!) as MutableList
                comb
            }
            wordsToSynonyms.containsKey(word) -> {
                wordsToSynonyms[word]
            }
            transitiveSynonymsToWords.containsKey(word) -> {
                transitiveSynonymsToWords[word]
            }
            synonymsToWords.containsKey(word) -> {
                val result = synonymsToWords[word]
                mutableListOf(result.toString())
            }
            else -> {
                mutableListOf("No results found")
            }
        }
    }

    fun getWordsList(): MutableSet<String>{
        return wordsToSynonyms.keys
    }
}