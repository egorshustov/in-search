package com.egorshustov.vpoiske.feature.params.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egorshustov.vpoiske.core.model.data.Relation
import com.egorshustov.vpoiske.core.ui.component.AppDropdownMenu
import com.egorshustov.vpoiske.feature.params.R
import com.egorshustov.vpoiske.feature.params.RelationState

@Composable
internal fun RelationLayout(
    relationState: RelationState,
    onRelationItemClick: (relation: Relation) -> Unit
) {
    Column {
        Text(text = stringResource(R.string.search_params_relation))
        Spacer(modifier = Modifier.height(4.dp))
        AppDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            items = Relation.values().toList(),
            onItemClick = { onRelationItemClick(it) },
            itemText = { item -> Text(item.getDescription(LocalContext.current)) },
            selectedItemValue = relationState.selectedRelation.getDescription(LocalContext.current)
        )
    }
}

@Preview
@Composable
internal fun RelationLayoutPreview() {
    RelationLayout(
        relationState = RelationState(),
        onRelationItemClick = {}
    )
}