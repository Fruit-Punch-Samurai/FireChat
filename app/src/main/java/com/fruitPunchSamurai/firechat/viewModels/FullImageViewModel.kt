package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.ext.MyAndroidViewModel.getApplicationContext
import com.fruitPunchSamurai.firechat.ext.MyAndroidViewModel.getString
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.repos.MainRepo
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.io.File
import java.io.FileOutputStream


class FullImageViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = MainRepo()
    lateinit var receiverID: String
    lateinit var imageID: String
    var state = MutableLiveData<MyState>(MyState.Idle)

    fun setIdleState() = state.postValue(MyState.Idle)

    suspend fun getImage(ctx: Context): Bitmap? {
        state.postValue(MyState.Loading())
        val bitmap = repo.getImage(ctx, imageID, receiverID)
        state.postValue(MyState.Finished())
        return bitmap
    }

    fun saveImageToStorage(bitmap: Bitmap?) {
        try {
            state.postValue(MyState.Loading())

            if (bitmap == null) {
                state.postValue(MyState.Error(getString(R.string.noImage)))
                return
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) saveImageWithMediaStore(bitmap)
            else saveImageToGalleryDirectory(bitmap)

            state.postValue(MyState.Finished(getString(R.string.downloadCompleted)))
        } catch (e: Exception) {
            e.printStackTrace()
            state.postValue(MyState.Error(e.message))
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageWithMediaStore(bitmap: Bitmap) {
        val resolver = getApplicationContext().contentResolver
        val contentValues = ContentValues()

        contentValues.run {
            put(MediaStore.MediaColumns.DISPLAY_NAME, generateFilename())
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/FireChat")
        }

        val imageUri: Uri? =
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val fos = resolver.openOutputStream(imageUri!!)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos?.close()
    }

    private fun saveImageToGalleryDirectory(bitmap: Bitmap) {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + "/FireChat"
            )
        imagesDir.mkdirs()

        val imageFile = File(imagesDir, generateFilename())

        val fos = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()

        addImageToGallery(imageFile)
    }

    private fun addImageToGallery(imgFile: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        mediaScanIntent.data = Uri.fromFile(imgFile)
        getApplicationContext().sendBroadcast(mediaScanIntent)
    }

    private fun generateFilename() =
        LocalDateTime().toString(DateTimeFormat.forPattern("ddMMyyHHmmss")) + ".jpg"
}