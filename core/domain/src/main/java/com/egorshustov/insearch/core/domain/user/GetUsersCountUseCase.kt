package com.egorshustov.insearch.core.domain.user

import com.egorshustov.insearch.core.common.base.FlowUseCase
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.common.utils.asResult
import com.egorshustov.insearch.core.data.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetUsersCountUseCaseParams(val searchId: Long)

class GetUsersCountUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<GetUsersCountUseCaseParams, Int>(ioDispatcher) {

    override fun execute(parameters: GetUsersCountUseCaseParams): Flow<Result<Int>> =
        usersRepository
            .getUsersCountStream(parameters.searchId)
            .asResult()
}