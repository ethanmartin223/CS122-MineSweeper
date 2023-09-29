import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GridTile extends JButton {
    private static final byte[][] SURROUNDINGS = new byte[][] {
            {-1,-1}, {-1, 0}, {-1, 1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};

    private int x, y;
    private boolean isAMine;
    private boolean isRevealed = false;
    private byte numberOfMinesAround;
    private MineSweeperBoard parentBoard;

    public GridTile(int pX, int pY, MineSweeperBoard parent) {
        super();
        setForeground(Color.WHITE);
        setBorderPainted(false);
        addActionListener(this::onClick);
        x = pX;
        y = pY;
        parentBoard = parent;
        setBorder(null);
    }

    public void setIsAMine(boolean v) {isAMine = v;}

    public void calculateMineCount() {
        if (!isAMine) {
            for (byte[] p : SURROUNDINGS) {
                GridTile tile = parentBoard.getGridTileAt(x + p[0], y + p[1]);
                if (tile != null && tile.isAMine) numberOfMinesAround++;
            }
        }
    }

    private void reveal() {
        setBackground(isAMine?Color.RED:Color.GREEN);
        if (isAMine) setText("*");
        else setText(""+((numberOfMinesAround>0)?numberOfMinesAround:""));
        isRevealed = true;
        setEnabled(false);
    }

    private void onClick(ActionEvent e) {
        if (parentBoard.isFirstMove()) {
            isAMine = false;
            for (byte[] p : SURROUNDINGS) {
                GridTile tile = parentBoard.getGridTileAt(x + p[0], y + p[1]);
                if (tile!=null && tile.isAMine) tile.isAMine = false;
            }
            parentBoard.calculateAllTilesMineCount();
        }
        if (!isRevealed && !isAMine) {
            reveal();
            if (numberOfMinesAround == 0) {
                for (byte[] p : SURROUNDINGS) {
                    GridTile tile = parentBoard.getGridTileAt(x + p[0], y + p[1]);
                    if (tile != null) {
                        if (tile.numberOfMinesAround == 0) {
                            tile.onClick(e);
                        }
                        else tile.reveal();
                    }
                }
            }
        } else if (isAMine){
            reveal();
            parentBoard.doGameOver();
        }
    }

}

