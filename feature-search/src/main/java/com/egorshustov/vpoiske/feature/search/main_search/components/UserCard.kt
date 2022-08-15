package com.egorshustov.vpoiske.feature.search.main_search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.egorshustov.vpoiske.core.common.R
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.model.data.mockUser

@Composable
internal fun UserCard(
    user: User,
    onUserCardClick: (userId: Long) -> Unit
) {
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onUserCardClick(user.id) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.photosInfo.photoMaxOrig)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(
                R.string.search_main_user_description,
                user.firstName + " " + user.lastName
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RectangleShape)
                .fillMaxSize()
        )
    }
}

@Preview
@Composable
internal fun UserCardPreview() {
    UserCard(
        user = mockUser,
        onUserCardClick = {}
    )
}