package com.example.textparsingapp.data.repository.bit_manipulation

import com.example.textparsingapp.di.DispatcherDefault
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitManipulationRepositoryImpl @Inject constructor(
    @DispatcherDefault private val workDispatcher: CoroutineDispatcher
) : BitManipulationRepository {

    override suspend fun calculateBits(firstNumber: String, secondNumber: String): String =
        withContext(workDispatcher) {
            if (firstNumber.length > LONG_MAXIMUM_LENGTH || secondNumber.length > LONG_MAXIMUM_LENGTH) {
                countBigIntegerBits(firstNumber, secondNumber)
            } else {
                countLongBits(firstNumber, secondNumber)
            }
        }

    private fun countLongBits(firstNumber: String, secondNumber: String): String {
        val xor = firstNumber.toLong() xor secondNumber.toLong()
        var count = 0
        var currentNumber = xor
        while (currentNumber > 0) {
            currentNumber = currentNumber and currentNumber - 1
            count++
        }
        return count.toString()
    }

    private fun countBigIntegerBits(
        firstLongIntegerString: String,
        secondLongIntegerString: String
    ): String {
        val firstBigInteger = BigInteger(firstLongIntegerString)
        val secondBigInteger = BigInteger(secondLongIntegerString)

        val xorResult = firstBigInteger.xor(secondBigInteger)
        val bitCount = xorResult.bitCount()

        return bitCount.toString()
    }
}

const val LONG_MAXIMUM_LENGTH = 18