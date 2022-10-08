package com.example.zlatko.beaches

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import coil.annotation.ExperimentalCoilApi
import com.example.zlatko.beaches.ui.navigation.AppNavigation
import com.example.zlatko.beaches.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
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