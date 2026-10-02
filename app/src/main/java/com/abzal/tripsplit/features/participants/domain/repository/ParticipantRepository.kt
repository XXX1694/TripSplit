package com.abzal.tripsplit.features.participants.domain.repository

import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.model.Participant
import kotlinx.coroutines.flow.Flow

interface ParticipantRepository {
    fun observeParticipants(tripId: String): Flow<List<Participant>>
    suspend fun addParticipant(tripId: String, name: String, email: String?): Participant
    suspend fun renameParticipant(participantId: String, name: String)
    suspend fun removeParticipant(participantId: String)

    fun observeInvitations(tripId: String): Flow<List<Invitation>>
    suspend fun createInvitation(tripId: String): Invitation
    suspend fun revokeInvitation(invitationId: String)

    /** Returns the id of the joined trip. */
    suspend fun joinByCode(code: String): Result<String>
}
