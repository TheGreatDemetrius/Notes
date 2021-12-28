package ru.simple.note.components.presentation.item

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.simple.note.components.domain.model.Note
import ru.simple.note.components.domain.util.Dimensions.MEDIUM
import ru.simple.note.components.domain.util.Dimensions.SMALL
import ru.simple.note.components.presentation.item.components.TransparentHint

@Composable
fun ItemScreen(
    color: Int,
    navController: NavController,
    viewModel: ItemViewModel = hiltViewModel()
) {
    val titleState = viewModel.title.value
    val descriptionState = viewModel.description.value

    val scaffoldState = rememberScaffoldState()

    val backgroundAnimation = remember {
        Animatable(
            Color(if (color != -1) color else viewModel.color.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {//выполняется один раз
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ItemViewModel.UiEvent.ShowSnackBar ->
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                is ItemViewModel.UiEvent.SaveNote -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(ItemEvent.SaveNote) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundAnimation.value)
                .padding(MEDIUM)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SMALL),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.colors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)//TODO в измерения
                            .shadow(MEDIUM, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color =
                                if (viewModel.color.value == colorInt) Color.Black
                                else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    backgroundAnimation.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(ItemEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(MEDIUM))
            TransparentHint(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(ItemEvent.EnteredTitle(it))
                },
                onFocusChange = { viewModel.onEvent(ItemEvent.ChangeTitleFocus(it)) },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(MEDIUM))
            TransparentHint(
                text = descriptionState.text,
                hint = descriptionState.hint,
                onValueChange = {
                    viewModel.onEvent(ItemEvent.EnteredDescription(it))
                },
                onFocusChange = { viewModel.onEvent(ItemEvent.ChangeDescriptionFocus(it)) },
                isHintVisible = descriptionState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}