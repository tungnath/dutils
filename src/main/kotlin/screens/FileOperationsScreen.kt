package screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import utils.FileOperationsUtils
import utils.openFileDialog

@Composable
fun FileOperationsScreen() {

    val scaffoldState = rememberScaffoldState()
    var filePath by remember { mutableStateOf("") }
    var editFieldText by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "File Operations Tool",
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }, backgroundColor = MaterialTheme.colors.primary
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main content card
            Card(
                modifier = Modifier.fillMaxWidth(fraction = 0.8f).wrapContentHeight().padding(8.dp),
                elevation = 16.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Card title
                    Text(
                        text = "File Name Ops",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    // Browse button
                    Button(
                        modifier = Modifier.fillMaxWidth(fraction = 0.8f), onClick = {
                            scope.launch(Dispatchers.IO) {
                                val selectedPath = openFileDialog()
                                withContext(Dispatchers.Main) {
                                    selectedPath?.let { filePath = it }
                                }
                            }
                        }) {
                        Text("Browse Files/Folders")
                    }

                    // Path label
                    Text(
                        text = "Path: $filePath",
                        style = MaterialTheme.typography.body1,
                        color = Color.DarkGray,
                        modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(8.dp),
                        textAlign = TextAlign.Start
                    )

                    // Editable field for Operation details
                    OutlinedTextField(
                        value = editFieldText,
                        onValueChange = { editFieldText = it },
                        label = { Text("Enter operation (e.g., removefromfilename:<word>)") },
                        placeholder = { Text("addtofilename:<suffix>") },
                        modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(8.dp),
                        enabled = !isProcessing
                    )

                    // Apply
                    Button(
                        modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(8.dp), onClick = {
                            if (filePath.isNotEmpty() && editFieldText.isNotEmpty()) {
                                scope.launch {
                                    isProcessing = true
                                    try {
                                        val result = FileOperationsUtils.performFileOperation(filePath, editFieldText)

                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Operation completed successfully! Processed $result files.",
                                            duration = SnackbarDuration.Short
                                        )
                                    } catch (e: Exception) {
                                        println("Error performing operation: ${e.message}")

                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Error: ${e.message}", duration = SnackbarDuration.Long
                                        )
                                    } finally {
                                        isProcessing = false
                                    }
                                }
                            }
                        }, enabled = !isProcessing && filePath.isNotEmpty() && editFieldText.isNotEmpty()
                    ) {
                        if (isProcessing) {
                            Text("Processing...")
                        } else {
                            Text("APPLY")
                        }
                    }
                }
            }
        }
    }
}