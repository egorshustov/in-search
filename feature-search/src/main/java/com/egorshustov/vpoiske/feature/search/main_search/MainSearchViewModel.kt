package com.egorshustov.vpoiske.feature.search.main_search

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.egorshustov.vpoiske.core.common.model.data
import com.egorshustov.vpoiske.core.common.network.DEFAULT_API_VERSION
import com.egorshustov.vpoiske.core.domain.GetAccessTokenUseCase
import com.egorshustov.vpoiske.core.model.data.SearchUsersRequestParams
import com.egorshustov.vpoiske.core.model.data.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.datasource.UsersNetworkDataSource
import com.egorshustov.vpoiske.feature.search.process_search.ProcessSearchWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MainSearchViewModel @Inject constructor(
    getAccessTokenUseCase: GetAccessTokenUseCase,
    private val usersNetworkDataSource: UsersNetworkDataSource,
    @ApplicationContext appContext: Context
) : ViewModel() {

    private val _state: MutableState<MainSearchState> = mutableStateOf(MainSearchState())
    val state: State<MainSearchState> = _state

    init {
        getAccessTokenUseCase(Unit).onEach {
            val accessToken = it.data
            _state.value = state.value.copy(isAuthRequired = accessToken.isNullOrBlank())

            searchUsers(accessToken)
        }.launchIn(viewModelScope)

        enqueueWorkRequest(appContext)
    }

    private fun enqueueWorkRequest(appContext: Context) {
        val request = OneTimeWorkRequestBuilder<ProcessSearchWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        WorkManager.getInstance(appContext).enqueue(request)
    }

    private fun searchUsers(accessToken: String?) {
        if (accessToken.isNullOrBlank()) return
        usersNetworkDataSource.searchUsers(
            SearchUsersRequestParams(
                countryId = 1,
                cityId = 2,
                ageFrom = 18,
                ageTo = 30,
                birthDay = 17,
                birthMonth = 3,
                fields = "last_seen,contacts,followers_count,photo_id,sex,bdate,city,country,home_town,photo_50,photo_max,photo_max_orig,contacts,relation,can_write_private_message,can_send_friend_request,can_write_private_message",
                homeTown = null,
                relation = 1,
                sex = 1,
                hasPhoto = 1,
                count = 1000,
                sortType = 1
            ),
            VkCommonRequestParams(
                accessToken = accessToken.orEmpty(),
                apiVersion = DEFAULT_API_VERSION
            )
        ).onEach {
            val res = it
            Timber.d(res.toString())
        }.launchIn(viewModelScope)
    }

    fun onTriggerEvent(event: MainSearchEvent) {
        when (event) {
            MainSearchEvent.OnAuthRequested -> onAuthRequested()
        }
    }

    private fun onAuthRequested() {
        _state.value = state.value.copy(isAuthRequired = false)
    }
}