package rit.edu.gjs8472.finalproject

import android.Manifest
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import rit.edu.gjs8472.finalproject.data.api.model.AlbumsViewModel
import rit.edu.gjs8472.finalproject.data.audio.AudioPlayer
import rit.edu.gjs8472.finalproject.data.audio.AudioRecorder
import rit.edu.gjs8472.finalproject.ui.AlbumScreen
import rit.edu.gjs8472.finalproject.ui.DrumScreen
import rit.edu.gjs8472.finalproject.ui.DrumTutorialScreen
import rit.edu.gjs8472.finalproject.ui.HomeScreen
import rit.edu.gjs8472.finalproject.ui.OnBoarding
import rit.edu.gjs8472.finalproject.ui.PianoScreen
import rit.edu.gjs8472.finalproject.ui.PianoTutorialScreen
import rit.edu.gjs8472.finalproject.ui.RecordingScreen
import rit.edu.gjs8472.finalproject.ui.theme.FinalProjectTheme
import java.io.File
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : ComponentActivity() {

    private val recorderPlayer by lazy { AudioRecorder(applicationContext) }
    val player by lazy { AudioPlayer(applicationContext) }
    var audioFileList : MutableList<File> = mutableListOf()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )
        setContent {
            FinalProjectTheme{
                val vm = AlbumsViewModel()
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val currentTitle = remember { mutableStateOf("Home") }
                val classicDesign = rememberSaveable{ mutableStateOf(true) }
                val currentRoute = remember { mutableStateOf("home") }

                val savedPaths = loadSavedFilePaths()
                audioFileList.addAll(savedPaths.map { File(it) })

                val context = LocalContext.current
                var timer: Timer? by remember { mutableStateOf(null) }
                var recordingTime by remember { mutableStateOf(0L) }
                var lockedRecordingTime by remember { mutableStateOf(0L) }
                var isRecording by remember { mutableStateOf(false) }

                DisposableEffect(Unit) {
                    val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                        requestedOrientation = when (destination.route) {
                            "piano" -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                            "drums" -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                            else -> ActivityInfo.SCREEN_ORIENTATION_USER
                        }
                        currentTitle.value = when (destination.route) {
                            "start" -> ""
                            "home" -> "Home"
                            "piano" -> ""
                            "drums" -> ""
                            "piano_tutorial" -> "Piano Tutorial"
                            "drum_tutorial" -> "Drum Tutorial"
                            "recording" -> "Recordings"
                            "albumScreen/{albumId}" -> "Album Details"
                            else -> "App"
                        }
                        currentRoute.value = when (destination.route) {
                            "start" -> "start"
                            "piano" -> "piano"
                            "drums" -> "drums"
                            else -> ""
                        }
                    }
                    navController.addOnDestinationChangedListener(listener)
                    onDispose {
                        navController.removeOnDestinationChangedListener(listener)
                    }
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            Row(modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)){
                                Text("Song App",
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Divider()
                            NavigationDrawerItem(
                                label = {
                                    Row{
                                        Text(text = "Home")
                                    }
                                },
                                selected = false,
                                onClick = { navController.navigate("home") }
                            )
                            Divider()
                            NavigationDrawerItem(
                                label = {
                                    Row{
                                        Text(text = "Piano")
                                    }
                                },
                                selected = false,
                                onClick = { navController.navigate("piano") }
                            )
                            Divider()
                            NavigationDrawerItem(
                                label = {
                                    Row{
                                        Text(text = "Drums")
                                    }
                                },
                                selected = false,
                                onClick = { navController.navigate("drums") }
                            )
                            Divider()
                            NavigationDrawerItem(
                                label = {
                                    Row{
                                        Text(text = "Piano Tutorials")
                                    }
                                },
                                selected = false,
                                onClick = { navController.navigate("piano_tutorial") }
                            )
                            Divider()
                            NavigationDrawerItem(
                                label = {
                                    Row{
                                        Text(text = "Drum Tutorials")
                                    }
                                },
                                selected = false,
                                onClick = { navController.navigate("drum_tutorial") }
                            )
                            Divider()
                            NavigationDrawerItem(
                                label = {
                                    Row{
                                        Text(text = "Recordings")
                                    }
                                },
                                selected = false,
                                onClick = { navController.navigate("recording") }
                            )
                        }
                    }
                ){
                    Scaffold(
                        topBar = {
                            if (currentRoute.value != "start") {
                                TopAppBar(
                                    title = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Text(currentTitle.value)
                                            if (currentRoute.value == "piano" || currentRoute.value == "drums") {
                                                OutlinedButton(
                                                    onClick = {
                                                        val timestamp = System.currentTimeMillis()
                                                        val fileName = "Record_${timestamp}.mp3"
                                                        val newAudioFile = File(
                                                            getExternalFilesDir("Records"),
                                                            fileName
                                                        )
                                                        if (!isRecording) {
                                                            recorderPlayer.start(newAudioFile)
                                                            audioFileList.add(newAudioFile)
                                                            saveFilePath(newAudioFile.absolutePath)
                                                            if (timer == null) {
                                                                timer = Timer()
                                                                timer?.scheduleAtFixedRate(
                                                                    0L,
                                                                    1000L
                                                                ) {
                                                                    recordingTime += 1000
                                                                }
                                                            }
                                                            isRecording = true
                                                        }
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.PlayArrow,
                                                        contentDescription = "Start",
                                                    )
                                                }
                                                OutlinedButton(
                                                    onClick = {
                                                        if (timer != null) {
                                                            timer?.cancel()
                                                            timer = null
                                                        }
                                                        recorderPlayer.stop()
                                                        lockedRecordingTime = recordingTime
                                                        recordingTime = 0L
                                                        isRecording = false
                                                        Toast.makeText(
                                                            context,
                                                            "Saved",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Close,
                                                        contentDescription = "Stop"
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.apply {
                                                    if (isClosed) open() else close()
                                                }
                                            }
                                        }) {
                                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                                        }
                                    },
                                    actions = {
                                        if (currentRoute.value == "piano" || currentRoute.value == "drums") {
                                            Button(onClick = {
                                                classicDesign.value = !classicDesign.value
                                            }) {
                                                Text(if (classicDesign.value) "Classic" else "Modern")
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "start",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("start") {
                                OnBoarding(navController)
                            }
                            composable("home") {
                                HomeScreen(navController, vm)
                            }
                            composable("albumScreen/{albumId}") { backStackEntry ->
                                val albumId = backStackEntry.arguments?.getString("albumId") ?: ""
                                AlbumScreen(albumId, vm)
                            }
                            composable("piano") {
                                PianoScreen(classicDesign.value)
                            }
                            composable("drums") {
                                DrumScreen(classicDesign.value)
                            }
                            composable("piano_tutorial") {
                                PianoTutorialScreen()
                            }
                            composable("drum_tutorial") {
                                DrumTutorialScreen()
                            }
                            composable("recording") {
                                RecordingScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    private val RECORDINGS_FILE_NAME = "recordings.txt"

    private fun getRecordingsFile() = File(filesDir, RECORDINGS_FILE_NAME)

    private fun loadSavedFilePaths(): List<String> {
        return getRecordingsFile().takeIf { it.exists() }?.readLines() ?: emptyList()
    }

    private fun saveFilePath(path: String) {
        getRecordingsFile().appendText("$path\n")
    }

    private fun saveFilePathsToFile() {
        getRecordingsFile().writeText(audioFileList.joinToString("\n"))
    }

    fun removeAudioFile(file: File) {
        if (file.exists() && file.delete()) {
            audioFileList.remove(file)
            saveFilePathsToFile()
        }
    }

    fun renameAudioFile(oldFileName: String, newFileName: String) {
        audioFileList.find { it.name == oldFileName }?.let { oldFile ->
            File(oldFile.parent, newFileName).also { newFile ->
                if (oldFile.renameTo(newFile)) {
                    audioFileList.remove(oldFile)
                    audioFileList.add(newFile)
                    saveFilePathsToFile()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveFilePathsToFile()
    }
}