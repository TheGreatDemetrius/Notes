package ru.simple.note.components.presentation.list

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.simple.note.R
import ru.simple.note.components.domain.util.Dimensions.MEDIUM
import ru.simple.note.components.presentation.list.components.NoteItem
import ru.simple.note.components.presentation.list.components.OrderSection

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val message = stringResource(id = R.string.note_deleted)
    val actionLabel = stringResource(id = R.string.undo)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MEDIUM)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.h4)
                IconButton(onClick = {
                    viewModel.onEvent(NotesEvent.ToggleOrderSection)
                }) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription = stringResource(id = R.string.sort))
                }
            }
            AnimatedVisibility(
                visible = state.isOderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MEDIUM),
                    orderProperties = state.orderProperties,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(MEDIUM))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteItem(note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            },
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))

                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = message,
                                    actionLabel = actionLabel
                                )
                                if (result == SnackbarResult.ActionPerformed)
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(MEDIUM))
                }
            }
        }
    }
}

