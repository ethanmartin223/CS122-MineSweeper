import javax.swing.*;
import java.awt.*;

public class MineSweeperBoard extends JPanel {

    private GridTile[][] gridTiles;
    private int gridSize;
    private int mineFrequency;
    private boolean isFirstMove;

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

    public void resetBoard(int mineFrequency) {
        isFirstMove = true;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                gridTiles[y][x].setIsAMine(100*Math.random()<=mineFrequency);
                gridTiles[y][x].setEnabled(true);
            }
        }
    }

    public int getGridSize() {
        return gridSize;
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
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                gridTiles[y][x].setEnabled(false);
            }
        }
    }

}