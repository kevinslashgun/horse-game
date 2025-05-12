package org.kevinslashgun.base.domain.service;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    private int[][] board;
    private int currentX = -1;
    private int currentY = -1;
    private int moveCount = 0;
    
    public void initializeGame(int size) {
        this.board = new int[size][size];
        this.currentX = -1;
        this.currentY = -1;
        this.moveCount = 0;
    }
    
    public boolean makeMove(int x, int y) {
        if (currentX == -1 && currentY == -1) {
            currentX = x;
            currentY = y;
            board[y][x] = ++moveCount;
            return true;
        }
        
        if (isValidMove(x, y)) {
            currentX = x;
            currentY = y;
            board[y][x] = ++moveCount;
            return true;
        }
        
        return false;
    }
    
    public boolean isGameWon() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) return false;
            }
        }
        return true;
    }
    
    public boolean isVisited(int x, int y) {
        return board[y][x] > 0;
    }
    
    public int getMoveNumber(int x, int y) {
        return board[y][x];
    }
    
    public int[] getCurrentPosition() {
        return new int[]{currentX, currentY};
    }
    
    public int getBoardSize() {
        return board.length;
    }
    
    private boolean isValidMove(int x, int y) {
        int dx = Math.abs(x - currentX);
        int dy = Math.abs(y - currentY);
        return dx * dy == 2;
    }
}