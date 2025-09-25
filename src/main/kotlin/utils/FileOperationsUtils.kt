package utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

/**
 * Function to open dialog to browse files/folders
 */
suspend fun openFileDialog(): String? = withContext(Dispatchers.IO) {
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

/**
 * Contains all the functions to perform file operations
 */
object FileOperationsUtils {

    suspend fun performFileOperation(path: String, operation: String): Int = withContext(Dispatchers.IO) {
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
                        processedCount = processDirectoryRecursively(selectedFile, operationType, operationValue)
                    } else {
                        processedCount = if (processSingleFile(selectedFile, operationType, operationValue)) 1 else 0
                    }
                }

                "addtofilename" -> {
                    if (selectedFile.isDirectory) {
                        processedCount = processDirectoryRecursively(selectedFile, operationType, operationValue)
                    } else {
                        processedCount = if (processSingleFile(selectedFile, operationType, operationValue)) 1 else 0
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

    /**
     * Processes directory recursively
     * @param directory directory selected for the file ops
     * @param operationType type of the operation
     * @param operationValue start input for the operation
     */
    private fun processDirectoryRecursively(directory: File, operationType: String, operationValue: String): Int {
        var processedCount = 0
        try {
            // Get all files and subdirectories
            val allFiles = directory.walkTopDown().toList()

            // Process files first (to avoid directory path issues)
            allFiles.filter { it.isFile }.forEach { file ->
                if (processSingleFile(file, operationType, operationValue)) {
                    processedCount++
                }
            }

            // Process directories in reverse order (deepest first)
            allFiles.filter { it.isDirectory && it != directory }.reversed().forEach { dir ->
                if (processSingleFile(dir, operationType, operationValue)) {
                    processedCount++
                }
            }

            println("Processed $processedCount items recursively")
        } catch (e: Exception) {
            println("Error processing directory recursively: ${e.message}")
        }
        return processedCount
    }

    /**
     * Process any file for the defined operation
     * @param file input file
     * @param operationType type of the operation
     * @param operationValue start input for the operation
     */
    private fun processSingleFile(file: File, operationType: String, operationValue: String): Boolean {
        return try {
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
                    true
                } else {
                    println("Failed to rename: $currentName")
                    false
                }
            } else {
                false
            }
        } catch (e: Exception) {
            println("Error processing file ${file.name}: ${e.message}")
            false
        }
    }
}