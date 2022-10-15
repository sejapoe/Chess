package com.sejapoe.chess.game.theme

data class Theme(
    val colors: ThemeColors,
    val resources: ThemeResources
) {
    companion object {
        val DEFAULT = Theme(ThemeColors.GREEN, ThemeResources.TAKE_ONE)
    }
}