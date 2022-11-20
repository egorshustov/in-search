package com.egorshustov.insearch.core.data.mappers

import androidx.paging.*
import com.egorshustov.insearch.core.model.data.requestsparams.PagingConfigParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * This function transforms data from PagingSource to PagingData.
 * Unfortunately, there is no way to perform data transformation for PagingSource class directly
 * (for example, for mapping data layer models to domain layer models). So this is the only
 * workaround I found till Google will release the issue they created:
 * [Investigate adding PagingSource.map](https://issuetracker.google.com/issues/206697857)
 *
 * @param params - parameters for [PagingConfig] class of [Pager]
 * @param transform - data transformation function
 */
internal inline fun <
        reified SourceKeyType : Any,
        reified SourceValueType : Any,
        reified ReturnValueType : Any>
        PagingSource<SourceKeyType, SourceValueType>.asPagingDataStream(
    params: PagingConfigParams,
    crossinline transform: (SourceValueType) -> ReturnValueType
): Flow<PagingData<ReturnValueType>> = Pager(
    config = PagingConfig(
        pageSize = params.pageSize,
        enablePlaceholders = params.enablePlaceholders,
        maxSize = params.maxSize
    )
) {
    this
}.flow.map { pagingData ->
    pagingData.map { sourceValue: SourceValueType ->
        transform(sourceValue)
    }
}