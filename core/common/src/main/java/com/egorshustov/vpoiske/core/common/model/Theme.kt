package com.egorshustov.vpoiske.core.common.model

/**
 * Represents the available UI themes for the application
 */
enum class Theme(val storageKey: String) {
    LIGHT("light"),
    DARK("dark"),
    SYSTEM("system")
}

/**
 * Returns the matching [Theme] for the given [storageKey] value.
 */
fun themeFromStorageKey(storageKey: String): Theme? {
    return Theme.values().firstOrNull { it.storageKey == storageKey }
}
