package rit.edu.gjs8472.finalproject.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import rit.edu.gjs8472.finalproject.data.api.model.AlbumX
import rit.edu.gjs8472.finalproject.data.api.model.AlbumsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, vm: AlbumsViewModel){
    LaunchedEffect(Unit, block = {
        vm.fetchAlbums()
    })

    Scaffold{ innerPadding ->
        if (vm.errorMessage.isEmpty()){
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if (vm.albumList.isEmpty()){
                    item {
                        Text(
                            text = "There are no songs :(",
                            fontSize = 30.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 12.dp)
                                .wrapContentWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedVisibility(visible = vm.loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(64.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                strokeWidth = 10.dp
                            )
                        }
                    }
                }

                items(items = vm.albumList){album ->
                    SmallFloatingActionButton(
                        onClick = {
                            navController.navigate("albumScreen/${album.idAlbum}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        CharacterImageCard(album)
                    }
                }
            }
        }
        else{
            Text(
                text = vm.errorMessage,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}

@Composable
fun CharacterImageCard(album: AlbumX){
    Card(
        shape = MaterialTheme.shapes.extraSmall
    ){
        Box {
            Row {
                GlideImage(
                    imageModel = { album.strAlbumThumb },
                    imageOptions = ImageOptions(
                        Alignment.CenterStart
                    ),
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    loading = {
                        Box(modifier = Modifier.matchParentSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    },
                    failure = {
                        Text(text = "Image request failed.")
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Album: ${album.strAlbum}",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W800,
                            letterSpacing = 0.1.em
                        )
                    )
                    Text(
                        text = "Artist: ${album.strArtist}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W200,
                            fontStyle = FontStyle.Italic
                        )
                    )
                }

            }
        }
    }
}

@Composable
fun AlbumScreen(albumId: String, vm: AlbumsViewModel) {
    val album = vm.getAlbumById(albumId)

    if (album != null) {
        AlbumDetails(album)
    } else {
        Text("Album with ID $albumId not found.")
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetails(album: AlbumX) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            GlideImage(
                imageModel = { album.strAlbumThumb },
                imageOptions = ImageOptions(
                    Alignment.CenterStart
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                failure = {
                    Text(text = "Image request failed.")
                }
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .border(2.dp, Color.LightGray)
                    .background(MaterialTheme.colorScheme.background),
            ) {
                Column(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = "Album: ${album.strAlbum}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                    Text(
                        text = album.strDescriptionEN,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}