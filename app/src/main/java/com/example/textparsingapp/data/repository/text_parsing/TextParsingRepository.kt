package com.example.textparsingapp.data.repository.text_parsing

interface TextParsingRepository {

    suspend fun initDictionary()

    suspend fun findAllWords(input: String) : String
}