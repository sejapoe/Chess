package com.sejapoe.chess.game.board

import com.sejapoe.chess.game.board.cell.ICell

interface IBoard {
    val state: BoardState
    val cells: MutableList<MutableList<ICell>>
}