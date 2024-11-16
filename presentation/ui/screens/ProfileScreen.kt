// presentation/ui/screens/ProfileScreen.kt
package com.example.lazyrow.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Здесь можно добавить элементы профиля, например, аватар, имя пользователя и т.д.
        Text(text = "Профиль", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Обработать действие */ }) {
            Text(text = "Изменить профиль")
        }
    }
}