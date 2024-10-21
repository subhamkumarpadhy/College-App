package com.example.collegeapp.admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.collegeapp.R
import com.example.collegeapp.ui.theme.Purple40
import com.example.collegeapp.viewmodel.CollegeInfoViewModel
import com.example.collegeapp.widget.LoadingDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCollegeInfo(navController: NavController) {

    val context = LocalContext.current
    val collegeInfoViewModel: CollegeInfoViewModel = viewModel()

    val isUploaded by collegeInfoViewModel.isPosted.observeAsState(false)
    val collegeInfo by collegeInfoViewModel.collegeInfo.observeAsState(null)

    val showLoader = remember {
        mutableStateOf(false)
    }

    if (showLoader.value) {
        LoadingDialog(onDismissRequest = {
            showLoader.value = false
        })
    }

    collegeInfoViewModel.getCollegeInfo()

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var imageUrl by remember {
        mutableStateOf("")
    }

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }

    var link by remember {
        mutableStateOf("")
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
        }

    LaunchedEffect(isUploaded) {
        if (isUploaded) {
            showLoader.value = false
            Toast.makeText(context, "College Info Updated", Toast.LENGTH_SHORT).show()
            imageUri = null
        }
    }

    LaunchedEffect(collegeInfo) {
        if (collegeInfo != null) {
            title = collegeInfo!!.name!!
            link = collegeInfo!!.websiteLink!!
            description = collegeInfo!!.description!!
            address = collegeInfo!!.address!!
            imageUrl = collegeInfo!!.imageUrl!!
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Manage College Info", color = Color.White
            )
        }, colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple40),

            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            })
    }) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            ElevatedCard(modifier = Modifier.padding(8.dp)) {

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    placeholder = { Text(text = "College Name: ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    placeholder = { Text(text = "College Description: ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                    },
                    placeholder = { Text(text = "College Address: ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                OutlinedTextField(
                    value = link,
                    onValueChange = {
                        link = it
                    },
                    placeholder = { Text(text = "Website Link: ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                Image(
                    painter = if (imageUrl != "") rememberAsyncImagePainter(model = imageUrl) else if (imageUri == null) painterResource(
                        id = R.drawable.placeholder
                    ) else rememberAsyncImagePainter(
                        model = imageUri
                    ),
                    contentDescription = "banner_image",
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth()
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )

                Row {
                    Button(
                        onClick = {
                            if (title == "" || description == "" || address == "") {
                                Toast.makeText(
                                    context, "Please Provide All Fields", Toast.LENGTH_SHORT
                                ).show()
                            } else if (imageUrl != "") {
                                collegeInfoViewModel.uploadImage(
                                    imageUrl,
                                    title,
                                    address,
                                    description,
                                    link
                                )
                            } else if (imageUri == null) {
                                Toast.makeText(
                                    context, "Please Provide Image", Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                collegeInfoViewModel.saveImage(
                                    imageUri!!,
                                    title,
                                    address,
                                    description,
                                    link
                                )
                            }
                            showLoader.value = true
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = "Update College Info")
                    }
                }
            }
        }
    }
}