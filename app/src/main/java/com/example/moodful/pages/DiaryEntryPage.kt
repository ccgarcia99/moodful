package com.example.moodful.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.moodful.ColorViewModel
import com.example.moodful.ui.theme.DiaryEntryTopAppBar
import com.example.moodful.ui.theme.MoodfulTheme
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

/*TODO:
    1. Create UI Layout for DiaryEntry
    2. Implement file-handling routines for DiaryEntry
* */



@Composable
fun DiaryEntryPage(
    colorViewModel: ColorViewModel,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }
    val selectedColor by colorViewModel.selectedColor.collectAsState()

    MoodfulTheme {
        Scaffold(
            topBar = { DiaryEntryTopAppBar() }
        ) { innerPadding ->
            Surface(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                color = MaterialTheme.colorScheme.surface
            ) {
                val constraints = ConstraintSet {
                    val prototype = createRefFor("prototype")

                    constrain(prototype) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                }
                ConstraintLayout(
                    constraintSet = constraints,
                    modifier = modifier.fillMaxSize()
                ) {
                    ColorPickerRow(
                        selectedColor = selectedColor,
                        showDialog = showDialog,
                        modifier = modifier.layoutId("prototype")
                    )

                    if (showDialog.value) {
                        ColorPickerDialog(
                            showDialog = showDialog,
                            colorViewModel = colorViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColorPickerRow(
    selectedColor: Color,
    showDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Box(
      modifier = modifier
          .fillMaxWidth()
          .wrapContentHeight(),
      contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            ColorPickerPrompter(showDialog = showDialog)
            CurrentColorMood(colorWays = selectedColor)
        }
    }
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
            style = MaterialTheme.typography.labelMedium
        )
    }
}


@Composable
fun ColorPickerDialog(
    showDialog: MutableState<Boolean>,
    colorViewModel: ColorViewModel,
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
            Text(text = ".moodful")
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(230.dp),
                    controller = colorController,
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        colorViewModel.updateColor(colorEnvelope.color)
                    }
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
        dismissButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text("Cancel")
            }
        },
        modifier = Modifier.padding(16.dp) // Ensure proper padding around the dialog
    )
}


@Composable
private fun CurrentColorMood(
    modifier: Modifier = Modifier,
    colorWays: Color
){
    Box(
        modifier = modifier
            .width(30.dp)
            .height(30.dp)
    ){
        Surface(
            modifier = modifier
                .clip(CircleShape)
                .fillMaxSize(),
            color = colorWays
        ) {
            // NOP
        }
    }
}
