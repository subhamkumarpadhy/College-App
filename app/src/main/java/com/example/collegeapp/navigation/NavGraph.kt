package com.example.collegeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collegeapp.admin.screens.AdminDashboard
import com.example.collegeapp.admin.screens.FacultyDetailsScreen
import com.example.collegeapp.admin.screens.ManageBanner
import com.example.collegeapp.admin.screens.ManageCollegeInfo
import com.example.collegeapp.admin.screens.ManageFaculty
import com.example.collegeapp.admin.screens.ManageGallery
import com.example.collegeapp.admin.screens.ManageNotice
import com.example.collegeapp.screens.AboutUs
import com.example.collegeapp.screens.BottomNav
import com.example.collegeapp.screens.Faculty
import com.example.collegeapp.screens.Gallery
import com.example.collegeapp.screens.Home
import com.example.collegeapp.utlis.Constant.isAdmin


@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = if (isAdmin) Routes.AdminDashboard.route else Routes.BottomNav.route
    ) {
        composable(Routes.BottomNav.route) {
            BottomNav(navController)
        }
        composable(Routes.Home.route) {
            Home()
        }
        composable(Routes.Gallery.route) {
            Gallery()
        }
        composable(Routes.AboutUs.route) {
            AboutUs()
        }
        composable(Routes.Faculty.route) {
            Faculty(navController)
        }
        composable(Routes.AdminDashboard.route) {
            AdminDashboard(navController)
        }
        composable(Routes.ManageBanner.route) {
            ManageBanner(navController)
        }
        composable(Routes.ManageFaculty.route) {
            ManageFaculty(navController)
        }
        composable(Routes.ManageGallery.route) {
            ManageGallery(navController)
        }
        composable(Routes.ManageCollegeInfo.route) {
            ManageCollegeInfo(navController)
        }
        composable(Routes.ManageNotice.route) {
            ManageNotice(navController)
        }
        composable(Routes.FacultyDetailsScreen.route) {
            val data = it.arguments!!.getString("catName")
            FacultyDetailsScreen(navController, data!!)
        }
    }
}