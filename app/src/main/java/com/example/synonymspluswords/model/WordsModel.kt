package com.example.synonymspluswords.model

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
    private val synonymsToWords = HashMap<String, MutableList<String>>()


    override suspend fun addWord(word: String, synonyms: MutableList<String>) {
        if(synonymsToWords.containsKey(word)){
            val secondWord = synonymsToWords[word]!!.get(0)
            synonyms.forEach{
                transitiveSynonymsToWords.put(it, mutableListOf(word, secondWord))
            }
        }
        else {
            synonyms.forEach() {
                if(synonymsToWords.containsKey(it)){
                    val listOfWords = synonymsToWords[it]!!.plus(word)
                    synonymsToWords.put(it, listOfWords as MutableList<String>)
                }
                else{
                    synonymsToWords.put(it, mutableListOf(word))
                }
            }
        }
        if(wordsToSynonyms.containsKey(word)){
            val listOfSynonyms = synonymsToWords[word]!!.plus(synonyms)
            synonymsToWords.put(word, listOfSynonyms as MutableList<String>)
        }
        else{
            wordsToSynonyms.put(word, synonyms)
        }
    }

    override suspend fun returnWordsAndSynonyms(word: String): MutableList<String>? {
        var results: MutableList<String> = mutableListOf()

        if(wordsToSynonyms.containsKey(word))
            results = results.plus(wordsToSynonyms[word]!!) as MutableList<String>
        if(transitiveSynonymsToWords.containsKey(word))
            results = results.plus(transitiveSynonymsToWords[word]!!) as MutableList<String>
        if(synonymsToWords.containsKey(word))
            results = results.plus(synonymsToWords[word]!!) as MutableList<String>

        if(results.size < 1)
            results = results.plus("No results found") as MutableList<String>

        return results
    }

    fun getWordsList(): MutableSet<String>{
        return wordsToSynonyms.keys
    }
}