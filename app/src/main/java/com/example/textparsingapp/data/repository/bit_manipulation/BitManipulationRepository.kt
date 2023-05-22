package com.example.textparsingapp.data.repository.bit_manipulation

interface BitManipulationRepository {

    suspend fun calculateBits(firstNumber: String, secondNumber: String) : String
}