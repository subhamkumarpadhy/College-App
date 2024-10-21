package com.example.collegeapp.models

data class NavItem(
    val title: String,
    val icon: Int
)

data class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
)
