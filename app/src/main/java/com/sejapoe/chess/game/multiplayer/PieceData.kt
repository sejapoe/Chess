package com.sejapoe.chess.game.multiplayer

import com.sejapoe.chess.game.piece.*
import com.sejapoe.chess.game.piece.core.PieceColor
import com.sejapoe.chess.game.theme.Theme
import kotlinx.serialization.Serializable

@Serializable
data class PieceData(
    val type: PieceType,
    val color: PieceColor
)

@Suppress("unused")
enum class PieceType {
    KING {
        override fun invoke(pieceColor: PieceColor, theme: Theme) =
            King(pieceColor, theme.resources.king)
    },
    QUEEN {
        override fun invoke(pieceColor: PieceColor, theme: Theme) =
            Queen(pieceColor, theme.resources.queen)
    },
    BISHOP {
        override fun invoke(pieceColor: PieceColor, theme: Theme) =
            Bishop(pieceColor, theme.resources.bishop)
    },
    KNIGHT {
        override fun invoke(pieceColor: PieceColor, theme: Theme) =
            Knight(pieceColor, theme.resources.knight)
    },
    ROOK {
        override fun invoke(pieceColor: PieceColor, theme: Theme) =
            Rook(pieceColor, theme.resources.rook)
    },
    PAWN {
        override fun invoke(pieceColor: PieceColor, theme: Theme) =
            Pawn(pieceColor, theme.resources.pawn, if (pieceColor == PieceColor.WHITE) 1 else 6)
    };

    abstract operator fun invoke(pieceColor: PieceColor, theme: Theme): Piece
}
