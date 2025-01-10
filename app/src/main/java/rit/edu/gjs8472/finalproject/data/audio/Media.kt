package rit.edu.gjs8472.finalproject.data.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

fun playSound(context: Context, soundResourceId: Int) {
    val mediaPlayer = MediaPlayer.create(context, soundResourceId)
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener {
        it.release()
    }
}

@Composable
fun YouTubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
){

    androidx.compose.ui.viewinterop.AndroidView(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp)),
        factory = {
            YouTubePlayerView(context = it).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer){
                        youTubePlayer.cueVideo(youtubeVideoId, 0f)
                    }
                })
            }
        }
    )
}
