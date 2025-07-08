package com.cankolay.kapacitor.android.data.remote.service

import com.cankolay.kapacitor.android.data.remote.client.HttpRoutes
import com.cankolay.kapacitor.android.data.remote.client.KtorClient
import com.cankolay.kapacitor.android.data.remote.model.ApiResult
import com.cankolay.kapacitor.android.data.remote.model.response.notes.GetAllNotesResponse
import com.cankolay.kapacitor.android.data.remote.model.response.notes.GetNoteResponse
import com.cankolay.kapacitor.android.data.remote.util.FetchUtil
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NotesService
@Inject
constructor(
    private val ktorClient: KtorClient,
    private val fetchUtil: FetchUtil,
) {
    private var client: HttpClient

    init {
        runBlocking {
            client = ktorClient.getClient()
        }
    }

    suspend fun getAll(
    ): ApiResult<GetAllNotesResponse> = fetchUtil.safeFetch {
        client
            .get {
                url {
                    path(HttpRoutes.GET_NOTES)
                }
            }
    }

    suspend fun get(
        id: String
    ): ApiResult<GetNoteResponse> = fetchUtil.safeFetch {
        client.get {
            url {
                path(HttpRoutes.GET_NOTES + id)
            }
        }
    }
}