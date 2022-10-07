package com.sejapoe.chess.game.board.cell

enum class CellColor(val mainColor: Int, val selectionColor: Int) {
    BLACK(0xFFD18B47.toInt(), 0xFF855425.toInt()),
    WHITE(0xFFF1A963.toInt(), 0xFFA87D53.toInt());
}