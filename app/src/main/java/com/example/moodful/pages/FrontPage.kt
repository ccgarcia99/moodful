package com.example.moodful.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import com.example.moodful.controller.ScreenController
import com.example.moodful.ui.theme.MoodfulTheme
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun FrontPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    previewExpanded: Boolean = false
) {
    var expanded by remember { mutableStateOf(previewExpanded) }

    MoodfulTheme {
        Scaffold { innerPadding ->
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.surfaceBright
            ) {
                val constraints = ConstraintSet {
                    val dateLabel = createRefFor("dateLabel")
                    val centerClock = createRefFor("centerClock")
                    val expandableFAB = createRefFor("expandableFAB")

                    createVerticalChain(dateLabel, centerClock, chainStyle = ChainStyle.Packed)

                    constrain(dateLabel) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }

                    constrain(centerClock) {
                        top.linkTo(dateLabel.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }

                    constrain(expandableFAB) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                }

                ConstraintLayout(
                    constraintSet = constraints,
                    modifier = modifier.fillMaxSize()
                ) {
                    DateLabel(modifier = Modifier.layoutId("dateLabel"))
                    CenterClock(modifier = Modifier.layoutId("centerClock"))

                    // Blurred background
                    BlurredBackground(
                        modifier = Modifier,
                        visible = expanded
                    )

                    // Expandable FAB
                    ExpandableFAB(
                        navController = navController,
                        modifier = Modifier.layoutId("expandableFAB"),
                        expandedState = expanded,
                        onExpandedStateChange = { expanded = it }
                    )
                }
            }
        }
    }
}

@Composable
fun DateLabel(modifier: Modifier = Modifier) {
    val calendar = Calendar.getInstance().time
    val dateFormat = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Today is $dateFormat",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Light,
            fontSize = 20.sp
        )
    }
}

@Composable
fun CenterClock(modifier: Modifier = Modifier) {
    var time by remember { mutableStateOf(Calendar.getInstance().time) }

    LaunchedEffect(Unit) {
        while (true) {
            time = Calendar.getInstance().time
            delay(1000)
        }
    }

    val hourFormat = SimpleDateFormat("hh", Locale.getDefault()).format(time)
    val minuteFormat = SimpleDateFormat("mm", Locale.getDefault()).format(time)
    val amPmFormat = SimpleDateFormat("a", Locale.getDefault()).format(time)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = modifier
                .size(280.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.inversePrimary,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = hourFormat,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = minuteFormat,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = amPmFormat,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Light,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun BlurredBackground(
    modifier: Modifier = Modifier,
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                .blur(radius = 16.dp)
        )
    }
}

@Composable
fun ExpandableFAB(
    navController: NavController,
    modifier: Modifier = Modifier,
    expandedState: Boolean,
    onExpandedStateChange: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimatedVisibility(
                visible = expandedState,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ExtendedFloatingActionButton(
                        modifier = modifier.shadow(8.dp, RoundedCornerShape(16.dp)),
                        text = {
                            Text(
                                "Create new entry",
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        // Navigate to Diary Entry
                        onClick = { navController.navigate(ScreenController.DiaryEntry.route) },
                        icon = { Icon(Icons.Filled.Create, "Create") }
                    )
                    ExtendedFloatingActionButton(
                        modifier = modifier.shadow(8.dp, RoundedCornerShape(16.dp)),
                        text = {
                            Text(
                                "Open diary",
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        onClick = { navController.navigate(ScreenController.DiaryView.route) },
                        icon = { Icon(Icons.Filled.Menu, "Menu") }
                    )
                }
            }

            LargeFloatingActionButton(
                onClick = { onExpandedStateChange(!expandedState) },
                shape = CircleShape,
                contentColor = MaterialTheme.colorScheme.surfaceContainerLow,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = if (expandedState) Icons.Filled.Close else Icons.Filled.FavoriteBorder,
                    contentDescription = "More actions"
                )
            }
        }
    }
}
