package com.company.engine.Classic.board;

import com.company.engine.Classic.Player.MoveStatus;

public class MoveTransition {

    private final Board transitionBoard;
    private final Board fromBoard;
    private final Board toBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    //toBoard and fromBoard tell the system where you are moving to and what tile you can from
    //moveStatus asks the system if what the state of the move you're trying to make is, i.e. valid, done, etc.
    public MoveTransition(final Board fromBoard,
                          final Board transitionBoard,
                          final Board toBoard,
                          final Move move,
                          final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getTransitionBoard() {
        return this.transitionBoard;
    }

    public Board getToBoard() {
        return this.toBoard;
    }

    public Board getFromBoard() {
        return this.fromBoard;
    }
}
