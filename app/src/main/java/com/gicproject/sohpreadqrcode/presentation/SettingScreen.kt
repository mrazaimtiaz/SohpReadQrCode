package com.gicproject.sohpreadqrcode.presentation

import android.content.Context
import android.content.Intent
import android.location.Location
import android.provider.Settings
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.gicproject.sohpreadqrcode.R
import com.gicproject.sohpreadqrcode.Screen
import com.gicproject.sohpreadqrcode.common.Constants
import com.gicproject.sohpreadqrcode.common.toBitmap
import com.gicproject.sohpreadqrcode.domain.model.PatientInfo

@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: MyViewModel,
) {

    val context = LocalContext.current




    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack(route = Screen.MainScreen.route, inclusive = false)
                    }) {
                        Icon(
                            Icons.Filled.KeyboardArrowLeft,
                            "back arrow",
                            tint = Color.White,
                            modifier = Modifier
                                .size(100.dp, 100.dp)
                        )
                    }

                },
            )
        },
    ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colors.surface,
                    )
            ) {
                Modifier.padding(innerPadding)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
//viewModel.funcPrinterConnect("test print")
                        //Constants.baseImage.toBitmap(),null
                        viewModel.testCase(patientInfo =PatientInfo("EA794A2A-0C2F-40CC-BED8-98C89BECEF85","104553","test gic test","84B42E25-7C3F-4B4F-A6AF-D1D7A6B0EC28","Jaber H","1","Active","21/03/2023","11:00 - 11:45","11:00 AM",0,"312052594333") )
                       // viewModel.funcPrinterImage(patientInfo = PatientInfo("EA794A2A-0C2F-40CC-BED8-98C89BECEF85","104553","test gic test","84B42E25-7C3F-4B4F-A6AF-D1D7A6B0EC28","Jaber H","1","Active","21/03/2023","11:00 - 11:45","11:00 AM",0,"312052594333"))
                        }) {
                        Text("print test")
                    }
                    IntentToSetting(context)
                }
            }
    }
}

@Composable
fun IntentToSetting(context: Context) {
    Button(
        modifier = Modifier.padding(top = 30.dp),
        onClick = {
            startActivity(context, Intent(Settings.ACTION_SETTINGS), null)

        }) {
        Icon(
            Icons.Filled.Settings,
            "setting",
            tint = Color.White,
            modifier = Modifier
                .size(30.dp, 30.dp)
        )

        Text(text = "Open Settings", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun ComposeMenu(
    menuItems: List<Location>,
    menuExpandedState: Boolean,
    selectedIndex: Int,
    updateExpandedValue: () -> Unit,
    onDismissMenuView: () -> Unit,
    onMenuItemClick: (Int, Location) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            .clickable(
                onClick = {
                    updateExpandedValue()
                },
            ),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .size(width = 300.dp, height = 60.dp)
                .padding(16.dp)
        ) {
            val (label, iconView) = createRefs()
            Text(
                text = if (selectedIndex != -1) menuItems[selectedIndex].toString() else "Select Location",
                color = Color.Black,
                modifier = Modifier
                    .constrainAs(label) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
            )

            val displayIcon: Painter = painterResource(
                id = R.drawable.ic_drop_down
            )

            Icon(
                painter = displayIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colors.onSurface
            )
            DropdownMenu(
                expanded = menuExpandedState,
                onDismissRequest = { onDismissMenuView() },
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
            ) {
                menuItems.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            onMenuItemClick(index, item)
                        }) {
                        Text(text = item.toString())
                    }
                }
            }
        }
    }
}
