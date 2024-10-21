package com.example.collegeapp.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object AboutUs : Routes("aboutUs")
    object Gallery : Routes("gallery")
    object Faculty : Routes("faculty")
    object BottomNav : Routes("bottom_nav")
    object AdminDashboard : Routes("admin_dashboard")
    object ManageBanner : Routes("manage_banner")
    object ManageFaculty : Routes("manage_faculty")
    object ManageGallery : Routes("manage_gallery")
    object ManageNotice : Routes("manage_notice")
    object ManageCollegeInfo : Routes("college_info")
    object FacultyDetailsScreen : Routes("faculty_details/{catName}")
}