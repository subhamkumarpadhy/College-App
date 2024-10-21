package com.example.collegeapp.admin.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp.models.DashboardItemModel
import com.example.collegeapp.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(navController: NavController) {
    val list = listOf(
        DashboardItemModel("Manage banner", Routes.ManageBanner.route),
        DashboardItemModel("Manage notice", Routes.ManageNotice.route),
        DashboardItemModel("Manage Gallery", Routes.ManageGallery.route),
        DashboardItemModel("Manage Faculty", Routes.ManageFaculty.route),
        DashboardItemModel("Manage College Info", Routes.ManageCollegeInfo.route)
    )

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Admin Dashboard")
        })
    }, content = { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {

            items(items = list, itemContent = {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(it.route)
                    }) {
                    Text(
                        text = it.title,
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            })
        }
    })
}