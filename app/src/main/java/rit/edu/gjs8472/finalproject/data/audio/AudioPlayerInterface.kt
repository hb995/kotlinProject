package rit.edu.gjs8472.finalproject.data.audio

import java.io.File

interface AudioPlayerInterface {
    fun playFile(file: File)
    fun stop()
}