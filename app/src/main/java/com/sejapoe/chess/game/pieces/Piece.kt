package com.sejapoe.chess.game.pieces

import android.graphics.Color
import com.sejapoe.chess.game.board.Board

enum class PieceColor(private val color: Int) {
    BLACK(Color.BLACK), WHITE(Color.WHITE);

    fun toInt() = color
}

sealed interface Piece {
    fun getColor(): PieceColor
    fun getCandidateCells(r: Int, c: Int, board: Board): MutableList<Pair<Int, Int>>
    fun getImageResource(): Int
}