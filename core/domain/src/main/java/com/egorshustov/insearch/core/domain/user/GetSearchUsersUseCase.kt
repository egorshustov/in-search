package com.egorshustov.insearch.core.domain.user

import com.egorshustov.insearch.core.common.base.FlowUseCase
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.common.utils.asResult
import com.egorshustov.insearch.core.data.repository.UsersRepository
import com.egorshustov.insearch.core.model.data.User
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