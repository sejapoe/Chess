package com.sejapoe.chess.game.multiplayer

import com.sejapoe.chess.game.board.cell.ICell
import kotlinx.serialization.Serializable

@Serializable
data class PieceMovementData(
    val columnSource: Int,
    val rowSource: Int,
    val columnDest: Int,
    val rowDest: Int,
) {
    constructor(sourceCell: ICell, destCell: ICell) : this(
        sourceCell.column,
        sourceCell.row,
        destCell.column,
        destCell.row,
    )
}