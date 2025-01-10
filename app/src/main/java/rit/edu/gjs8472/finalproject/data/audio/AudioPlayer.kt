package rit.edu.gjs8472.finalproject.data.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayer(
    private val context: Context
): AudioPlayerInterface {

    private var player: MediaPlayer? = null

    override fun playFile(file: File) {
        player?.stop()
        player?.release()
        player = MediaPlayer.create(context, file.toUri()).apply {
            setOnCompletionListener {
                it.release()
            }
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}