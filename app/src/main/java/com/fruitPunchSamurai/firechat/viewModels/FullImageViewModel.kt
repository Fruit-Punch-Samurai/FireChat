package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.ext.MyAndroidViewModel.getExternalFilesDir
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.repos.MainRepo
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.io.File
import java.io.FileOutputStream


class FullImageViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = MainRepo()
    var state = MutableLiveData<MyState>(MyState.Idle)

    fun setIdleState() = state.postValue(MyState.Idle)

    suspend fun getImageURI(imageID: String, receiverID: String): Uri {
        state.postValue(MyState.Loading())
        val uri = repo.getImageURI(imageID, receiverID)
        state.postValue(MyState.Finished())
        return uri
    }

    fun saveImageToStorage(bitmap: Bitmap) {
        val file = getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: return
        saveImageFile(bitmap, file)
    }

    private fun saveImageFile(res: Bitmap, dir: File) {
        try {
            val imageFileName = "${generateFilename()}.jpeg"
            val imageFile = File(dir, imageFileName)

            val outputStream = FileOutputStream(imageFile)
            res.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateFilename() =
        LocalDateTime().toString(DateTimeFormat.forPattern("ddMMyyHHmmss"))


}