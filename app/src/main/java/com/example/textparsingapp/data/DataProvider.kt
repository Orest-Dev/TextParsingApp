package com.example.textparsingapp.data

interface DataProvider {

    suspend fun fetchWords() : Set<String>
}