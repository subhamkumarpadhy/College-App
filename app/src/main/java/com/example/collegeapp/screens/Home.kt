package com.example.collegeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.collegeapp.itemview.NoticeItemView
import com.example.collegeapp.ui.theme.SKYBLUE
import com.example.collegeapp.viewmodel.BannerViewModel
import com.example.collegeapp.viewmodel.CollegeInfoViewModel
import com.example.collegeapp.viewmodel.NoticeViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield


@Composable
fun Home() {

    val bannerViewModel: BannerViewModel = viewModel()
    val bannerList by bannerViewModel.bannerList.observeAsState(null)
    bannerViewModel.getBanner()

    val collegeInfoViewModel: CollegeInfoViewModel = viewModel()
    val collegeInfo by collegeInfoViewModel.collegeInfo.observeAsState(null)
    collegeInfoViewModel.getCollegeInfo()

    val noticeViewModel: NoticeViewModel = viewModel()
    val noticeList by noticeViewModel.noticeList.observeAsState(null)
    noticeViewModel.getNotice()

    val pagerState = rememberPagerState(initialPage = 0)

    val imageSlider = ArrayList<AsyncImagePainter>()

    if (bannerList != null) {
        bannerList!!.forEach {
            imageSlider.add(rememberAsyncImagePainter(it.url))
        }
    }

    LaunchedEffect(Unit) {
        try {
            while (true) {
                yield()
                delay(3000)
                pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % pagerState.pageCount)
            }
        } catch (e: Exception) {
        }
    }

    LazyColumn(modifier = Modifier.padding(8.dp)) {

        item {
            HorizontalPager(
                count = imageSlider.size, state = pagerState
            ) { pager ->
                Card(modifier = Modifier.height(220.dp)) {
                    Image(
                        painter = imageSlider[pager],
                        contentDescription = "banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier.fillParentMaxWidth()
            ) {
                HorizontalPagerIndicator(pagerState = pagerState, modifier = Modifier.padding(8.dp))
            }
        }

        item {
            if (collegeInfo != null) {

                Text(
                    text = collegeInfo!!.name!!,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = collegeInfo!!.description!!, color = Color.White, fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = collegeInfo!!.address!!, color = Color.White, fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = collegeInfo!!.websiteLink!!, color = SKYBLUE, fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Notices",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        items(noticeList ?: emptyList()) {
            NoticeItemView(it, delete = { docId ->
                noticeViewModel.deleteNotice(docId)
            })
        }
    }
}