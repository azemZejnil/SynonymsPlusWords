package com.example.synonymspluswords

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.wordsnsynonyms.model.WordsModel
import com.example.wordsnsynonyms.presenter.WordsPresenter
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WordsOperationsTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var model: WordsModel<MutableList<String>>
    private lateinit var presenter: WordsPresenter

    @Before
    fun setup() {
        model = WordsModel()
        presenter = WordsPresenter(model)
    }

    @Test
    fun insertWordSynonyms() = runBlockingTest {
        val keyWord = "exampleKeyword"
        val synonyms = mutableListOf("examples","synonyms")
        presenter.addWordToStorage(keyWord, synonyms)

        val supposedList = presenter.getWordsAndSynonyms(keyWord)
        val wordsList = model.getWordsList()

        assertThat(supposedList).contains("examples")
        assertThat(supposedList).contains("synonyms")
        assertThat(wordsList).contains("exampleKeyword")
    }

    @Test
    fun insertTransitiveWordSynonyms() = runBlockingTest {
        var keyWord = "exampleKeyword2"
        var synonyms = mutableListOf("examples2","synonyms2")
        presenter.addWordToStorage(keyWord, synonyms)

        keyWord = "synonyms2"
        synonyms = mutableListOf("transitiveSynonym","SecondSynonym")
        presenter.addWordToStorage(keyWord, synonyms)

        val supposedList = presenter.getWordsAndSynonyms("transitiveSynonym")
        assertThat(supposedList).contains("exampleKeyword2")
        assertThat(supposedList).contains("synonyms2")
    }

    @Test
    fun getWordsWithTransitiveSynonyms() = runBlockingTest {
        val r1 = "r1"
        val r1Syns = mutableListOf("syn1", "syn2","syn3")

        val r2 = "syn1"
        val r2Syns = mutableListOf("syn4", "syn5","syn6")

        val r3 = "syn2"
        val r3Syns = mutableListOf("syn7", "syn8","syn9")

        val r4 = "syn7"
        val r4Syns = mutableListOf("syn10", "syn11","syn12")

        presenter.addWordToStorage(r1, r1Syns)
        presenter.addWordToStorage(r2, r2Syns)
        presenter.addWordToStorage(r3, r3Syns)
        presenter.addWordToStorage(r4, r4Syns)


        // syn7 is both synonym that should return corresponding words, but it is also a word that should return synonyms
        val supposedList = presenter.getWordsAndSynonyms("syn7")
        assertThat(supposedList).contains("syn2")
        assertThat(supposedList).contains("r1")
        assertThat(supposedList).contains("syn10")
        assertThat(supposedList).contains("syn11")
        assertThat(supposedList).contains("syn12")
    }
}