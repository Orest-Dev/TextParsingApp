package com.example.textparsingapp.data

import android.content.Context
import com.example.textparsingapp.di.DispatcherIo
import com.google.gson.JsonParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import javax.inject.Inject

class LocalDataProvider@Inject constructor(
    @ApplicationContext private val appContext: Context,
    @DispatcherIo private val workDispatcher: CoroutineDispatcher,
) : DataProvider {

    override suspend fun fetchWords(): Set<String> = withContext(workDispatcher) {
        val parser = JsonParser()
        val words = parser.parse(InputStreamReader(appContext.assets.open(DICTIONARY_FILE_NAME)))
            .asJsonObject.keySet()
        words
    }
}

const val DICTIONARY_FILE_NAME = "words_dictionary.json"