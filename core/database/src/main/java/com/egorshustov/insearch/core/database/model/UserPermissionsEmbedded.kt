package com.egorshustov.insearch.core.database.model

import androidx.room.ColumnInfo
import com.egorshustov.insearch.core.model.data.UserPermissions

data class UserPermissionsEmbedded(

    @ColumnInfo(name = "is_closed")
    val isClosed: Boolean?,

    @ColumnInfo(name = "can_access_closed")
    val canAccessClosed: Boolean?,

    @ColumnInfo(name = "can_write_private_message")
    val canWritePrivateMessage: Boolean?,

    @ColumnInfo(name = "can_send_friend_request")
    val canSendFriendRequest: Boolean?
)

internal fun UserPermissionsEmbedded.asExternalModel() = UserPermissions(
    isClosed = isClosed,
    canAccessClosed = canAccessClosed,
    canWritePrivateMessage = canWritePrivateMessage,
    canSendFriendRequest = canSendFriendRequest
)
