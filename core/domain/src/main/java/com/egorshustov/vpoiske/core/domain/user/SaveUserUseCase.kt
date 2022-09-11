package com.egorshustov.vpoiske.core.domain.user

import com.egorshustov.vpoiske.core.common.base.UseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.repository.UsersRepository
import com.egorshustov.vpoiske.core.model.data.User
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