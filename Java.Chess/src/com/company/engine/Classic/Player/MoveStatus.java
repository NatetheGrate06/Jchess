package com.company.engine.Classic.Player;

public enum MoveStatus {
    //This class just lists the types of move statuses that are possible
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    },
    //Illegal move tells the system that the move status of a certain move is invalid.
    ILLEGAL_MOVE {
        @Override
        public boolean isDone() {
            return false;
        }
    },
    LEAVES_PLAYER_IN_CHECK {
        @Override
        public boolean isDone() {
            return false;
        }
    };

    public abstract boolean isDone();
}
