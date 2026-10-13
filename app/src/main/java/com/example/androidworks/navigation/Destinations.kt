package com.example.androidworks.navigation

enum class BottomTab(
    val route: String,
    val title: String
) {
    LIST("list", "Список"),
    PROFILE("profile", "Профиль")
}

object DetailsDestination {
    const val ROUTE = "details/{objectId}"
    const val ARG_OBJECT_ID = "objectId"

    fun createRoute(objectId: Int) = "details/$objectId"
}