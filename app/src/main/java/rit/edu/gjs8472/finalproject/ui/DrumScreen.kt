package rit.edu.gjs8472.finalproject.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rit.edu.gjs8472.finalproject.data.audio.drumPads
import rit.edu.gjs8472.finalproject.data.audio.playSound

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DrumScreen(classicDesign: Boolean) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        drumPads.chunked(2).forEach { rowPads ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowPads.forEach { pad ->
                    DrumPadButton(context, pad.soundResourceId, pad.label, classicDesign)
                }
            }
        }
    }
}

@Composable
fun DrumPadButton(context: Context, soundResourceId: Int, label: String, classicDesign: Boolean) {
    val containerColor = if (classicDesign) Color.DarkGray else Color.LightGray
    Button(
        onClick = { playSound(context, soundResourceId) },
        modifier = Modifier
            .aspectRatio(1f)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = Color.White)
    ) {
        Text(
            text = label,
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(color = Color.Black, blurRadius = 2f)
            )
        )
    }
}