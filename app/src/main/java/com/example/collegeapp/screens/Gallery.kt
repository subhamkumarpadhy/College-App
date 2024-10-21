package com.example.collegeapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collegeapp.itemview.GalleryItemView
import com.example.collegeapp.viewmodel.GalleryViewModel


@Composable
fun Gallery() {

    val galleryViewModel: GalleryViewModel = viewModel()
    val galleryList by galleryViewModel.galleryList.observeAsState(null)

    galleryViewModel.getGallery()

    LazyColumn {
        items(galleryList ?: emptyList()) {
            GalleryItemView (it, delete = { docId ->
                galleryViewModel.deleteGallery(docId)
            }, deleteImage = { cat, imageUrl ->
                galleryViewModel.deleteImage(cat, imageUrl)
            })
        }
    }
}