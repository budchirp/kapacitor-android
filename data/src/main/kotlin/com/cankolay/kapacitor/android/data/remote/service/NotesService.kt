package com.cankolay.kapacitor.android.data.remote.service

import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.notes.GetAllNotesResponseDto
import com.cankolay.kapacitor.android.data.remote.model.response.notes.GetNoteResponseDto
import com.cankolay.kapacitor.android.data.remote.util.safeFetch
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NotesService
@Inject
constructor(
    private val ktorClient: KtorClient,
) {
    private var client: HttpClient

    init {
        runBlocking {
            client = ktorClient.getClient()
        }
    }

    suspend fun getAll(
    ): ApiResult<GetAllNotesResponseDto> = safeFetch {
        client
            .get {
                url {
                    path(HttpRoutes.GET_NOTES)
                }
            }
    }

    suspend fun get(
        id: String
    ): ApiResult<GetNoteResponseDto> = safeFetch {
        client.get {
            url {
                path(HttpRoutes.GET_NOTES + id)
            }
        }
    }
}