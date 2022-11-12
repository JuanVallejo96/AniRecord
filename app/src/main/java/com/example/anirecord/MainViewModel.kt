package com.example.anirecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.type.MediaSeason
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PaginatedPopularList(
    var page: Int = 1,
    val year: Int = 2022,
    val season: MediaSeason = MediaSeason.WINTER,
    val data: SeasonPopularQuery.Data? = null,
)

class MainViewModel : ViewModel() {
    private val _list = MutableStateFlow(PaginatedPopularList())
    val list: StateFlow<PaginatedPopularList> = _list.asStateFlow()
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://graphql.anilist.co/")
        .build()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            apolloClient.query(
                SeasonPopularQuery(
                    page = 1,
                    year = 2022,
                    season = MediaSeason.WINTER
                )
            ).execute().data?.let { data ->
                _list.update { currentState ->
                    currentState.copy(
                        page = currentState.page + 1,
                        data = data,
                    )
                }
            }
        }
    }

}