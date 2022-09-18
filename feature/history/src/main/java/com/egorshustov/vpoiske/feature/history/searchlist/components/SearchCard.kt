package com.egorshustov.vpoiske.feature.history.searchlist.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.core.common.utils.UrlString
import com.egorshustov.vpoiske.core.common.utils.getFormattedDateTimeText
import com.egorshustov.vpoiske.core.model.data.Search
import com.egorshustov.vpoiske.core.model.data.mockSearch
import com.egorshustov.vpoiske.core.ui.R

@Composable
internal fun SearchCard(
    search: Search,
    photos: List<UrlString>,
    onSearchCardClick: (searchId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(width = Dp.Hairline, color = MaterialTheme.colorScheme.outline),
        modifier = modifier
            .fillMaxWidth()
            .clickable { search.id?.let { onSearchCardClick(it) } }
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            UsersPhotosGrid(photos = photos)
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = search.city.title,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = search.startTime.getFormattedDateTimeText())
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = getAgeFromToText(
                            ageFrom = search.ageFrom,
                            ageTo = search.ageTo
                        )
                    )
                    Icon(
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .size(18.dp),
                        painter = painterResource(R.drawable.ic_baseline_people_24),
                        contentDescription = stringResource(R.string.history_search_users_count)
                    )
                    Text(text = search.foundUsersCount.toString())
                }
                Text(text = getWithPhoneOnlyText(withPhoneOnly = search.withPhoneOnly))
            }
        }
    }
}

@Composable
private fun getAgeFromToText(ageFrom: Int, ageTo: Int?): String = if (ageTo == null) {
    val anyText = stringResource(R.string.history_search_any)
    stringResource(R.string.history_search_age_from_to, ageFrom, anyText)
} else {
    stringResource(R.string.history_search_age_from_to, ageFrom, ageTo)
}

@Composable
private fun getWithPhoneOnlyText(withPhoneOnly: Boolean): String = if (withPhoneOnly) {
    stringResource(R.string.history_search_with_phone_yes)
} else {
    stringResource(R.string.history_search_with_phone_no)
}

internal const val photo50UrlExample =
    "https://sun1-56.userapi.com/s/v1/if1/gOiwNfNenacUXeCTf9qiavok8vKfBGXWtOGZt9IiB2DMD4RyQkRNMkN2t2E0v_evPmReUICN.jpg?size=50x50&quality=96&crop=0,0,1437,1437&ava=1"

@Preview
@Composable
internal fun SearchCardPreview() {
    SearchCard(
        search = mockSearch,
        photos = List(9) { photo50UrlExample },
        onSearchCardClick = {},
    )
}