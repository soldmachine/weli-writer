package com.szoldapps.weliwriter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.szoldapps.weliwriter.ui.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PopularMoviesMainPage()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text (text = "Hello $name!")
}

@Preview(name = "Newline greeting")
@Composable
fun PreviewNewlineGreeting() {
    Greeting("world\nwith a line break")
}

@Preview(showBackground = true)
@Composable
fun PopularMoviesMainPage() {
    val click = ClickCount()
    Column {
        TopAppBar(title = { Text(text = "Popular Movies") })
        Button(
            content = { Text(text = "test ${click.count}") },
            onClick = { click.count.value++ }
        )
//        LazyColumnFor(items = List(3) { it }, itemContent = {
//            NewsStory()
//        })
    }
}

class ClickCount {
    var count = mutableStateOf(10)
}

@Composable
fun NewsStory() {
    val image = imageResource(R.drawable.header)

    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val imageModifier = Modifier
                .preferredHeightIn(maxHeight = 180.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))

            Image(
                asset = image,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.preferredHeight(16.dp))

            Text(
                text = "A day wandering through the sandhills in Shark Fin Cove, and a few of the sights I saw",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = typography.h6
            )
            Text("Davenport, California", style = typography.body2)
            Text("December 2018", style = typography.body2)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsStory()
}


//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    WeliWriterTheme {
//        Greeting("Android")
//    }
//}