package com.fruitPunchSamurai.firechat.viewModels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.ext.MyAndroidViewModel.getExternalFilesDir
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

    fun saveImageToStorage(bitmap: Bitmap) {
        state.postValue(MyState.Loading())
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
            state.postValue(MyState.Finished(getString(R.string.downloadCompleted)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateFilename() =
        LocalDateTime().toString(DateTimeFormat.forPattern("ddMMyyHHmmss"))


}