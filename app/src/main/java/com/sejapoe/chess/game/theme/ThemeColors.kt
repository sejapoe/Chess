package com.sejapoe.chess.game.theme

import android.graphics.Color

enum class ThemeColors(
    val blackCell: CellColor,
    val whiteCell: CellColor,
    val blackPiece: Int,
    val whitePiece: Int
) {
    GREEN(
        blackCell = CellColor(
            mainColor = Color.rgb(70, 70, 50),
            selectionColor = Color.rgb(90, 70, 50)
        ),
        whiteCell = CellColor(
            mainColor = Color.rgb(110, 110, 90),
            selectionColor = Color.rgb(130, 110, 90)
        ),
        blackPiece = Color.BLACK,
        whitePiece = Color.WHITE
    ),
    RED(
        blackCell = CellColor(
            mainColor = Color.rgb(70, 50, 50),
            selectionColor = Color.rgb(90, 50, 50)
        ),
        whiteCell = CellColor(
            mainColor = Color.rgb(110, 90, 90),
            selectionColor = Color.rgb(130, 90, 90)
        ),
        blackPiece = Color.BLACK,
        whitePiece = Color.WHITE
    ),
    BW(
        blackCell = CellColor(
            mainColor = Color.BLACK,
            selectionColor = Color.DKGRAY
        ),
        whiteCell = CellColor(
            mainColor = Color.GRAY,
            selectionColor = Color.LTGRAY
        ),
        blackPiece = Color.MAGENTA,
        whitePiece = Color.CYAN
    );

    operator fun plus(i: Int): ThemeColors =
        ThemeColors.values()[(values().indexOf(this) + i + values().size) % values().size]

    operator fun minus(i: Int): ThemeColors = this + (-i)
}
