package ru.simple.notes.components.presentation.item

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.simple.notes.R
import ru.simple.notes.components.domain.model.Note
import ru.simple.notes.components.presentation.util.Dimensions.MEDIUM
import ru.simple.notes.components.presentation.util.Dimensions.SMALL
import ru.simple.notes.components.presentation.item.components.TextFieldHint
import ru.simple.notes.components.presentation.util.Dimensions.CIRCLE_BUTTON_BORDER
import ru.simple.notes.components.presentation.util.Dimensions.CIRCLE_BUTTON_SIZE

@Composable
fun ItemScreen(
    navController: NavController,
    viewModel: ItemViewModel = hiltViewModel()
) {
    val titleState = viewModel.title.value
    val descriptionState = viewModel.description.value
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
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
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(id = R.string.save),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(viewModel.color.value))
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
                            .size(CIRCLE_BUTTON_SIZE)
                            .shadow(MEDIUM, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = CIRCLE_BUTTON_BORDER,
                                color =
                                if (viewModel.color.value == colorInt) Color.Black
                                else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { viewModel.onEvent(ItemEvent.ChangeColor(colorInt)) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(MEDIUM))
            TextFieldHint(
                modifier = Modifier.fillMaxWidth(),
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    if (!it.contains("\n"))
                        viewModel.onEvent(ItemEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ItemEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(MEDIUM))
            TextFieldHint(
                modifier = Modifier.fillMaxSize(),
                text = descriptionState.text,
                hint = descriptionState.hint,
                onValueChange = {
                    viewModel.onEvent(ItemEvent.EnteredDescription(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ItemEvent.ChangeDescriptionFocus(it))
                },
                isHintVisible = descriptionState.isHintVisible,
                textStyle = TextStyle(fontSize = 20.sp)
            )
        }
    }
}