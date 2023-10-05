package uz.yuzka.admin.ui.screen

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import uz.yuzka.admin.BuildConfig
import uz.yuzka.admin.R
import uz.yuzka.admin.ui.screen.tools.DistrictModalLayout
import uz.yuzka.admin.ui.screen.tools.GetContentDialog
import uz.yuzka.admin.ui.screen.tools.ModalSheetType
import uz.yuzka.admin.ui.screen.tools.RegionsModalLayout
import uz.yuzka.admin.ui.theme.BackButton
import uz.yuzka.admin.ui.viewModel.main.MainViewModel
import uz.yuzka.admin.ui.viewModel.main.MainViewModelImpl
import uz.yuzka.admin.utils.collectClickAsState
import uz.yuzka.admin.utils.fileFromUri
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun UserScreen(
    viewModel: MainViewModel = hiltViewModel<MainViewModelImpl>(),
    onBackPress: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val context = LocalContext.current

    val getMeData by viewModel.getMeFlow.collectAsState(initial = null)

    var avatar by remember {
        mutableStateOf(getMeData?.data?.avatar)
    }

    var uploadImage by remember {
        mutableStateOf<File?>(null)
    }

    var cameraImage by remember {
        mutableStateOf<Uri?>(null)
    }

    val progress by viewModel.progressFlow.collectAsState(initial = false)
    val hasLoadedRegions by viewModel.hasLoadedRegions.observeAsState(initial = false)
    val updateSuccess by viewModel.updateUserFlow.observeAsState(initial = false)
    val logoutFlow by viewModel.logoutFlow.collectAsState(initial = false)

    val error by viewModel.errorFlow.collectAsState(initial = null)

    val regions by viewModel.regions.collectAsState(initial = listOf())

    LaunchedEffect(error) {
        if (error != null) {
            Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            viewModel.gotError()
        }
    }

    LaunchedEffect(logoutFlow) {
        if (logoutFlow) {
            onLogoutClick()
            viewModel.gotLogout()
        }
    }

    LaunchedEffect(null) {
        viewModel.getMeFromLocal()
    }

    LaunchedEffect(key1 = updateSuccess) {
        if (updateSuccess) {
            onBackPress()
            viewModel.gotUpdateSuccess()
        }
    }

    LaunchedEffect(hasLoadedRegions) {
        if (!hasLoadedRegions) viewModel.getLocations()
    }

    var name by remember {
        mutableStateOf(getMeData?.data?.name ?: "")
    }

    var surname by remember {
        mutableStateOf(getMeData?.data?.surname ?: "")
    }

    var phone by remember {
        mutableStateOf(getMeData?.data?.username ?: "")
    }

    var city by remember {
        mutableStateOf(getMeData?.data?.region_name ?: "")
    }

    var district by remember {
        mutableStateOf(getMeData?.data?.district_name ?: "")
    }

    var cityId by remember {
        mutableIntStateOf(getMeData?.data?.region_id ?: -1)
    }

    var districtId by remember {
        mutableIntStateOf(getMeData?.data?.district_id ?: -1)
    }

    var address by remember {
        mutableStateOf(getMeData?.data?.address ?: "")
    }

    var region by remember {
        mutableStateOf(regions.find {
            it.id == cityId
        }
        )
    }

    LaunchedEffect(key1 = regions) {
        region = regions.find {
            it.id == cityId
        }
    }

    LaunchedEffect(key1 = getMeData?.data) {
        getMeData?.data?.let {
            name = it.name ?: ""
            surname = it.surname ?: ""
            phone = it.username ?: ""
            city = it.region_name ?: ""
            district = it.district_name ?: ""
            address = it.address ?: ""
            cityId = it.region_id ?: -1
            districtId = it.district_id ?: -1
            avatar = it.avatar
        }
    }

    val pullRefreshState =
        rememberPullRefreshState(refreshing = progress, onRefresh = { viewModel.getMe() })

    val modalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val scope = rememberCoroutineScope()

    val filePickerListener = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                fileFromUri(context, uri)?.let {
                    avatar = it.absolutePath
                    uploadImage = it
                }
            }
        }
    )

    val cameraResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                cameraImage?.let { path ->
                    fileFromUri(context = context, path)?.let { file ->
                        uploadImage = file
                        avatar = file.absolutePath
                    }
                }
            }
        }
    )

    val filePermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (it) {
                filePickerListener.launch("image/*")
            }
        }
    )

    val cameraPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (it) {
                getTmpFileUri(context = context).let { uri ->
                    cameraImage = uri
                    cameraResult.launch(uri)
                }
            }
        }
    )

    fun takeImage() {
        if (checkCameraPermission(context = context)) {
            getTmpFileUri(context = context).let { uri ->
                cameraImage = uri
                cameraResult.launch(uri)
            }
        } else {
            cameraPermission.launch(android.Manifest.permission.CAMERA)
        }
    }

    fun getImage() {
        if (checkFilePermission(context)) filePickerListener.launch("image/*")
        else filePermission.launch(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) android.Manifest.permission.READ_MEDIA_IMAGES
            else android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    var sheetState by remember {
        mutableStateOf<ModalSheetType>(ModalSheetType.Regions)
    }

    var showContentDialog by remember {
        mutableStateOf(false)
    }

    val cityInteractionSource = remember {
        MutableInteractionSource()
    }

    val districtInteractionSource = remember {
        MutableInteractionSource()
    }

    val onCityClick: () -> Unit = {
        sheetState = ModalSheetType.Regions
        if (regions.isNotEmpty()) scope.launch {
            modalState.show()
        } else {
            if (!hasLoadedRegions) {
                viewModel.getLocations()
            }
        }
    }

    if (cityInteractionSource.collectClickAsState().value) {
        onCityClick()
    }

    val onDistrictClick: () -> Unit = {
        sheetState = ModalSheetType.District
        if (region != null) {
            scope.launch {
                modalState.show()
            }
        } else if (hasLoadedRegions) {
            region = regions.find {
                it.id == cityId
            }
            scope.launch {
                modalState.show()
            }
        } else {
            Toast.makeText(context, "Viloyatni tanlang!", Toast.LENGTH_SHORT).show()
        }
    }

    if (districtInteractionSource.collectClickAsState().value) {
        onDistrictClick()
    }

    ModalBottomSheetLayout(
        sheetState = modalState,
        sheetContent = {
            if (sheetState is ModalSheetType.Regions) {
                RegionsModalLayout(regions = regions, onBackPress = {
                    scope.launch {
                        modalState.hide()
                    }
                }
                ) {
                    region = it
                    cityId = it.id
                    city = it.name
                    district = ""
                    scope.launch {
                        modalState.hide()
                    }
                }
            } else if (sheetState is ModalSheetType.District) {
                DistrictModalLayout(region?.districts ?: listOf(),
                    onBackPress = {
                        scope.launch {
                            modalState.hide()
                        }
                    }) {
                    districtId = it.id
                    district = it.name
                    scope.launch {
                        modalState.hide()
                    }
                }
            }
        },
        sheetShape = RoundedCornerShape(20.dp)
    ) {

        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Shaxsiy sahifa",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF202020),
                        )
                    )
                },
                navigationIcon = {
                    BackButton {
                        onBackPress()
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ), modifier = Modifier.shadow(2.dp)
            )
        }) { pad ->
            Box(
                modifier = Modifier
                    .padding(pad)
                    .fillMaxSize()
                    .background(Color(0xFFF0F0F0))
                    .pullRefresh(pullRefreshState)
            ) {

                if (showContentDialog) {
                    Dialog(
                        onDismissRequest = { showContentDialog = false }
                    ) {
                        GetContentDialog(
                            onGalleryClick = {
                                getImage()
                                showContentDialog = false
                            },
                            onCameraClick = {
                                takeImage()
                                showContentDialog = false
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 70.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        AsyncImage(
                            error = painterResource(id = R.drawable.ic_user),
                            placeholder = painterResource(id = R.drawable.ic_user),
                            model = avatar,
                            contentDescription = null,
                            modifier = Modifier
                                .size(67.dp)
                                .clip(CircleShape)
                                .border(width = 1.dp, color = Color(0xFF51AEE7), CircleShape)
                                .background(color = Color(0x3351AEE7))
                                .clickable(interactionSource = remember {
                                    MutableInteractionSource()
                                }, indication = rememberRipple()) {
                                    showContentDialog = true
                                },
                            contentScale = ContentScale.Crop
                        )

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFE9EBEA),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(10.dp),
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.ic_link_m),
                                contentDescription = null,
                                tint = Color.Black
                            )

                            Text(
                                text = "${getMeData?.data?.coins ?: 0}",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                    fontWeight = FontWeight(500),
                                    color = Color.Black,
                                ),
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFE9EBEA),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(10.dp),
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.ic_list),
                                contentDescription = null,
                                tint = Color(0xFF51AEE7)
                            )

                            Text(
                                text = "${getMeData?.data?.id ?: -1}",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF51AEE7),
                                ),
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }

                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 15.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {

                        Text(
                            text = "Ma’lumotlarni o‘zgartirish",

                            // Headline 15px Medium
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF51AEE7),
                            )
                        )

                        TextField(
                            value = name,
                            onValueChange = {
                                name = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Ismingiz")
                            },
                            placeholder = {
                                Text(text = "Ismingiz...")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF868686),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF51AEE7),
                                focusedPlaceholderColor = Color(0xFF868686),
                                unfocusedPlaceholderColor = Color(0xFF868686),
                                disabledPlaceholderColor = Color(0xFF868686),
                                errorPlaceholderColor = Color(0xFF868686),
                                focusedLabelColor = Color(0xFF51AEE7),
                                disabledLabelColor = Color(0xFF51AEE7),
                                errorLabelColor = Color(0xFF51AEE7),
                                unfocusedLabelColor = Color(0xFF51AEE7),
                                errorIndicatorColor = Color.Red,
                                disabledIndicatorColor = Color(0xFF868686),
                                unfocusedIndicatorColor = Color(0xFF868686)
                            ),
                            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
                        )

                        TextField(
                            value = surname,
                            onValueChange = {
                                surname = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Familiyangiz")
                            },
                            placeholder = {
                                Text(text = "Familiya...")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF868686),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF51AEE7),
                                focusedPlaceholderColor = Color(0xFF868686),
                                unfocusedPlaceholderColor = Color(0xFF868686),
                                disabledPlaceholderColor = Color(0xFF868686),
                                errorPlaceholderColor = Color(0xFF868686),
                                focusedLabelColor = Color(0xFF51AEE7),
                                disabledLabelColor = Color(0xFF51AEE7),
                                errorLabelColor = Color(0xFF51AEE7),
                                unfocusedLabelColor = Color(0xFF51AEE7),
                                errorIndicatorColor = Color.Red,
                                disabledIndicatorColor = Color(0xFF868686),
                                unfocusedIndicatorColor = Color(0xFF868686)
                            ),
                            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
                        )

                        TextField(
                            value = phone,
                            onValueChange = {
                                phone = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Telefon raqam")
                            },
                            placeholder = {
                                Text(text = "+998901234455")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF868686),
                                unfocusedTextColor = Color(0xFF868686),
                                disabledTextColor = Color(0xFF868686),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF51AEE7),
                                focusedPlaceholderColor = Color(0xFF868686),
                                unfocusedPlaceholderColor = Color(0xFF868686),
                                disabledPlaceholderColor = Color(0xFF868686),
                                errorPlaceholderColor = Color(0xFF868686),
                                focusedLabelColor = Color(0xFF51AEE7),
                                disabledLabelColor = Color(0xFF51AEE7),
                                errorLabelColor = Color(0xFF51AEE7),
                                unfocusedLabelColor = Color(0xFF51AEE7),
                                errorIndicatorColor = Color.Red,
                                disabledIndicatorColor = Color(0xFF868686),
                                unfocusedIndicatorColor = Color(0xFF868686)
                            ),
                            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                            enabled = false,
                            readOnly = true
                        )

                        TextField(
                            value = city,
                            onValueChange = {

                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Viloyat/shahar")
                            },
                            placeholder = {
                                Text(text = "Viloyat yoki shahar...")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF868686),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF51AEE7),
                                focusedPlaceholderColor = Color(0xFF868686),
                                unfocusedPlaceholderColor = Color(0xFF868686),
                                disabledPlaceholderColor = Color(0xFF868686),
                                errorPlaceholderColor = Color(0xFF868686),
                                focusedLabelColor = Color(0xFF51AEE7),
                                disabledLabelColor = Color(0xFF51AEE7),
                                errorLabelColor = Color(0xFF51AEE7),
                                unfocusedLabelColor = Color(0xFF51AEE7),
                                errorIndicatorColor = Color.Red,
                                disabledIndicatorColor = Color(0xFF868686),
                                unfocusedIndicatorColor = Color(0xFF868686)
                            ),
                            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                            readOnly = true,
                            interactionSource = cityInteractionSource
                        )

                        TextField(
                            value = district,
                            onValueChange = {

                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Tuman")
                            },
                            placeholder = {
                                Text(text = "Tuman...")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF868686),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF51AEE7),
                                focusedPlaceholderColor = Color(0xFF868686),
                                unfocusedPlaceholderColor = Color(0xFF868686),
                                disabledPlaceholderColor = Color(0xFF868686),
                                errorPlaceholderColor = Color(0xFF868686),
                                focusedLabelColor = Color(0xFF51AEE7),
                                disabledLabelColor = Color(0xFF51AEE7),
                                errorLabelColor = Color(0xFF51AEE7),
                                unfocusedLabelColor = Color(0xFF51AEE7),
                                errorIndicatorColor = Color.Red,
                                disabledIndicatorColor = Color(0xFF868686),
                                unfocusedIndicatorColor = Color(0xFF868686)
                            ),
                            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                            readOnly = true,
                            interactionSource = districtInteractionSource
                        )

                        TextField(
                            value = address,
                            onValueChange = {
                                address = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Manzil")
                            },
                            placeholder = {
                                Text(text = "Manzilingiz...")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF868686),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color(0xFF51AEE7),
                                focusedPlaceholderColor = Color(0xFF868686),
                                unfocusedPlaceholderColor = Color(0xFF868686),
                                disabledPlaceholderColor = Color(0xFF868686),
                                errorPlaceholderColor = Color(0xFF868686),
                                focusedLabelColor = Color(0xFF51AEE7),
                                disabledLabelColor = Color(0xFF51AEE7),
                                errorLabelColor = Color(0xFF51AEE7),
                                unfocusedLabelColor = Color(0xFF51AEE7),
                                errorIndicatorColor = Color.Red,
                                disabledIndicatorColor = Color(0xFF868686),
                                unfocusedIndicatorColor = Color(0xFF868686)
                            ),
                            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
                        )

                    }


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }, indication = rememberRipple()
                            ) {
                                viewModel.logout()
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout_box),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )

                        Text(
                            text = "Hisobdan chiqish",
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(500),
                                color = Color.Black,
                            ),
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }

                    Text(
                        text = "Version: ${BuildConfig.VERSION_NAME}",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(
                                top = 10.dp
                            ),
                        fontSize = 12.sp,
                        color = Color(0xFF9C9C9C)
                    )

                }

                Button(
                    onClick = {
                        viewModel.updateUser(
                            name,
                            surname,
                            cityId,
                            districtId,
                            address,
                            uploadImage
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF51AEE7)),
                    enabled = !progress
                ) {
                    Text(
                        text = "O'zgarishlarni saqlash",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }

                PullRefreshIndicator(
                    refreshing = progress,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )

            }
        }

    }

}

fun getTmpFileUri(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

    val tmpFile = File.createTempFile(
        "IMG${timeStamp}",
        ".jpg",
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )

    return FileProvider.getUriForFile(
        context, "${context.packageName}.fileprovider", tmpFile
    )
}

fun checkFilePermission(context: Context): Boolean {
    val checked =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_MEDIA_IMAGES
        ) else ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    return checked == PackageManager.PERMISSION_GRANTED
}

fun checkCameraPermission(context: Context): Boolean {
    val checked =
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        )
    return checked == PackageManager.PERMISSION_GRANTED
}
