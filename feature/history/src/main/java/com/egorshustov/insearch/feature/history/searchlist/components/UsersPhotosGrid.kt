package com.egorshustov.insearch.feature.history.searchlist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.egorshustov.insearch.core.common.R
import com.egorshustov.insearch.core.common.utils.UrlString

private const val photosCountInGrid = 9
private const val photosInRow = 3

@Composable
internal fun UsersPhotosGrid(
    photos: List<UrlString>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val visiblePhotos = photos.take(photosCountInGrid).toMutableList()
        if (visiblePhotos.size < photosCountInGrid) {
            visiblePhotos.addAll(List(photosCountInGrid - visiblePhotos.size) { "" })
        }
        visiblePhotos.take(photosCountInGrid).windowed(photosInRow, photosInRow)
            .forEach { photosUrls ->
                Row {
                    photosUrls.forEach { photoUrl ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(photoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(R.string.history_search_photo_description),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
    }
}


@Preview
@Composable
internal fun UsersPhotosGridPreview() {
    UsersPhotosGrid(photos = List(9) { photo50UrlExample })
}