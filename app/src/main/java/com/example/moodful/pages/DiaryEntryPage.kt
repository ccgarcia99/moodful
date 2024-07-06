package com.example.moodful.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.moodful.ui.theme.MoodfulTheme
import com.example.moodful.ui.theme.MyTopBar

/*TODO:
    1. Create UI Layout for DiaryEntry
    2. Implement file-handling routines for DiaryEntry
* */

@Composable
fun DiaryEntryPage(modifier: Modifier = Modifier) {
    MoodfulTheme {
        Scaffold(
            topBar = { MyTopBar() }
        ) { innerPadding ->
            Surface(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                color = MaterialTheme.colorScheme.surface
            ) {
                val constraints = ConstraintSet {
                    val moodRow = createRefFor("moodRow")
                    val notesField = createRefFor("notesField")

                    constrain(moodRow) {
                        top.linkTo(parent.top, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }

                    constrain(notesField) {
                        top.linkTo(moodRow.bottom, margin = 24.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
                }
                ConstraintLayout(
                    constraintSet = constraints,
                    modifier = modifier.fillMaxSize()
                ) {
                    ColorPaletteRow(modifier = Modifier.layoutId("moodRow"))
                    TextInputContainer(modifier = Modifier.layoutId("notesField"))
                }
            }
        }
    }
}

@Composable
fun TextInputContainer(modifier: Modifier = Modifier, myTextPreview: String = "") {
    var text by remember { mutableStateOf(myTextPreview) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp), // Padding added here
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)),
            color = MaterialTheme.colorScheme.surfaceDim
        ) {
            BasicTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ColorPaletteRow(modifier: Modifier = Modifier) {
    val colors = listOf(
        Color(0xFF1E88E5), // Blue
        Color(0xFF42A5F5), // Light Blue
        Color(0xFF9C27B0), // Purple
        Color(0xFFEF5350), // Light Red
        Color(0xFFE53935)  // Red
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Adjust space between buttons
        ) {
            colors.forEach { color ->
                MoodColorButton(color = color)
            }
        }
    }
}

@Composable
fun MoodColorButton(modifier: Modifier = Modifier, color: Color) {
    Surface(
        modifier = Modifier
            .size(60.dp) // Set size directly to Surface
            .clip(CircleShape),
        color = color
    ) {
        // No content needed inside Surface
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ColorButtonPreview() {
    MoodfulTheme {
        ColorPaletteRow()
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputContainerPreview() {
    MoodfulTheme {
        TextInputContainer(
            Modifier, "This is just a preview"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DEPage() {
    DiaryEntryPage()
}
