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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import com.example.collegeapp.itemview.NoticeItemView
import com.example.collegeapp.ui.theme.Purple40
import com.example.collegeapp.viewmodel.NoticeViewModel
import com.example.collegeapp.widget.LoadingDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNotice(navController: NavController) {

    val context = LocalContext.current
    val noticeViewModel: NoticeViewModel = viewModel()

    val isUploaded by noticeViewModel.isPosted.observeAsState(false)
    val isDeleted by noticeViewModel.isDeleted.observeAsState(false)
    val noticeList by noticeViewModel.noticeList.observeAsState(null)

    val showLoader = remember {
        mutableStateOf(false)
    }

    if (showLoader.value) {
        LoadingDialog(onDismissRequest = {
            showLoader.value = false
        })
    }

    noticeViewModel.getNotice()

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var isNotice by remember {
        mutableStateOf(false)
    }

    var title by remember {
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
            Toast.makeText(context, "Notice uploaded", Toast.LENGTH_SHORT).show()
            noticeViewModel.resetPostedState()
            imageUri = null
            isNotice = false
            title = ""
            link = ""
        }
    }

    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            showLoader.value = false
            Toast.makeText(context, "Notice Deleted", Toast.LENGTH_SHORT).show()
            noticeViewModel.resetDeletedState()
        }
    }


    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Manage Notice", color = Color.White
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
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            isNotice = true
        }) {
            Icon(
                imageVector = Icons.Rounded.Add, contentDescription = null
            )
        }
    }) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            if (isNotice)

                ElevatedCard(modifier = Modifier.padding(8.dp)) {

                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        placeholder = { Text(text = "Notice Title...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )

                    OutlinedTextField(
                        value = link,
                        onValueChange = {
                            link = it
                        },
                        placeholder = { Text(text = "Notice Link...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )

                    Image(
                        painter = if (imageUri == null) painterResource(id = R.drawable.placeholder) else rememberAsyncImagePainter(
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
                                if (imageUri == null) {
                                    Toast.makeText(
                                        context, "Please Provide Image", Toast.LENGTH_SHORT
                                    ).show()
                                } else if (title == "") {
                                    Toast.makeText(
                                        context, "Please Provide Title", Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    noticeViewModel.saveNotice(imageUri!!, title, link)
                                    showLoader.value = true
                                }
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Text(text = "Add Notice")
                        }

                        OutlinedButton(
                            onClick = {
                                imageUri = null
                                isNotice = false
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                }

            LazyColumn {
                items(noticeList ?: emptyList()) {
                    NoticeItemView(it, delete = { docId ->
                        showLoader.value = true
                        noticeViewModel.deleteNotice(docId)
                    })
                }
            }
        }
    }
}