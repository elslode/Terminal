package com.elish.terminal.presentation

import com.elish.terminal.data.Bar

sealed class TerminalScreenState {
    object Initial : TerminalScreenState()
    object Loading : TerminalScreenState()

    data class Content(
        val barList: List<Bar>,
        val timeFrame: TimeFrame
    ) : TerminalScreenState()
}