package com.stonetree.retrofit.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stonetree.retrofit.example.RetrofitUiState.Loaded
import com.stonetree.retrofit.example.RetrofitUiState.Loading
import com.stonetree.retrofit.example.ui.theme.RetrofitExampleTheme

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ping by vm.ping.collectAsStateWithLifecycle()
            RetrofitExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (ping) {
                        Loading -> Text(
                            text = "Loading...",
                        )

                        Loaded -> Text(
                            text = "Loaded",
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.ping()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitExampleTheme {
        Greeting("Android")
    }
}