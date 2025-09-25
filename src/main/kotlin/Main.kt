import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

@Composable
@Preview
fun App() {
    // State variables
    var scaffoldState = rememberScaffoldState()
    var filePath by remember { mutableStateOf("") }
    var editFieldText by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    MaterialTheme {

        // wrap with scaffold to allow snackbar host and may be topbar
        Scaffold(
            scaffoldState = scaffoldState,

            topBar = {
                TopAppBar(
                    title = {
                        // Topbar Title
                        Text(
                            text = "Desktop Utils",
                            style = MaterialTheme.typography.h4,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
//                                .fillMaxHeight(fraction = 0.2f)
                                .padding(8.dp).wrapContentHeight(Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )


                    })
            }, bottomBar = {
                BottomAppBar() {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        IconButton(onClick = { println("Hello BottomBar") }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                        }
                    }
                    Spacer(Modifier.weight(1f, true))
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Info, contentDescription = "Localized description")
                    }
                }
            }


        ) { paddingValues ->

            println("paddingValues = $paddingValues")

            Column(
                modifier = Modifier.fillMaxSize().background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Main content area
                Column(
                    modifier = Modifier
//                    .fillMaxSize(fraction = 0.8f)
                        .fillMaxWidth().wrapContentHeight().padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Card with maincontent
                    Card(
                        modifier = Modifier.fillMaxWidth(fraction = 0.7f)
//                        .fillMaxHeight(fraction = 0.7f)
                            .wrapContentHeight().padding(8.dp), elevation = 16.dp, shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
//                                .fillMaxSize()
                                .fillMaxWidth().wrapContentHeight().padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Card title
                            Text(
                                text = "File Operations Tool",
                                style = MaterialTheme.typography.h5,
                                color = Color.Black,
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
                                Text("Browse")
                            }

                            // Path label
                            Text(
                                text = "Path: $filePath",
                                style = MaterialTheme.typography.body1,
                                color = Color.DarkGray,
                                modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(8.dp),
                                textAlign = TextAlign.Start
                            )

                            // Editable text field
                            OutlinedTextField(
                                value = editFieldText,
                                onValueChange = { editFieldText = it },
                                label = { Text("Enter op (e.g., removefromfilename:test)") },
                                modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(8.dp),
                                enabled = !isProcessing
                            )

                            // TAG IT button
                            Button(
                                modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(8.dp), onClick = {
                                    if (filePath.isNotEmpty() && editFieldText.isNotEmpty()) {

                                        scope.launch {
                                            isProcessing = true
                                            try {
                                                val result: Int = performFileOperation(filePath, editFieldText)

                                                // Show a success message
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "Op completed successfully! Processed $result files.",
                                                    duration = SnackbarDuration.Short
                                                )


                                            } catch (e: Exception) {
                                                println("Error performing operation: ${e.message}")

                                                // Show an error message
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
                                    Text("TAG IT")
                                }
                            }
                        }
                    }
                }
            }


        }

    }
}

private suspend fun openFileDialog(): String? = withContext(Dispatchers.IO) {
    try {
        val dialog = FileDialog(null as Frame?, "Select File or Directory", FileDialog.LOAD)
        dialog.isVisible = true
        val file = dialog.file
        val directory = dialog.directory

        if (file != null && directory != null) {
            "$directory$file"
        } else null
    } catch (e: Exception) {
        println("Error opening file dialog: ${e.message}")
        null
    }
}

private suspend fun performFileOperation(path: String, operation: String): Int = withContext(Dispatchers.IO) {
    var processedCount = 0

    try {
        val parts = operation.split(":")
        if (parts.size != 2) {
            println("Invalid operation format. Use 'operation:value'")
            return@withContext processedCount
        }

        val operationType = parts[0].trim()
        val operationValue = parts[1].trim()

        val selectedFile = File(path)

        when (operationType.lowercase()) {
            "removefromfilename" -> {
                if (selectedFile.isDirectory) {
                    // Process all files in directory recursively
                    processDirectoryRecursively(selectedFile, operationType, operationValue)
                    processedCount++
                } else {
                    // Process single file
                    processSingleFile(selectedFile, operationType, operationValue)
                    processedCount++
                }
            }

            "addtofilename" -> {
                if (selectedFile.isDirectory) {
                    processDirectoryRecursively(selectedFile, operationType, operationValue)
                    processedCount++
                } else {
                    processSingleFile(selectedFile, operationType, operationValue)
                    processedCount++
                }
            }

            else -> {
                println("Unknown operation: $operationType")
            }
        }
    } catch (e: Exception) {
        println("Error in file operation: ${e.message}")
    }

    return@withContext processedCount
}

// Process directory recursively
private fun processDirectoryRecursively(directory: File, operationType: String, operationValue: String) {
    try {
        // Get all files and subdirectories
        val allFiles = directory.walkTopDown().toList()

        // Process files first (to avoid directory path issues)
        allFiles.filter { it.isFile }.forEach { file ->
            processSingleFile(file, operationType, operationValue)
        }

        // Process directories in reverse order (deepest first)
        allFiles.filter { it.isDirectory && it != directory }.reversed().forEach { dir ->
            processSingleFile(dir, operationType, operationValue)
        }

        println("Processed ${allFiles.size} items recursively")
    } catch (e: Exception) {
        println("Error processing directory recursively: ${e.message}")
    }
}


private fun processSingleFile(file: File, operationType: String, operationValue: String) {
    try {
        val currentName = file.name
        val newName = when (operationType.lowercase()) {
            "removefromfilename" -> currentName.replace(operationValue, "")
            "addtofilename" -> {
                val extension = file.extension
                val nameWithoutExtension = file.nameWithoutExtension
                if (extension.isNotEmpty()) {
                    "${nameWithoutExtension}_${operationValue}.${extension}"
                } else {
                    "${currentName}_${operationValue}"
                }
            }

            else -> currentName
        }

        if (newName != currentName && newName.isNotEmpty()) {
            val newFile = File(file.parent, newName)
            val success = file.renameTo(newFile)
            if (success) {
                println("Renamed: $currentName -> $newName")
            } else {
                println("Failed to rename: $currentName")
            }
        }
    } catch (e: Exception) {
        println("Error processing file ${file.name}: ${e.message}")
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication, title = "dUTILS - Desktop Utils"
    ) {
        App()
    }
}