package com.egorshustov.vpoiske.core.domain.user

import com.egorshustov.vpoiske.core.common.base.FlowUseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.common.utils.asResult
import com.egorshustov.vpoiske.core.data.repository.UsersRepository
import com.egorshustov.vpoiske.core.model.data.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetSearchUsersUseCaseParams(val searchId: Long)

class GetSearchUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<GetSearchUsersUseCaseParams, List<User>>(ioDispatcher) {

    override fun execute(parameters: GetSearchUsersUseCaseParams): Flow<Result<List<User>>> =
        usersRepository.getUsersStream(parameters.searchId).asResult()
}