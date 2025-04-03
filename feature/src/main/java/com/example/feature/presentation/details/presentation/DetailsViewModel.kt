package com.example.feature.presentation.details.presentation

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.core.utils.Details
import com.example.feature.presentation.details.domain.usecase.GetPhotoDetailsUseCase
import com.example.feature.presentation.details.domain.usecase.UpdateFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPhotoDetailsUseCase: GetPhotoDetailsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val details: Details = savedStateHandle.toRoute()

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        getPhotoById()
    }

    private fun getPhotoById(){
        viewModelScope.launch {
            getPhotoDetailsUseCase(details.id)
                .onSuccess { photo ->
                    _uiState.value = DetailsUiState.Success(photo)
                }
                .onFailure { exception ->
                    _uiState.value = DetailsUiState.Error(
                        exception.message ?: "Unknown error"
                    )
                }
        }
    }

    fun updateFavoriteStatus(id: Long, isFavorite: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            updateFavoriteStatusUseCase(id = id, isFavorite = !isFavorite)
            getPhotoById()
        }
    }

    fun downloadImage(context: Context, imageUrl: String, fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val contentResolver = context.contentResolver

            val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val imageDetails = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/PexelsApp")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }

            val imageUri = contentResolver.insert(imageCollection, imageDetails)

            imageUri?.let { uri ->
                try {

                    val inputStream = URL(imageUrl).openStream()
                    val outputStream = contentResolver.openOutputStream(uri)

                    inputStream.use { input ->
                        outputStream?.use { output ->
                            input.copyTo(output)
                        }
                    }

                    imageDetails.clear()
                    imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
                    contentResolver.update(uri, imageDetails, null, null)

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(context, "Изображение сохранено", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}