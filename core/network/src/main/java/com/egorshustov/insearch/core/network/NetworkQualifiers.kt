package com.egorshustov.insearch.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class AppBaseUrl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class AppReserveUrl