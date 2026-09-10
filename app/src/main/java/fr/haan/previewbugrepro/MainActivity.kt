package fr.haan.previewbugrepro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import fr.haan.previewbugrepro.ui.theme.PreviewBugReproTheme
import kotlin.reflect.KProperty

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PreviewBugReproTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PreviewBugReproTheme {
        Greeting("Android")
    }
}


object SampleScenarios {
    val loading: String = "loading"
    val error: String = "error"
}

class ReflectingPreviewParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> by lazy {
        SampleScenarios::class.members
            .asSequence()
            .filterIsInstance<KProperty<*>>()
            .mapNotNull { it.call(SampleScenarios) as? String }
    }
}
@Preview(showBackground = true)
@Composable
fun ReflectingPreview(
    @PreviewParameter(ReflectingPreviewParameterProvider::class) label: String,
) {
    Text(text = label)
}