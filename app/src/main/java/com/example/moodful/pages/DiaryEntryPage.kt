package com.example.moodful.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodful.DiaryViewModel
import com.example.moodful.ui.theme.MoodfulTheme
import com.example.moodful.ui.theme.ReusableTopAppBar
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryEntryPage(
    diaryViewModel: DiaryViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }
    val selectedColor by diaryViewModel.selectedColor.collectAsState()
    val (currentDate, currentTime) = remember { getCurrentDateAndTime() }
    var selectedRating by remember { mutableStateOf<Int?>(null) }
    var diaryText by remember { mutableStateOf(TextFieldValue()) }

    MoodfulTheme {
        Scaffold(
            topBar = { ReusableTopAppBar("Create new entry", navController) }
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding()
            ) {
                Column(
                    modifier = modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    DateTimeDisplay(date = currentDate, time = currentTime)
                    ControlRow(
                        selectedColor = selectedColor,
                        showDialog = showDialog,
                        onSaveClick = {
                            diaryViewModel.insertDiaryEntry(
                                date = currentDate,
                                time = currentTime,
                                rating = selectedRating ?: 0,
                                color = selectedColor,
                                text = diaryText.text
                            )
                            navController.popBackStack() // Go back to the previous screen
                        }
                    )
                    Text(
                        text = "How would you rate your mood?",
                        modifier = modifier
                            .padding(start = 16.dp, top = 16.dp)
                            .fillMaxWidth(),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    MoodRating(
                        selectedRating = selectedRating,
                        onRatingSelected = { selectedRating = it }
                    )
                    TextEntry(
                        text = diaryText,
                        onTextChanged = { diaryText = it }
                    )
                }
            }

            if (showDialog.value) {
                ColorPickerDialog(
                    showDialog = showDialog,
                    diaryViewModel = diaryViewModel,
                    previousColor = selectedColor
                )
            }
        }
    }
}

@Composable
fun ControlRow(
    selectedColor: Color,
    showDialog: MutableState<Boolean>,
    onSaveClick: () -> Unit,
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
        SaveEntryButton(onClick = onSaveClick)
    }
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
    )
}

@Composable
fun SaveEntryButton(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = "Save",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun TextEntry(
    text: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { onTextChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp), // Set a fixed height for the TextField
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

@Composable
fun MoodRating(
    selectedRating: Int?,
    onRatingSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (rating in 1..5) {
            RatingButton(
                rating = rating,
                isSelected = rating == selectedRating,
                onClick = { onRatingSelected(rating) }
            )
        }
    }
}

@Composable
fun RatingButton(
    rating: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(
            text = rating.toString(),
            style = MaterialTheme.typography.titleMedium.copy(
                textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None
            ),
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateAndTime(): Pair<String, String> {
    val current = LocalDateTime.now()
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return Pair(current.format(dateFormatter), current.format(timeFormatter))
}

@Composable
fun DateTimeDisplay(
    date: String,
    time: String,
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
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Date: $date",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Time: $time",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

// Color Picker Functions
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
fun ColorPickerDialog(
    showDialog: MutableState<Boolean>,
    diaryViewModel: DiaryViewModel,
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
                        diaryViewModel.updateColor(colorEnvelope.color)
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

