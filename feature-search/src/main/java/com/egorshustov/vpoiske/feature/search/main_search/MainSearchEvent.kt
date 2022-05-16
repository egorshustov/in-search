package com.egorshustov.vpoiske.feature.search.main_search

internal sealed interface MainSearchEvent {

    object OnAuthRequested : MainSearchEvent
}