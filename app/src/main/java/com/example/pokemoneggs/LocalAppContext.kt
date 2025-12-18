package com.example.pokemoneggs

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppContext = staticCompositionLocalOf<Context> {
    error("LocalAppContext not provided")
}
