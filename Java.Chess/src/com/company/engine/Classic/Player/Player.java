package com.company.engine.Classic.Player;

import com.company.engine.Classic.Alliance;
import com.company.engine.Classic.board.Board;
import com.company.engine.Classic.board.Move;
import com.company.engine.Classic.board.MoveTransition;
import com.company.engine.Classic.Pieces.King;
import com.company.engine.Classic.Pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Player {

    //The reason why the playerKing is protected is because that is the system's way of telling the script that the King is the main objective.
    //This means the Player needs to focus their defenses on their King.
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    public final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> playerLegals,
           final Collection<Move> opponentLegals) {
        this.board = board;
        this.playerKing = establishKing();
        playerLegals.addAll(calculateKingCastles(playerLegals, opponentLegals));
        this.legalMoves = Collections.unmodifiableCollection(playerLegals);
        //This asks the system if the opponent's current moves attack the King's position on the Board.
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentLegals).isEmpty();

    }

    //Just implementing the method calculateAttacksOnTile
    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves) {
            if(piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return Collections.unmodifiableList(attackMoves);
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    //Establishes whether or not the current piece you're trying to move is a King or not because, to the system, the King is the objective
    private King establishKing() {
        for(final Piece piece : getActivePieces()) {
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }

        //Tells people that, if they reach this internal code, that it was the wrong board code, and that they need to look harder for the right one.
        throw new RuntimeException("Should not reach here! Not a valid Board!!");
    }

    //All of these methods are basically callouts in a chess game, and this is the beginnings of those systematic callouts.

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && hasEscapeMoves();
    }

    //hasEscapeMoves just checks to see if tht objective(King) has anywhere to go before it decides to verify the checkmate.
    protected boolean hasEscapeMoves() {
        for(final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()) {
                return true;
            }
        }

        return false;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(final Move move) {
        if(!isMoveLegal(move)) {
            return new MoveTransition(this.board, move.undo(), move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();
        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(this.board, transitionBoard, move, MoveStatus.DONE);
    }

    //These are all new methods that needed to be implemented to anc from the Piece and Alliance classes.
    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);
}