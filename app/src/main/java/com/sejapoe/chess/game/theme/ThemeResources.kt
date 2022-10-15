package com.sejapoe.chess.game.theme

import com.sejapoe.chess.R

enum class ThemeResources(
    val pawn: Int,
    val rook: Int,
    val knight: Int,
    val bishop: Int,
    val queen: Int,
    val king: Int,
) {
    TAKE_ONE(
        R.drawable.pawn,
        R.drawable.rook,
        R.drawable.knight,
        R.drawable.bishop,
        R.drawable.queen,
        R.drawable.king
    );

    operator fun plus(i: Int): ThemeResources =
        ThemeResources.values()[(values().indexOf(this) + i + values().size) % values().size]

    operator fun minus(i: Int): ThemeResources = this + (-i)
}
