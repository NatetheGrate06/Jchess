package com.company.engine.Classic.Pieces;

import com.company.engine.Classic.Alliance;
import com.company.engine.Classic.board.Board;
import com.company.engine.Classic.board.Move;

import java.util.Collection;

public abstract class Piece {

    final PieceType pieceType;
    final Alliance pieceAlliance;
    final int piecePosition;
    private final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final PieceType type,
          final Alliance pieceAlliance,
          final int piecePosition,
          final boolean isFirstMove) {
        this.pieceType = type;
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        //TODO more work to do here!!
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();
    }

    //Computes the hash code for the resulting move.
    private int computeHashCode() {
        int result = this.pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public abstract Piece movePiece(Move move);

    //Calculate legal moves is the method that most of the pieces are based on
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    //Calculates the reference equality for the object
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return this.piecePosition == otherPiece.piecePosition && this.pieceType == otherPiece.pieceType &&
                this.pieceAlliance == otherPiece.pieceAlliance && this.isFirstMove == otherPiece.isFirstMove;
    }


    public int hashCode() {
        return this.cachedHashCode;
    }


    //Assigns the pieces their names and their values
    //Also states whether they are Rooks or Kings or not
    public enum PieceType {

        PAWN("P") {

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return true;
            }
        },
        KNIGHT("N") {

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }
        },
        BISHOP("B") {

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }
        },
        ROOK("R") {

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }

            @Override
            public boolean isPawn() {
                return false;
            }
        },
        QUEEN("Q") {

            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }
        },
        KING("K") {

            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isPawn() {
                return false;
            }
        };

        private final String pieceName;

        @Override
        public String toString() {
            return this.pieceName;
        }

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();

        public abstract boolean isPawn();
    }
}