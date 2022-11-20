package com.egorshustov.insearch.core.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any?> AppDropdownMenu(
    items: List<T>,
    onItemClick: (item: T) -> Unit,
    itemText: @Composable (item: T) -> Unit,
    selectedItemValue: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            enabled = enabled,
            readOnly = true,
            value = selectedItemValue,
            onValueChange = {},
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    enabled = enabled,
                    text = { itemText(item) },
                    onClick = {
                        onItemClick(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
internal fun AppDropdownMenuPreview() {
    AppDropdownMenu(
        enabled = true,
        items = emptyList<Any>(),
        onItemClick = {},
        itemText = { Text("") },
        selectedItemValue = "selected_item"
    )
}