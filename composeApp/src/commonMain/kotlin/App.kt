import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import se.denke.ui.core.platformResource
import se.denke.ui.designsystem.JVideoPlayer
import se.denke.ui.designsystem.JVideoSettings
import se.denke.ui.designsystem.JVideoSource
import se.denke.ui.designsystem.component.JSearch
import se.denke.ui.designsystem.component.jTextOf
import se.denke.ui.designsystem.theme.JDesignSystem

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    JDesignSystem {
        var showContent by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            JVideoPlayer(
                modifier = Modifier.fillMaxWidth().height(400.dp),
                video = JVideoSource.LocalFile(platformResource("raw/sample.mp4")),
                //video = JVideoSource.VideoUrl("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
                settings = JVideoSettings(showController = true)
            )
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource("drawable/compose_multiplatform.xml"), null)
                    Text("Compose: $greeting")
                    var searchQuery by remember {
                        mutableStateOf("")
                    }
                    JSearch(value = searchQuery, label = jTextOf("Search"), onValueChanged = {
                        searchQuery = it
                    })
                }
            }
        }
    }
}