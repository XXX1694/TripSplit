package com.abzal.tripsplit.features.participants.data.repository

import com.abzal.tripsplit.features.participants.domain.model.Invitation
import com.abzal.tripsplit.features.participants.domain.model.InvitationStatus
import com.abzal.tripsplit.features.participants.domain.model.Participant
import com.abzal.tripsplit.features.participants.domain.repository.ParticipantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.UUID

class ParticipantRepositoryImpl : ParticipantRepository {
    private val participants = MutableStateFlow<List<Participant>>(emptyList())
    private val invitations = MutableStateFlow<List<Invitation>>(emptyList())

    override fun observeParticipants(tripId: String): Flow<List<Participant>> =
        participants.map { list -> list.filter { it.tripId == tripId } }

    override suspend fun addParticipant(tripId: String, name: String, email: String?): Participant {
        val participant = Participant(UUID.randomUUID().toString(), tripId, name, email)
        participants.update { it + participant }
        return participant
    }

    override suspend fun removeParticipant(participantId: String) {
        participants.update { list -> list.filterNot { it.id == participantId } }
    }

    override fun observeInvitations(tripId: String): Flow<List<Invitation>> =
        invitations.map { list -> list.filter { it.tripId == tripId } }

    override suspend fun createInvitation(tripId: String): Invitation {
        val invitation = Invitation(
            id = UUID.randomUUID().toString(),
            tripId = tripId,
            code = UUID.randomUUID().toString().take(8).uppercase(),
        )
        invitations.update { it + invitation }
        return invitation
    }

    override suspend fun revokeInvitation(invitationId: String) {
        invitations.update { list ->
            list.map { if (it.id == invitationId) it.copy(status = InvitationStatus.REVOKED) else it }
        }
    }

    override suspend fun joinByCode(code: String): Result<String> {
        val invitation = invitations.value.firstOrNull {
            it.code.equals(code.trim(), ignoreCase = true) && it.status == InvitationStatus.PENDING
        } ?: return Result.failure(IllegalArgumentException("Invalid invitation code"))
        invitations.update { list ->
            list.map { if (it.id == invitation.id) it.copy(status = InvitationStatus.ACCEPTED) else it }
        }
        return Result.success(invitation.tripId)
    }
}
