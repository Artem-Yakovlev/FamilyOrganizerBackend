package com.badger.familyorgbe.utils

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

fun deleteImageForEmail(
    dir: String,
    email: String
) {
    File(dir).list().filter { fileName ->
        fileName.contains(email)
    }.forEach { fileName ->
        File(dir, fileName).delete()
    }
}

fun saveFile(
    uploadDir: String?,
    fileName: String,
    multipartFile: MultipartFile
) {
    val uploadPath = Paths.get(uploadDir)
    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath)
    }
    try {
        multipartFile.inputStream.use { inputStream ->
            val filePath = uploadPath.resolve(fileName)
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
        }
    } catch (ioe: IOException) {
        throw IOException("Could not save image file: $fileName", ioe)
    }
}