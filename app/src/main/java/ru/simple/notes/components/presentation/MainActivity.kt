package ru.simple.notes.components.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.toArgb
import dagger.hilt.android.AndroidEntryPoint
import ru.simple.notes.ui.theme.DarkGray
import ru.simple.notes.ui.theme.NoteTheme

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTheme {
                window.navigationBarColor = DarkGray.toArgb()
                Surface(color = MaterialTheme.colors.background) {
                    MainContent()
                }
            }
        }
    }
}