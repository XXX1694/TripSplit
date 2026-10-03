package com.abzal.tripsplit.features.participants.presentation.components

import com.abzal.tripsplit.core.designsystem.components.Tone
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus

fun InvitationStatus.label(): String = when (this) {
    InvitationStatus.ACCEPTED -> "Accepted"
    InvitationStatus.PENDING -> "Pending"
    InvitationStatus.REVOKED -> "Revoked"
}

fun InvitationStatus.tone(): Tone = when (this) {
    InvitationStatus.ACCEPTED -> Tone.Primary
    InvitationStatus.PENDING -> Tone.Warning
    InvitationStatus.REVOKED -> Tone.Negative
}
