package com.abzal.tripsplit.core.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.abzal.tripsplit.core.navigation.Routes
import com.abzal.tripsplit.core.navigation.tripId
import com.abzal.tripsplit.features.participants.presentation.AddParticipantRoute
import com.abzal.tripsplit.features.participants.presentation.InvitationManagementRoute
import com.abzal.tripsplit.features.participants.presentation.InviteParticipantsRoute
import com.abzal.tripsplit.features.participants.presentation.ParticipantManagementRoute

/** Participants & invitations */
fun NavGraphBuilder.participantsGraph(navController: NavHostController) {
        composable(Routes.PARTICIPANTS) { entry ->
            val tripId = entry.tripId()
            ParticipantManagementRoute(
                onBackClick = { navController.popBackStack() },
                onAddParticipantClick = { navController.navigate(Routes.addParticipant(tripId)) },
                onInviteClick = { navController.navigate(Routes.inviteParticipants(tripId)) },
                onInvitationsClick = { navController.navigate(Routes.invitations(tripId)) },
            )
        }
        composable(Routes.ADD_PARTICIPANT) {
            AddParticipantRoute(
                onBackClick = { navController.popBackStack() },
                onAdded = { navController.popBackStack() },
            )
        }
        composable(Routes.INVITE_PARTICIPANTS) {
            InviteParticipantsRoute(onBackClick = { navController.popBackStack() })
        }
        composable(Routes.INVITATIONS) { entry ->
            val tripId = entry.tripId()
            InvitationManagementRoute(
                onBackClick = { navController.popBackStack() },
                onInviteClick = { navController.navigate(Routes.inviteParticipants(tripId)) },
            )
        }
}
