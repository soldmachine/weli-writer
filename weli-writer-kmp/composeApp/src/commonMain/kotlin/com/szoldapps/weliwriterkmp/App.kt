package com.szoldapps.weliwriterkmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.szoldapps.weliwriterkmp.MainViewModel.UiState
import com.szoldapps.weliwriterkmp.appDatabase.AppDatabase
import com.szoldapps.weliwriterkmp.di.appModules
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import weliwriterkmp.composeapp.generated.resources.Res
import weliwriterkmp.composeapp.generated.resources.compose_multiplatform

@Composable
internal fun App(appDatabase: AppDatabase) = MaterialTheme {
    KoinApplication(
        application = {
            modules(modules = appModules(appDatabase))
        }
    ) {
        AppContent()
    }
}


@Composable
@Preview
fun AppContent(
    viewModel: MainViewModel = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            is UiState.Content -> {
                Content(uiState as UiState.Content)
            }

            is UiState.Error -> {
                Text("Error")
            }

            is UiState.Loading -> {
                Text("Loading")
            }
        }

    }
}

@Composable
private fun ColumnScope.Content(
    uiState: UiState.Content,
) {
    var showContent by remember { mutableStateOf(false) }
    Button(onClick = { showContent = !showContent }) {
        Text(uiState.buttonLabel)
    }
    AnimatedVisibility(showContent) {
        val greeting = remember { Greeting().greet() }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(painterResource(Res.drawable.compose_multiplatform), null)
            Text("Compose: $greeting")
        }
    }
    Text(
        text = uiState.text,
        modifier = Modifier.padding(16.dp),
    )
}
