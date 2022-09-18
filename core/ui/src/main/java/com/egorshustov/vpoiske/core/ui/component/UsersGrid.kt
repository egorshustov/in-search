package com.egorshustov.vpoiske.core.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.model.data.mockUser

@Composable
fun UsersGrid(
    users: List<User>,
    columnCount: Int,
    onUserCardClick: (userId: Long) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columnCount),
        contentPadding = contentPadding
    ) {
        items(users) { user ->
            UserCard(user = user, onUserCardClick = onUserCardClick)
        }
    }
}

@Preview
@Composable
fun UsersGridPreview() {
    UsersGrid(
        users = List(100) { mockUser },
        columnCount = 3,
        onUserCardClick = {}
    )
}