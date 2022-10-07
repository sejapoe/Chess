package com.sejapoe.chess.game.theme

import android.graphics.Color
import com.sejapoe.chess.R

enum class Theme(
    val colors: ThemeColors,
    val resources: ThemeResources
) {
    DEFAULT(
        ThemeColors(
            blackCell = CellColor(
                mainColor = Color.rgb(50, 50, 50),
                selectionColor = Color.rgb(80, 50, 50)
            ),
            whiteCell = CellColor(
                mainColor = Color.rgb(90, 90, 90),
                selectionColor = Color.rgb(120, 90, 90)
            ),
            blackPiece = Color.BLACK,
            whitePiece = Color.WHITE
        ),
        ThemeResources(
            R.drawable.pawn,
            R.drawable.rook,
            R.drawable.knight,
            R.drawable.bishop,
            R.drawable.queen,
            R.drawable.king
        )
    )
}