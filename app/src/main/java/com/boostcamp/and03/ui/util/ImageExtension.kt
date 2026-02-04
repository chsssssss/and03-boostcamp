package com.boostcamp.and03.ui.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

//fun getImageUri(context: Context, inImage: Bitmap): Uri {
//    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        createTempImageUri(context)
//    }
//    else {
//        saveTheImageLegacyStyle(context, inImage)
//    }
//}

fun createTempImageUri(context: Context): Uri {
    val file = File.createTempFile(
        "temp_${System.currentTimeMillis()}", ".jpg",
        context.cacheDir
    )
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}