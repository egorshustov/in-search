package com.egorshustov.vpoiske.core.domain.user

import com.egorshustov.vpoiske.core.common.base.FlowUseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.common.utils.asResult
import com.egorshustov.vpoiske.core.data.repository.SearchesRepository
import com.egorshustov.vpoiske.core.data.repository.UsersRepository
import com.egorshustov.vpoiske.core.model.data.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapMerge
import javax.inject.Inject

@OptIn(FlowPreview::class)
class GetLastSearchUsersUseCase @Inject constructor(
    private val searchesRepository: SearchesRepository,
    private val usersRepository: UsersRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<User>>(ioDispatcher) {

    override fun execute(parameters: Unit): Flow<Result<List<User>>> =
        searchesRepository
            .getLastSearchIdStream()
            .filterNotNull()
            .flatMapMerge { usersRepository.getUsersStream(it) }
            .asResult()
}