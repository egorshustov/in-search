package com.egorshustov.insearch.core.domain.user

import com.egorshustov.insearch.core.common.base.FlowUseCase
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.data.repository.UsersRepository
import com.egorshustov.insearch.core.model.data.User
import com.egorshustov.insearch.core.model.data.requestsparams.GetUserRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetUserUseCaseParams(
    val getUserParams: GetUserRequestParams,
    val commonParams: VkCommonRequestParams
)

class GetUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<GetUserUseCaseParams, User>(ioDispatcher) {

    override fun execute(parameters: GetUserUseCaseParams): Flow<Result<User>> =
        usersRepository.getUser(parameters.getUserParams, parameters.commonParams)
}