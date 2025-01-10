package rit.edu.gjs8472.finalproject.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import rit.edu.gjs8472.finalproject.data.audio.YouTubePlayer

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PianoTutorialScreen(){
    Scaffold {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            YouTubePlayer(
                youtubeVideoId = "vCQ3Zvv87EQ",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            YouTubePlayer(
                youtubeVideoId = "P7podHrt8iQ",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            YouTubePlayer(
                youtubeVideoId = "OoEIr_XclVs",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            YouTubePlayer(
                youtubeVideoId = "P7podHrt8iQ",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            YouTubePlayer(
                youtubeVideoId = "_omz21DIl4M",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            YouTubePlayer(
                youtubeVideoId = "cz1xYYQfFBQ",
                lifecycleOwner = LocalLifecycleOwner.current
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrumTutorialScreen(){
    Scaffold {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            YouTubePlayer(
                youtubeVideoId = "Av0JmyLtTZ4",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            YouTubePlayer(
                youtubeVideoId = "2xc55G7vVxg",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            YouTubePlayer(
                youtubeVideoId = "7iyevNn1cZQ",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            YouTubePlayer(
                youtubeVideoId = "z7aA6d9AGmo",
                lifecycleOwner = LocalLifecycleOwner.current
            )
        }
    }
}