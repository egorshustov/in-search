package com.egorshustov.insearch.core.domain.user

import com.egorshustov.insearch.core.common.base.UseCase
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.data.repository.UsersRepository
import com.egorshustov.insearch.core.model.data.User
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class SaveUserUseCaseParams(val user: User)

class SaveUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : UseCase<SaveUserUseCaseParams, Result<Long>>(ioDispatcher) {

    override suspend fun execute(parameters: SaveUserUseCaseParams): Result<Long> =
        usersRepository.saveUser(parameters.user)
}