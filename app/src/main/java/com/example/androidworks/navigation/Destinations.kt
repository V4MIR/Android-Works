package com.example.androidworks.navigation

enum class BottomTab(val route: String, val title: String) {
    LIST("list", "Список"),
    FAVORITES("favorites", "Избранное"),
    PROFILE("profile", "Профиль")
}

object FilterDestination {
    const val ROUTE = "filter"
}

object DetailsDestination {
    const val ROUTE = "details/{objectId}"
    const val ARG_OBJECT_ID = "objectId"
    fun createRoute(objectId: Int) = "details/$objectId"
}