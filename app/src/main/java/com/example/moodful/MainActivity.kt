package com.example.moodful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.moodful.ui.theme.MoodfulTheme
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrontPage()
        }
    }
}


@Composable
fun FrontPage(modifier: Modifier = Modifier) {
    MoodfulTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        Scaffold { innerPadding ->
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.surface
            ) {
                ConstraintLayout {
                    // TODO: Create refs here
                    val dateRef = createRef()
                    val timeRef = createRef()
                    val floatingButtonRef = createRef()
                    DateToday(
                        modifier = Modifier
                            .constrainAs(dateRef) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(timeRef.top, margin = 8.dp)
                            }
                    )
                    DateAndTimeDisplay(
                        modifier = Modifier
                            .constrainAs(timeRef) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                    HubButton(
                        modifier = Modifier
                            .constrainAs(floatingButtonRef) {
                                bottom.linkTo(parent.bottom, margin = 18.dp)
                                end.linkTo(parent.end, margin = 18.dp)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun DateToday(modifier: Modifier = Modifier) {
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
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            fontSize = 20.sp
        )
    }
}

@Composable
fun DateAndTimeDisplay(modifier: Modifier = Modifier) {
    val calendar = Calendar.getInstance().time
    val hourFormat = SimpleDateFormat("hh", Locale.getDefault()).format(calendar)
    val minuteFormat = SimpleDateFormat("mm", Locale.getDefault()).format(calendar)
    val amPmFormat = SimpleDateFormat("a", Locale.getDefault()).format(calendar)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = modifier
                .size(280.dp) // Ensure it's a square
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.inversePrimary,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp) // Inner padding for the content
            ) {
                Text(
                    text = hourFormat,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
                Text(
                    text = minuteFormat,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
                Text(
                    text = amPmFormat,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}


@Composable
fun HubButton(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

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
                visible = expanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ExtendedFloatingActionButton(
                        modifier = modifier.shadow(8.dp, RoundedCornerShape(16.dp)),
                        text = { Text("Create new entry") },
                        onClick = { /* Action 1 */ },
                        icon = { Icon(Icons.Filled.Create, "Create") }
                    )
                    ExtendedFloatingActionButton(
                        modifier = modifier.shadow(8.dp, RoundedCornerShape(16.dp)),
                        text = { Text("Open diary") },
                        onClick = { /* Action 2 */ },
                        icon = { Icon(Icons.Filled.Menu, "Menu") }
                    )
                }
            }

            LargeFloatingActionButton(
                onClick = { expanded = !expanded },
                shape = CircleShape,
                contentColor = MaterialTheme.colorScheme.surfaceContainerLow,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.Close else Icons.Filled.FavoriteBorder,
                    contentDescription = "More actions"
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HubButtonPreview() {
    MoodfulTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        HubButton()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppPreview() {
    FrontPage()
}