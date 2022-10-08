package com.example.zlatko.beaches.ui.details.viewmodel

import androidx.lifecycle.*
import com.example.zlatko.beaches.data.BathingAreas
import com.example.zlatko.beaches.domain.FavoritesUseCase
import com.example.zlatko.beaches.domain.GetBathingAreaUseCase
import com.example.zlatko.beaches.ui.details.viewmodel.DetailsViewModel.ViewState.Content
import com.example.zlatko.beaches.ui.navigation.BATHING_AREA_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBathingAreaUseCase: GetBathingAreaUseCase,
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private val bathingAreaId: String = checkNotNull(savedStateHandle[BATHING_AREA_ID])

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class Content(val bathingArea: BathingAreas) : ViewState()
    }

    private val _viewState = MutableLiveData<ViewState>(ViewState.Loading)
    val viewState: LiveData<ViewState> = _viewState

    init {
        viewModelScope.launch {
            val bathingArea = withContext(Dispatchers.IO) {
                getBathingAreaUseCase.getBathingArea(bathingAreaId)
            }
            _viewState.value = if (bathingArea != null) {
                Content(bathingArea)
            } else {
                ViewState.Error
            }
        }
    }

    fun favoriteToggleClicked() {
        viewModelScope.launch {
            val bathingArea = getBathingAreaFromViewState()
            val updatedBathingArea = bathingArea.copy(favorite = !bathingArea.favorite)
            favoritesUseCase.setFavorite(updatedBathingArea.id, updatedBathingArea.favorite)
            _viewState.value = Content(updatedBathingArea)
        }
    }

    fun prepareUrlForNavigating(): String {
        val bathingAreas = getBathingAreaFromViewState()
        val coordinates = "${bathingAreas.latitude},${bathingAreas.longitude}"
        return "geo:$coordinates?q=$coordinates"
    }

    fun prepareUrlForMoreDetails(): String? = getBathingAreaFromViewState().detailsUrl

    private fun getBathingAreaFromViewState() =
        when (val viewState = _viewState.value) {
            is Content -> {
                viewState.bathingArea
            }
            else -> {
                throw RuntimeException("View is in ${_viewState.value} state")
            }
        }

}