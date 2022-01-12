package com.example.zlatko.beaches.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.zlatko.beaches.base.navigation.AppNavigation
import com.example.zlatko.beaches.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavigation(
                    startActivity = { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it))) }
                )
            }
        }
    }
}