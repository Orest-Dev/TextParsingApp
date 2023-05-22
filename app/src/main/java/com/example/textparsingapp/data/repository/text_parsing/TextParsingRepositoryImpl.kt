package com.example.textparsingapp.data.repository.text_parsing

import com.example.textparsingapp.data.DataProvider
import com.example.textparsingapp.data.Dictionary
import com.example.textparsingapp.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TextParsingRepositoryImpl @Inject constructor(
    @DispatcherDefault private val workDispatcher: CoroutineDispatcher,
    private val api: DataProvider
) : TextParsingRepository {

    private val _dictionaryState = MutableStateFlow(
        Dictionary(
            words = null
        )
    )

    private val _longestDictionaryWordLength = MutableStateFlow(DEFAULT_LONGEST_WORD_VALUE)

    override suspend fun initDictionary() {
        withContext(workDispatcher) {
            _dictionaryState.update {
                it.copy(words = api.fetchWords())
            }
            findLongestDictionaryWord()
        }
    }

    private suspend fun findLongestDictionaryWord() {
        if (_dictionaryState.value.words == null) {
            initDictionary()
        }
        var longestWordLength: Int = DEFAULT_LONGEST_WORD_VALUE
        _dictionaryState.value.words?.forEach {
            if (it.length > longestWordLength) {
                longestWordLength = it.length
            }
        }
        _longestDictionaryWordLength.value = longestWordLength
    }

    override suspend fun findAllWords(input: String): String = withContext(workDispatcher) {
        if (_dictionaryState.value.words == null) {
            initDictionary()
        }
        val foundedWords = mutableSetOf<String>()
        val inputString = input.trim().lowercase()
        val longestDictionaryWordLength = _longestDictionaryWordLength.value

        for (firstIndex in inputString.indices) {
            val lastWordLookUpIndex =
                if ((inputString.length) <= longestDictionaryWordLength) {
                    inputString.length
                } else {
                    val charsLeft = inputString.length - firstIndex
                    if (charsLeft > longestDictionaryWordLength) {
                        firstIndex + longestDictionaryWordLength
                    } else {
                        inputString.length
                    }
                }

            for (lastIndex in firstIndex until lastWordLookUpIndex) {

                val checkString: String = inputString.substring(firstIndex, lastIndex + 1)
                _dictionaryState.value.words?.let { dictionary ->
                    if (dictionary.contains(checkString)) {
                        foundedWords.add(checkString)
                    }
                }
            }
        }

        val builder = StringBuilder()
        for (word in foundedWords) {
            builder.append(" $word")
        }

        builder.toString()
    }
}


const val DEFAULT_LONGEST_WORD_VALUE = 31