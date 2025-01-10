package rit.edu.gjs8472.finalproject.data.audio

import java.io.File

interface AudioRecorderInterface {
    fun start(outputFile: File)
    fun stop()
}