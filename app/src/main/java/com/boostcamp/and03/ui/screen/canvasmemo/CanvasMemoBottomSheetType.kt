package com.boostcamp.and03.ui.screen.canvasmemo

sealed interface CanvasMemoBottomSheetType {

    data object AddCharacter : CanvasMemoBottomSheetType

    data object AddQuote : CanvasMemoBottomSheetType
}
