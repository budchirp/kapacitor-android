package com.cankolay.kapacitor.android.domain.usecase.user.profile

import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.user.profile.GetProfileResponse
import com.cankolay.kapacitor.android.data.remote.service.user.profile.ProfileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProfileUseCase
@Inject
constructor(
    private val profileService: ProfileService
) {
    suspend operator fun invoke(): ApiResult<GetProfileResponse> {
        return try {
            val result = withContext(context = Dispatchers.IO) {
                profileService.get()
            }

            result
        } catch (_: Exception) {
            ApiResult.Fatal(Throwable())
        }
    }
}
