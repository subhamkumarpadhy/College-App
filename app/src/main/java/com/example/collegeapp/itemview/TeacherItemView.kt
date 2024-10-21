package com.example.collegeapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.rememberAsyncImagePainter
import com.example.collegeapp.R
import com.example.collegeapp.models.FacultyModel
import com.example.collegeapp.ui.theme.SKYBLUE
import com.example.collegeapp.utlis.Constant.isAdmin

@Composable
fun TeacherItemView(
    facultyModel: FacultyModel,
    delete: (facultyModel: FacultyModel) -> Unit
) {

    OutlinedCard(modifier = Modifier.padding(4.dp)) {
        ConstraintLayout() {

            val (image, delete) = createRefs()

            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = facultyModel.imageUrl
                    ),
                    contentDescription = "banner_image",
                    modifier = Modifier
                        .height(130.dp)
                        .width(130.dp)
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = facultyModel.name!!,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = facultyModel.email!!,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                    fontSize = 14.sp,
                    color = SKYBLUE
                )
                Text(
                    text = facultyModel.position!!,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                    fontSize = 14.sp,
                )

            }

            if (isAdmin) {
                Card(modifier = Modifier
                    .constrainAs(delete) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(7.dp)
                    .clickable {
                        delete(facultyModel)
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
}