package com.egorshustov.vpoiske.core.model.data

data class UserPermissions(
    val isClosed: Boolean?,
    val canAccessClosed: Boolean?,
    val canWritePrivateMessage: Boolean?,
    val canSendFriendRequest: Boolean?
)
