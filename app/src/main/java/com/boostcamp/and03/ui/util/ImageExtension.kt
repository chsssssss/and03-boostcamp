package com.boostcamp.and03.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore.Images
import java.io.ByteArrayOutputStream

fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
    return Uri.parse(path)
}