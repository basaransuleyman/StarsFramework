package com.starFramework.feature.presentation

import android.webkit.WebView
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.starFramework.feature.core.Constants.LOAD_WEB_URL
import com.starFramework.feature.core.presentation.TextPopup
import com.starFramework.feature.domain.model.Star

@Composable
fun MainScreen() {
    val viewModel: StarViewModel = hiltViewModel()
    val isSkyFull by viewModel.isSkyFull.collectAsState()
    val stars by viewModel.stars.collectAsState(initial = emptyList())
    var showPopup by remember { mutableStateOf(false) }

    LaunchedEffect(isSkyFull) {
        showPopup = isSkyFull
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    loadUrl(LOAD_WEB_URL)
                }
            },
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "${stars.size}",
            color = Color.DarkGray,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StarActions(
                onAddSmallStar = { viewModel.addStar(Star.createSmallStar()) },
                onAddBigStar = { viewModel.addStar(Star.createBigStar()) },
                onResetSky = viewModel::resetSky,
                isSkyFull = isSkyFull
            )

            if (showPopup) {
                TextPopup(message = "Sky is full", onDismiss = { showPopup = false })
            }

        }
    }
}


@Composable
fun StarActions(
    onAddSmallStar: () -> Unit,
    onAddBigStar: () -> Unit,
    onResetSky: () -> Unit,
    isSkyFull: Boolean
) {
    Button(
        onClick = {
            if (!isSkyFull) {
                onAddSmallStar()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Yellow,
            contentColor = Color.Black
        )
    ) {
        Text("Small Star")
    }

    Button(
        onClick = {
            if (!isSkyFull) {
                onAddBigStar()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.Black
        )
    ) {
        Text("Big Star")
    }

    Button(
        onClick = { onResetSky() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.Black
        )
    ) {
        Text("Reset")
    }
}
