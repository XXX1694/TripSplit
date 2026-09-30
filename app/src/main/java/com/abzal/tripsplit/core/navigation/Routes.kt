package com.abzal.tripsplit.core.navigation

object Routes {
    const val ARG_TRIP_ID = "tripId"
    const val ARG_EXPENSE_ID = "expenseId"

    /** Key used to return the picked currency code to the previous back stack entry. */
    const val RESULT_CURRENCY = "currency"

    // Authorization & account
    const val SIGN_IN = "sign_in"
    const val SIGN_UP = "sign_up"
    const val PASSWORD_RESET = "password_reset"
    const val PROFILE = "profile"

    // Trips
    const val HOME = "home"
    const val CREATE_TRIP = "trips/create"
    const val JOIN_TRIP = "trips/join"
    const val TRIP_OVERVIEW = "trips/{$ARG_TRIP_ID}"
    const val EDIT_TRIP = "trips/{$ARG_TRIP_ID}/edit"
    const val DELETE_TRIP = "trips/{$ARG_TRIP_ID}/delete"

    // Expenses
    const val EXPENSE_HISTORY = "trips/{$ARG_TRIP_ID}/expenses"
    const val ADD_EXPENSE = "trips/{$ARG_TRIP_ID}/expenses/add"
    const val EXPENSE_DETAIL = "trips/{$ARG_TRIP_ID}/expense/{$ARG_EXPENSE_ID}"
    const val EDIT_EXPENSE = "trips/{$ARG_TRIP_ID}/expense/{$ARG_EXPENSE_ID}/edit"
    const val CURRENCY_PICKER = "currency_picker"

    // Participants
    const val PARTICIPANTS = "trips/{$ARG_TRIP_ID}/participants"
    const val ADD_PARTICIPANT = "trips/{$ARG_TRIP_ID}/participants/add"
    const val INVITE_PARTICIPANTS = "trips/{$ARG_TRIP_ID}/invite"
    const val INVITATIONS = "trips/{$ARG_TRIP_ID}/invitations"

    // Balances
    const val BALANCES = "trips/{$ARG_TRIP_ID}/balances"
    const val OPTIMIZED_SETTLEMENT = "trips/{$ARG_TRIP_ID}/balances/optimized"
    const val RECORD_SETTLEMENT = "trips/{$ARG_TRIP_ID}/balances/record"

    // Insights
    const val INSIGHTS = "trips/{$ARG_TRIP_ID}/insights"

    fun tripOverview(tripId: String) = "trips/$tripId"
    fun editTrip(tripId: String) = "trips/$tripId/edit"
    fun deleteTrip(tripId: String) = "trips/$tripId/delete"
    fun expenseHistory(tripId: String) = "trips/$tripId/expenses"
    fun addExpense(tripId: String) = "trips/$tripId/expenses/add"
    fun expenseDetail(tripId: String, expenseId: String) = "trips/$tripId/expense/$expenseId"
    fun editExpense(tripId: String, expenseId: String) = "trips/$tripId/expense/$expenseId/edit"
    fun participants(tripId: String) = "trips/$tripId/participants"
    fun addParticipant(tripId: String) = "trips/$tripId/participants/add"
    fun inviteParticipants(tripId: String) = "trips/$tripId/invite"
    fun invitations(tripId: String) = "trips/$tripId/invitations"
    fun balances(tripId: String) = "trips/$tripId/balances"
    fun optimizedSettlement(tripId: String) = "trips/$tripId/balances/optimized"
    fun recordSettlement(tripId: String) = "trips/$tripId/balances/record"
    fun insights(tripId: String) = "trips/$tripId/insights"
}
