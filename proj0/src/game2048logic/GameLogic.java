package game2048logic;

import game2048rendering.Board;
import game2048rendering.Side;
import net.sf.saxon.expr.Component;

import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        // TODO: Fill this in in tasks 2, 3, 4
        int val = board[r][c];
        // Nothing to move if the square is empty
        if (val == 0) {
            return 0;
        }
        // Clear the original position
        board[r][c] = 0;

        // Slide upward until we hit the minR or a non-empty square immediately above
        int newR = r;
        while (newR > minR && board[newR - 1][c] == 0) {
            newR--;
        }

        // If we stopped because of a same‐valued tile, merge
        if (newR > minR && board[newR - 1][c] == val) {
            int mergeRow = newR - 1;
            board[mergeRow][c] = val * 2;
            return mergeRow + 1;
        }

        // Otherwise, place the tile at newR and report no merge
        board[newR][c] = val;
        return 0;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        // TODO: fill this in in task 5
        int total_row = board.length;
        int minR = 0;
        for(int i=0; i<total_row; i++){
            int newR = moveTileUpAsFarAsPossible(board, i, c, minR);
            if(newR > 0){
                minR = newR;
            }
        }
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        // TODO: fill this in in task 6
        int total_column = board[0].length;

        for(int i=0; i<total_column; i++){
            tiltColumn(board, i);
        }
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // TODO: fill this in in task 7
        if (side == Side.EAST) {
            MatrixUtils.rotateLeft(board);
            tiltUp(board);
            MatrixUtils.rotateRight(board);
        } else if (side == Side.WEST) {
            MatrixUtils.rotateRight(board);
            tiltUp(board);
            MatrixUtils.rotateLeft(board);
        } else if (side == Side.SOUTH) {
            MatrixUtils.rotateRight(board);
            MatrixUtils.rotateRight(board);
            tiltUp(board);
            MatrixUtils.rotateLeft(board);
            MatrixUtils.rotateLeft(board);
        } else {
            tiltUp(board);
        }
    }
}
