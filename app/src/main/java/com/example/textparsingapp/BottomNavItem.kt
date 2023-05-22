package com.example.textparsingapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon: ImageVector, var screen_route:String){

    object TextParsing : BottomNavItem("Text Parsing", Icons.Default.Search,"text_parsing")
    object BitManipulation: BottomNavItem("Bit Manipulation",Icons.Default.Build,"bit_manipulation")
}