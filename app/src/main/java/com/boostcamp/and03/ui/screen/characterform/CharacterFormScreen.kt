package com.boostcamp.and03.ui.screen.characterform

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.boostcamp.and03.R
import com.boostcamp.and03.ui.component.And03AppBar
import com.boostcamp.and03.ui.component.And03Button
import com.boostcamp.and03.ui.component.And03Dialog
import com.boostcamp.and03.ui.component.And03InfoSection
import com.boostcamp.and03.ui.component.ButtonVariant
import com.boostcamp.and03.ui.component.PhotoPickerBottomSheet
import com.boostcamp.and03.ui.component.DialogDismissAction
import com.boostcamp.and03.ui.screen.canvasmemo.component.PersonImagePlaceholder
import com.boostcamp.and03.ui.theme.And03ComponentSize
import com.boostcamp.and03.ui.theme.And03Padding
import com.boostcamp.and03.ui.theme.And03Spacing
import com.boostcamp.and03.ui.theme.And03Theme
import com.boostcamp.and03.ui.util.collectWithLifecycle
import com.boostcamp.and03.ui.util.createTempImageUri

private object DescriptionInputSectionValues {
    const val MAX_CHARACTER_COUNT = 500
}

@Composable
fun CharacterFormRoute(
    navigateBack: () -> Unit,
    viewModel: CharacterFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.event.collectWithLifecycle { event ->
        when (event) {
            CharacterFormEvent.NavigateBack -> navigateBack()
        }
    }

    CharacterFormScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun CharacterFormScreen(
    uiState: CharacterFormUiState,
    onAction: (CharacterFormAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var preview by remember { mutableStateOf<Bitmap?>(null) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
        )
    }

    val requestCameraPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    val getTakePicture =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                tempImageUri?.let {
                    onAction(CharacterFormAction.OnImageSelected(it))
                }
            }
            Log.d("CharacterFormScreen", "getTakePicture: $success")

        }

    val getContentImage =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                onAction(CharacterFormAction.OnImageSelected(it))
            }
            Log.d("CharacterFormScreen", "getContentImage: $uri")
        }

    BackHandler {
        if (uiState.isExitConfirmationDialogVisible) {
            onAction(CharacterFormAction.CloseExitConfirmationDialog)
        } else if (uiState.isEdited) {
            onAction(CharacterFormAction.OnBackClick)
        } else {
            onAction(CharacterFormAction.CloseScreen)
        }
    }

    Scaffold(
        topBar = {
            And03AppBar(
                title = stringResource(id = R.string.character_form_title),
                onBackClick = { onAction(CharacterFormAction.OnBackClick) }
            )
        },
        bottomBar = {
            And03Button(
                text = if (!uiState.isSaving) {
                    stringResource(id = R.string.add_memo_save_button_text)
                } else {
                    stringResource(id = R.string.add_memo_saving_button_text)
                },
                onClick = { onAction(CharacterFormAction.OnSaveClick) },
                variant = ButtonVariant.Primary,
                enabled = uiState.isSaveable && !uiState.isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                        )
                    )
                    .padding(And03Padding.PADDING_L)
                    .height(And03ComponentSize.BUTTON_HEIGHT_L)
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(And03Padding.PADDING_L)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_L),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                And03InfoSection(
                    title = stringResource(R.string.info_section_character_title),
                    description = stringResource(R.string.info_section_character_description)
                )

                PersonImagePlaceholder(
                    iconColor = uiState.profileColor,
                    imageUrl = uiState.imageUrl,
                    profileType = uiState.profileType,
                    onClick = { onAction(CharacterFormAction.OnOpenImagePickerBottomSheet) }
                )

                SingleLineInputSection(
                    value = uiState.name,
                    onValueChange = { onAction(CharacterFormAction.OnNameChange(name = it)) },
                    labelStringRes = R.string.character_form_name,
                    placeholderStringRes = R.string.character_form_enter_name_placeholder
                )

                SingleLineInputSection(
                    value = uiState.role,
                    onValueChange = { onAction(CharacterFormAction.OnRoleChange(role = it)) },
                    labelStringRes = R.string.character_form_role,
                    placeholderStringRes = R.string.character_form_enter_role_placeholder
                )

                DescriptionInputSection(
                    description = uiState.description,
                    onDescriptionChange = {
                        onAction(
                            CharacterFormAction.OnDescriptionChange(
                                description = it
                            )
                        )
                    }
                )
            }

            if (uiState.isVisibleBottomSheet) {
                PhotoPickerBottomSheet(
                    onDismiss = { onAction(CharacterFormAction.OnDismissImagePickerBottomSheet) },
                    onCameraClick = {
                        if (hasCameraPermission) {
                            val uri = createTempImageUri(context)
                            tempImageUri = uri
                            getTakePicture.launch(uri)
                        } else {
                            requestCameraPermission.launch(Manifest.permission.CAMERA)
                        }
                        onAction(CharacterFormAction.OnDismissImagePickerBottomSheet)
                    },
                    onGalleryClick = {
                        val uri = createTempImageUri(context)
                        tempImageUri = uri
                        getContentImage.launch("image/*")

                        onAction(CharacterFormAction.OnDismissImagePickerBottomSheet)
                    }
                )
            }
        }

        if (uiState.isEdited && uiState.isExitConfirmationDialogVisible) {
            And03Dialog(
                iconResId = R.drawable.ic_warning_filled,
                iconColor = And03Theme.colors.error,
                iconContentDescription = stringResource(id = R.string.content_description_caution),
                title = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_title),
                dismissText = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_dismiss_text),
                confirmText = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_confirm_text),
                onDismiss = { onAction(CharacterFormAction.CloseScreen) },
                onConfirm = { onAction(CharacterFormAction.CloseExitConfirmationDialog) },
                description = stringResource(id = R.string.canvas_memo_exit_confirmation_dialog_description),
                dismissAction = DialogDismissAction.Confirm
            )
        }
    }
}

@Composable
private fun SingleLineInputSection(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelStringRes: Int,
    @StringRes placeholderStringRes: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S)
    ) {
        Text(
            text = stringResource(labelStringRes),
            style = And03Theme.typography.labelLarge
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(id = placeholderStringRes)) },
            shape = And03Theme.shapes.defaultCorner,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        )
    }
}

@Composable
private fun DescriptionInputSection(
    description: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(And03Spacing.SPACE_S)
    ) {
        Text(
            text = stringResource(R.string.character_form_description),
            style = And03Theme.typography.labelLarge
        )

        OutlinedTextField(
            value = description,
            onValueChange = {
                if (it.length <= DescriptionInputSectionValues.MAX_CHARACTER_COUNT) onDescriptionChange(
                    it
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .height(And03ComponentSize.TEXT_FIELD_HEIGHT_L),
            placeholder = { Text(stringResource(id = R.string.character_form_enter_description_placeholder)) },
            shape = And03Theme.shapes.defaultCorner
        )

        Text(
            text = stringResource(
                id = R.string.add_memo_character_count,
                description.length,
                DescriptionInputSectionValues.MAX_CHARACTER_COUNT
            ),
            modifier = Modifier.align(Alignment.End),
            style = And03Theme.typography.bodySmall,
            color = And03Theme.colors.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun CharacterFormScreenPreview() {
    And03Theme {
        CharacterFormScreen(
            uiState = CharacterFormUiState(
                name = "그레고르 잠자",
                role = "주인공",
                description = "어느 날, 잠에서 깨어나 보니 갑충이 된 남자."
            ),
            onAction = {}
        )
    }
}