package com.cankolay.kapacitor.android.viewmodel.user.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.user.User
import com.cankolay.kapacitor.android.domain.usecase.user.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) :
    ViewModel() {
    var isLoading = MutableStateFlow(value = false)
        private set

    var user: MutableStateFlow<User?> = MutableStateFlow(value = null)
        private set

    suspend fun fetch() {
        isLoading.value = true

        val result = getProfileUseCase()
        if (result is ApiResult.Success) {
            user.value = result.response.data
        }

        isLoading.value = false
    }

    init {
        viewModelScope.launch {
            fetch()
        }
    }
}