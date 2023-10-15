import javax.swing.*;
import java.awt.*;

public class MineSweeperBoard extends JPanel {

    private GridTile[][] gridTiles;
    private int gridSize;
    private int mineFrequency;
    private boolean isFirstMove;
    private int mineCount;

    public MineSweeperBoard(int gridSize, int minePercentage) {
        setLayout(new GridLayout(gridSize, gridSize));
        this.mineFrequency = minePercentage;
        this.gridSize = gridSize;
        gridTiles = new GridTile[gridSize][gridSize];
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                gridTiles[y][x] = new GridTile(x, y, this);
                add(gridTiles[y][x]);
            }
        }
        resetBoard(mineFrequency);

    }


    public void checkForWin() {
        int exploredTiles = 0;
        for (int y = 0; y < gridSize; y++)
            for (int x = 0; x < gridSize; x++)
                if (gridTiles[y][x].isRevealed()) exploredTiles++;
        if (((gridSize*gridSize)-exploredTiles) <= mineCount) {
            JOptionPane.showMessageDialog(null, "You Win!");
            resetBoard(mineFrequency);
        }
    }

    public void resetBoard(int mineFrequency) {
        isFirstMove = true;
        mineCount = 0;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                gridTiles[y][x].resetTile();
                boolean isAMine = 100*Math.random()<=mineFrequency;
                if (isAMine) {mineCount++;}
                gridTiles[y][x].setIsAMine(isAMine);
            }
        }
    }

    public void calculateAllTilesMineCount() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                gridTiles[y][x].calculateMineCount();
            }
        }
    }

    public boolean isFirstMove() {
        if (isFirstMove) {
            isFirstMove = false;
            return true;
        }
        return false;
    }

    public GridTile getGridTileAt(int x, int y) {
        return (x>-1&&y>-1&&x<gridSize&&y<gridSize)?gridTiles[y][x]:null;
    }

    public void doGameOver() {
        JOptionPane.showMessageDialog(null, "Game Over");
        resetBoard(mineFrequency);
    }

}