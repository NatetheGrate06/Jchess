package com.company.Tests;

import com.company.engine.Classic.board.Board;
import com.company.gui.Table;

import static com.company.engine.Classic.board.Board.createStandardBoard;

public class JChess {

    public static void main(String[] args) {

        Board board = createStandardBoard();

        System.out.println(board);
        Table table = new Table();
    }
}
