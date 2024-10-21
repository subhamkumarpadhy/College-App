package com.example.collegeapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.collegeapp.R
import com.example.collegeapp.utlis.Constant.isAdmin
import com.example.collegeapp.widget.LoadingDialog

@Composable
fun FacultyItemView(
    catName: String,
    delete: (catName: String) -> Unit,
    onClick: (catName: String) -> Unit
) {
    OutlinedCard(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .clickable {
            onClick(catName)
        }) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

            val (category, delete) = createRefs()

            Text(
                text = catName,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .constrainAs(
                        category
                    ) {
                        start.linkTo(parent.start)
                        end.linkTo(delete.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            if (isAdmin)
                Card(modifier = Modifier
                    .constrainAs(delete) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(7.dp)
                    .clickable {
                        delete(catName)
                    }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_delete_24),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
        }
    }
}