package com.example.textparsingapp.di

import com.example.textparsingapp.data.repository.bit_manipulation.BitManipulationRepository
import com.example.textparsingapp.data.repository.bit_manipulation.BitManipulationRepositoryImpl
import com.example.textparsingapp.data.repository.text_parsing.TextParsingRepository
import com.example.textparsingapp.data.repository.text_parsing.TextParsingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindTextParsingRepository(impl: TextParsingRepositoryImpl): TextParsingRepository

    @Binds
    fun bindBitManipulationRepository(impl: BitManipulationRepositoryImpl): BitManipulationRepository
}