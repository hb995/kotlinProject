package rit.edu.gjs8472.finalproject.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import rit.edu.gjs8472.finalproject.MainActivity
import java.io.File

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecordingScreen() {
    val context = LocalContext.current
    val speechContext = context as MainActivity

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(speechContext.audioFileList) { audioFile ->
                    RecordingCard(audioFile, speechContext, context)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingCard(audioFile: File, speechContext: MainActivity, context: Context){
    val fileName by remember { mutableStateOf(audioFile.name) }
    var newFileName by remember { mutableStateOf("") }
    var isDeleteDialogOpen by remember { mutableStateOf(false) }
    var isRenameDialogOpen by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(320.dp)
            .height(120.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = fileName
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { speechContext.player.playFile(audioFile) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play record"
                    )
                }
                IconButton(
                    onClick = { isRenameDialogOpen = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Rename"
                    )
                }
                IconButton(
                    onClick = {
                        val textToShare = "Hello, check this out!"
                        shareData(context, textToShare)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share"
                    )
                }
                IconButton(
                    onClick = { isDeleteDialogOpen = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
        if (isDeleteDialogOpen) {
            AlertDialog(
                onDismissRequest = { isDeleteDialogOpen = false },
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Delete",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Divider(
                            thickness = 1.dp
                        )
                    }
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Are you sure to delete this record?",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { isDeleteDialogOpen = false }
                    ) {
                        Text(
                            text = "No",
                            color = Color.White
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            speechContext.removeAudioFile(audioFile)
                            isDeleteDialogOpen = false
                        }
                    ) {
                        Text(
                            text = "Yes",
                            color = Color.White
                        )
                    }
                }
            )
        }
        if (isRenameDialogOpen) {
            Dialog(
                onDismissRequest = { isRenameDialogOpen = false }
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentHeight()
                        .height(250.dp)
                        .width(300.dp),
                    shape = RoundedCornerShape(size = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Rename",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Divider(
                            thickness = 1.dp
                        )
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = newFileName,
                                onValueChange = { newFileName = it },
                                label = { Text("New File Name") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text
                                ),
                                singleLine = true,
                                maxLines = 1
                            )
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        if (newFileName.isNotEmpty()) {
                                            speechContext.renameAudioFile(
                                                fileName,
                                                "$newFileName.mp3"
                                            )
                                            isRenameDialogOpen = false
                                            newFileName = ""
                                        }
                                    }
                                ) {
                                    Text(
                                        text = "Rename",
                                        color = Color.White
                                    )
                                }
                                Button(
                                    onClick = {
                                        isRenameDialogOpen = false
                                        newFileName = ""
                                    }
                                ) {
                                    Text(
                                        text = "Cancel",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun shareData(context: Context, textToShare: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, textToShare)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}