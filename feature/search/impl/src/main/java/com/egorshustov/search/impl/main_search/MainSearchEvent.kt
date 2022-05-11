package com.egorshustov.search.impl.main_search

internal sealed interface MainSearchEvent {

    object OnAuthRequested : MainSearchEvent
}