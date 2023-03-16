package com.gicproject.sohpreadqrcode.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gicproject.sohpreadqrcode.Screen

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DemoScreen(
    navController: NavController,
    viewModel: MyViewModel,
) {
    val closeEmployeeScreen = viewModel.resetScreens.value

    Scaffold(
        modifier = Modifier.background(color = Color.White)
    ) { innerPadding ->
        Modifier.padding(innerPadding)
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row{
                    Image(
                        //  painter = rememberAsyncImagePainter(viewModel.photoUri.value),
                        painter = painterResource(id = com.gicproject.sohpreadqrcode.R.drawable.sampleemployee),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp, 200.dp)
                            .clip(CircleShape)
                            .border(4.dp, MaterialTheme.colors.primary, CircleShape)
                    )
                }
                Row(
                    Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Spacer(Modifier.height(10.dp))
                        TextInfo("Employee ID: ", " 19966")
                        Spacer(Modifier.height(10.dp))
                        TextInfo("Employee Name: ", " Mohammad Raza")
                        Spacer(Modifier.height(10.dp))
                        TextInfo("Department: ", " Information Technology")
                        Spacer(Modifier.height(10.dp))
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                    ) {
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row{
                    Image(
                        //  painter = rememberAsyncImagePainter(viewModel.photoUri.value),
                        painter = painterResource(id = com.gicproject.sohpreadqrcode.R.drawable.qrcode),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp, 120.dp)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row{
                    Button(onClick = {
                        navController.navigate(Screen.QrReaderScreen.route)

                    }) {
                        Text("Scan a QR code")
                    }
                }
            }
        }

    }
}

