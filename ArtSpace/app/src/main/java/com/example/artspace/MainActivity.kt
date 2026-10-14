package com.example.artspace
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                ArtSpaceLayout()
            }
        }
    }
}

@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) {
    var currentArtwork by remember { mutableStateOf(1) }
    val artworkImage = when (currentArtwork) {
        1 -> R.drawable.artwork_1
        2 -> R.drawable.artwork_2
        else -> R.drawable.artwork_3
    }
    val title = when (currentArtwork) {
        1 -> R.string.artwork_1_title
        2 -> R.string.artwork_2_title
        else -> R.string.artwork_3_title
    }
    val artist = when (currentArtwork) {
        1 -> R.string.artwork_1_artist
        2 -> R.string.artwork_2_artist
        else -> R.string.artwork_3_artist
    }
    val year = when (currentArtwork) {
        1 -> R.string.artwork_1_year
        2 -> R.string.artwork_2_year
        else -> R.string.artwork_3_year
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ArtworkWall(artworkImage, modifier = Modifier.weight(1f))
        ArtworkDescriptor(
            title = title,
            artist = artist,
            year = year,
            modifier = Modifier.widthIn(max = 400.dp)
        )
        DisplayController(onPreviousClick = {
            currentArtwork = when (currentArtwork) {
                1 -> 3
                2 -> 1
                else -> 2
            }
        }, onNextClick = {
                currentArtwork = when (currentArtwork) {
                    1 -> 2
                    2 -> 3
                    else -> 1
                }
        })
    }
}
@Composable
fun ArtworkWall(@DrawableRes artwork: Int, modifier: Modifier = Modifier) {
    Surface(shadowElevation = 8.dp, modifier = modifier) {
        Image(
            painter = painterResource(artwork),
            contentDescription = stringResource(R.string.artwork_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(32.dp)
        )
    }
}
@Composable
fun ArtworkDescriptor(
    @StringRes title: Int,
    @StringRes artist: Int,
    @StringRes year: Int,
    modifier: Modifier = Modifier
) {
    val artistText = stringResource(artist)
    val yearText = stringResource(year)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(artistText) }
                append(" ($yearText)")
            }
        )
    }
}
@Composable
fun DisplayController(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onPreviousClick, modifier = Modifier.width(140.dp)) {
            Text(stringResource(R.string.previous))
        }
        Button(onClick = onNextClick, modifier = Modifier.width(140.dp)) {
            Text(stringResource(R.string.next))
        }
    }
}
@Preview
    (showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}