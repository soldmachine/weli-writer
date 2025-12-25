package com.szoldapps.weliwriterkmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.szoldapps.weliwriterkmp.MainViewModel.UiState
import com.szoldapps.weliwriterkmp.appDatabase.AppDatabase
import com.szoldapps.weliwriterkmp.di.appModules
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

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
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .verticalScroll(scrollState)
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
private fun Content(
    uiState: UiState.Content,
) {
    Button(onClick = { uiState.clickAction() }) {
        Text(uiState.buttonLabel)
    }
    Text(
        text = uiState.text,
        modifier = Modifier.padding(16.dp),
    )
}
