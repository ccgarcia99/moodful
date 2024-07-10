package com.example.moodful.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moodful.ColorViewModel
import com.example.moodful.ui.theme.MoodfulTheme
import com.example.moodful.ui.theme.ReusableTopAppBar
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DiaryEntryPage(
    colorViewModel: ColorViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }
    val selectedColor by colorViewModel.selectedColor.collectAsState()
    val timestamp = remember { getCurrentTimestamp() }

    MoodfulTheme {
        Scaffold(
            topBar = { ReusableTopAppBar("Create new entry", navController) }
        ) { innerPadding ->
            Surface(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .consumeWindowInsets(innerPadding) // Take up all available space
                        .imePadding() // This will push the content up when the keyboard appears
                ) {
                    TimeStamp(timeStamp = timestamp)
                    ControlRow(
                        selectedColor = selectedColor,
                        showDialog = showDialog
                    )
                    TextEntry(
                        modifier = Modifier.weight(1f),
                        onTextChanged = { /* You can still use this if needed */ }
                    )
                }

                if (showDialog.value) {
                    ColorPickerDialog(
                        showDialog = showDialog,
                        colorViewModel = colorViewModel,
                        previousColor = selectedColor
                    )
                }
            }
        }
    }
}

fun getCurrentTimestamp(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(Date())
}

@Composable
fun TimeStamp(
    timeStamp: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp)),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 1.dp,
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Date: $timeStamp",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

// Color Picker Functions
@Composable
fun ControlRow(
    selectedColor: Color,
    showDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ColorPickerPrompter(showDialog = showDialog)
        CurrentColorMood(colorWays = selectedColor, modifier = Modifier)
        Spacer(modifier = Modifier.weight(1f))
        SaveEntry()
    }
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
    )
}

@Composable
private fun ColorPickerPrompter(showDialog: MutableState<Boolean>) {
    TextButton(
        onClick = { showDialog.value = true }
    ) {
        Text(
            text = "Pick a color",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun CurrentColorMood(
    modifier: Modifier = Modifier,
    colorWays: Color
) {
    Box(
        modifier = modifier
            .size(33.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(colorWays)
        )
    }
}

@Composable
private fun SaveEntry() {
    TextButton(onClick = { /*TODO*/ }) {
        Text(
            text = "Save",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun ColorPickerDialog(
    showDialog: MutableState<Boolean>,
    colorViewModel: ColorViewModel,
    previousColor: Color
) {
    val colorController = rememberColorPickerController()
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text("Done")
            }
        },
        title = {
            Text(
                text = ".moodful",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CurrentColorMood(colorWays = previousColor)
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(300.dp),
                    controller = colorController,
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        colorViewModel.updateColor(colorEnvelope.color)
                    },
                    initialColor = previousColor
                )
                Text(
                    text = "pick a color that best describes your mood",
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        modifier = Modifier.padding(16.dp) // Ensure proper padding around the dialog
    )
}


@Composable
fun TextEntry(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue()) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    onTextChanged(it.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                ),
                placeholder = { Text("What's on your mind?") },
                singleLine = false,
            )

            Text(
                text = "${text.text.length} characters",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun UIComponent(modifier: Modifier = Modifier) {
    val mockNavController = rememberNavController()
    MoodfulTheme {
        Surface(
            modifier.fillMaxSize()
        ) {
            DiaryEntryPage(
                navController = mockNavController,
                colorViewModel = ColorViewModel()
            )
        }
    }
}