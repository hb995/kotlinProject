package rit.edu.gjs8472.finalproject.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rit.edu.gjs8472.finalproject.data.audio.keys
import rit.edu.gjs8472.finalproject.data.audio.playSound

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PianoScreen(classicDesign: Boolean) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .horizontalScroll(scrollState)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            keys.forEach { key ->
                PianoKeyButton(context, key.soundResourceId, key.label, key.isWhite, classicDesign)
            }
        }
    }
}

@Composable
fun PianoKeyButton(context: Context, soundResourceId: Int, label: String, isWhite: Boolean, classicDesign: Boolean) {
    val backgroundColor = if (classicDesign) (if (isWhite) Color.White else Color.Black) else (if (!isWhite) Color.White else Color.Black)
    val textColor = if (classicDesign) (if (isWhite) Color.Black else Color.White) else (if (!isWhite) Color.Black else Color.White)
    val keyWidth = if (isWhite) 75.dp else 40.dp
    val keyHeight = if (isWhite) 300.dp else 180.dp

    Button(
        onClick = { playSound(context, soundResourceId) },
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(keyWidth)
            .height(keyHeight)
            .background(backgroundColor)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = textColor)
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = textColor,
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}