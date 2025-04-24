package ru.itmo.se.mad.ui.main.products.stepsActivity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.itmo.se.mad.ui.main.products.stepsActivity.fit.FitRepository
import ru.itmo.se.mad.ui.main.products.stepsActivity.di.ViewModelFactoryProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.fitness.FitnessOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ActivityScreen(
    viewModel: ActivityViewModel = viewModel(
        factory = ViewModelFactoryProvider.provideActivityViewModelFactory()
    )
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState !is ActivityUiState.Loading && isRefreshing) {
            isRefreshing = false
        }
    }

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                viewModel.checkPermissionsAndFetchData(context)
            } catch (e: ApiException) {
                viewModel.handleError("Failed to sign in: ${e.message}")
            }
        } else {
            viewModel.handleError("Sign-in canceled or failed")
        }
    }

    val activityRecognitionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            continueWithGoogleFitAuth(context, viewModel, signInLauncher)
        } else {
            viewModel.handleError("Activity recognition permission is required to track steps")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkInitialState(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (val state = uiState) {
            is ActivityUiState.Initial -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Initializing...",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            is ActivityUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Updating step data...",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            is ActivityUiState.NeedsPermission -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                                    context.checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                                    activityRecognitionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
                                } else {
                                    continueWithGoogleFitAuth(context, viewModel, signInLauncher)
                                }
                            } catch (e: Exception) {
                                viewModel.handleError("Failed to start auth flow: ${e.message}")
                            }
                        }
                    ) {
                        Text("Connect to Google Fit")
                    }
                }
            }
            is ActivityUiState.Success -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            StepsActivityWidget(
                                steps = state.steps,
                                dailyGoal = state.dailyGoal
                            )
                        }

                        if (state.lastUpdated.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Last updated: ${state.lastUpdated}",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Button(
                                onClick = {
                                    isRefreshing = true
                                    viewModel.checkPermissionsAndFetchData(context)
                                }
                            ) {
                                Text("Refresh Data")
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }

                        item {
                            OutlinedButton(
                                onClick = {
                                    viewModel.signOut(context)
                                },
                                modifier = Modifier.padding(vertical = 16.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "Logout",
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Disconnect from Google Fit")
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
            is ActivityUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = {
                                viewModel.checkPermissionsAndFetchData(context)
                            }
                        ) {
                            Text("Try Again")
                        }
                    }
                }
            }
        }

        if (isRefreshing) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

private fun continueWithGoogleFitAuth(
    context: Context,
    viewModel: ActivityViewModel,
    signInLauncher: ActivityResultLauncher<Intent>
) {
    val account = GoogleSignIn.getLastSignedInAccount(context)

    if (account == null) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        val signInClient = GoogleSignIn.getClient(context, gso)
        signInLauncher.launch(signInClient.signInIntent)
    } else if (context is Activity) {
        val fitnessOptions = viewModel.getFitnessOptions() as FitnessOptions
        GoogleSignIn.requestPermissions(
            context,
            GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
            account,
            fitnessOptions
        )
    } else {
        viewModel.handleError("Context must be an Activity to request permissions")
    }
}

private const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1001

class ActivityViewModel(private val fitRepository: FitRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ActivityUiState>(ActivityUiState.Initial)
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

    fun checkInitialState(context: Context) {
        if (fitRepository.hasPermissions(context)) {
            checkPermissionsAndFetchData(context)
        } else {
            _uiState.value = ActivityUiState.NeedsPermission
        }
    }

    fun checkPermissionsAndFetchData(context: Context) {
        _uiState.value = ActivityUiState.Loading
        
        viewModelScope.launch {
            try {
                if (fitRepository.hasPermissions(context)) {
                    val activityData = fitRepository.getDailyStepCount(context)
                    val steps = activityData.firstOrNull()?.steps ?: 0
                    val dailyGoal = 8000  // Default goal value
                    
                    val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                    val lastUpdated = dateFormat.format(Date())
                    
                    _uiState.value = ActivityUiState.Success(
                        steps = steps,
                        dailyGoal = dailyGoal,
                        lastUpdated = lastUpdated
                    )
                } else {
                    _uiState.value = ActivityUiState.NeedsPermission
                }
            } catch (e: Exception) {
                _uiState.value = ActivityUiState.Error("Failed to load step data: ${e.message}")
            }
        }
    }

    fun handleError(message: String) {
        _uiState.value = ActivityUiState.Error(message)
    }

    fun getFitnessOptions() = fitRepository.getFitnessOptions()
    
    fun signOut(context: Context) {
        val signInClient = GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
        signInClient.signOut().addOnCompleteListener {
            _uiState.value = ActivityUiState.NeedsPermission
        }
    }

    class Factory(private val fitRepository: FitRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ActivityViewModel::class.java)) {
                return ActivityViewModel(fitRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class ActivityUiState {
    object Initial : ActivityUiState()
    object Loading : ActivityUiState()
    object NeedsPermission : ActivityUiState()
    data class Success(
        val steps: Int,
        val dailyGoal: Int,
        val lastUpdated: String
    ) : ActivityUiState()
    data class Error(val message: String) : ActivityUiState()
}