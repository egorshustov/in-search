package com.egorshustov.vpoiske.core.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T: Any> AppDropdownMenu(
    items: List<T>,
    onItemClick: (item: T) -> Unit,
    itemText: @Composable (item: T) -> Unit,
    selectedItemValue: String
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedItemValue,
            onValueChange = {},
            label = { Text("Label") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
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