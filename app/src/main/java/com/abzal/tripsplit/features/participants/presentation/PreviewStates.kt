package com.abzal.tripsplit.features.participants.presentation

import com.abzal.tripsplit.core.preview.sampleInvitations
import com.abzal.tripsplit.core.preview.sampleParticipants
import com.abzal.tripsplit.core.preview.sampleTrip

val sampleParticipantManagementUiState = ParticipantManagementUiState(
    trip = sampleTrip,
    participants = sampleParticipants,
    editingId = "p2",
    editingName = "Leo Evans",
)

val sampleInvitationManagementUiState = InvitationManagementUiState(trip = sampleTrip, invitations = sampleInvitations)

val sampleAddParticipantUiState = AddParticipantUiState(trip = sampleTrip, name = "Inês Silva", email = "ines.silva@gmail.com")

val sampleInviteParticipantsUiState = InviteParticipantsUiState(trip = sampleTrip, invitation = sampleInvitations.first())

val sampleJoinTripUiState = JoinTripUiState()
