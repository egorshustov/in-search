package com.egorshustov.vpoiske.core.domain.user

import com.egorshustov.vpoiske.core.common.base.FlowUseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.repository.UsersRepository
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.model.data.requestsparams.SearchUsersRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class SearchUsersUseCaseParams(
    val searchUsersParams: SearchUsersRequestParams,
    val commonParams: VkCommonRequestParams
)

class SearchUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<SearchUsersUseCaseParams, List<User>>(ioDispatcher) {

    override fun execute(parameters: SearchUsersUseCaseParams): Flow<Result<List<User>>> =
        usersRepository.searchUsers(parameters.searchUsersParams, parameters.commonParams)
}