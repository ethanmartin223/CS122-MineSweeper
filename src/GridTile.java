import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import static java.util.Map.entry;

public class GridTile extends JButton {
    private static final byte[][] SURROUNDINGS = new byte[][] {
            {-1,-1}, {-1, 0}, {-1, 1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};

    private static final Map<Integer, Color> numberColorMap = Map.ofEntries(
            entry(1, Color.BLUE),
            entry(2, new Color(12, 62, 14)),
            entry(3, Color.RED),
            entry(4, new Color(12, 12, 62)),
            entry(5, new Color(79, 16, 16)),
            entry(6, new Color(1,2,3)),
            entry(7, new Color(1,2,3)),
            entry(8, new Color(1,2,3))
    );

    private int x, y;
    private boolean isFlagged;
    private boolean isAMine;
    private boolean isRevealed = false;
    private byte numberOfMinesAround;
    private MineSweeperBoard parentBoard;

    public GridTile(int pX, int pY, MineSweeperBoard parent) {
        super();
        x = pX;
        y = pY;
        parentBoard = parent;
        setBackground((x+y)%2==0?Color.LIGHT_GRAY:Color.GRAY);
        setBorderPainted(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
        isFlagged = false;
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                onRightClick(e);
                onClick(e);
                parentBoard.checkForWin();
            }
        });
    }

    public void setIsAMine(boolean v) {isAMine = v;}

    public boolean isRevealed() {
        return isRevealed;
    }

    public void resetTile() {
        setEnabled(true);
        isAMine = false;
        isRevealed = false;
        isFlagged = false;
        numberOfMinesAround = 0;
        setBackground((x+y)%2==0?Color.LIGHT_GRAY:Color.GRAY);
        setText("");

    }

    public void calculateMineCount() {
        if (!isAMine) {
            for (byte[] p : SURROUNDINGS) {
                GridTile tile = parentBoard.getGridTileAt(x + p[0], y + p[1]);
                if (tile != null && tile.isAMine) numberOfMinesAround++;
            }
        }
    }

    private void reveal() {
        if (!isRevealed) {
            setBackground(isAMine ? Color.RED : Color.GREEN);
            setBorder(null);
            if (isAMine) setText("*");
            else setText("" + ((numberOfMinesAround > 0) ? numberOfMinesAround : ""));
            isRevealed = true;
            isFlagged = false;
            setForeground(numberColorMap.get((int) numberOfMinesAround));
        }
    }

    private void onClick(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            recurse();
        }
    }

    private void recurse() {
        //This double checks to make set the first tile revealed to be not a mine,
        // and that it always reveals a 3x3 around the first click.
        if (!isFlagged && parentBoard.isFirstMove()) {
            isAMine = false;
            for (byte[] p : SURROUNDINGS) {
                GridTile tile = parentBoard.getGridTileAt(x + p[0], y + p[1]);
                if (tile != null && tile.isAMine) {
                    tile.isAMine = false;
                    parentBoard.mineCount--;
                }
            }
            parentBoard.calculateAllTilesMineCount();
        }
        if (!isRevealed && !isFlagged && !isAMine) {
            reveal();
            if (numberOfMinesAround == 0) {
                for (byte[] p : SURROUNDINGS) {
                    GridTile tile = parentBoard.getGridTileAt(x + p[0], y + p[1]);
                    if (tile != null) {
                        if (tile.numberOfMinesAround == 0) {
                            tile.recurse();
                        } else tile.reveal();
                    }
                }
            }
        } else if (isAMine && !isFlagged) {
            reveal();
            parentBoard.doGameOver();
        }
    }

    private void onRightClick(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON3 && !isRevealed && !isFlagged) {
            isFlagged = true;
            setText("F");
            setForeground(Color.BLACK);
            setBackground(Color.YELLOW);
        } else if (e.getButton()==MouseEvent.BUTTON3 && isFlagged) {
            isFlagged = false;
            setForeground(Color.BLACK);
            setText("");
            setBackground((x+y)%2==0?Color.LIGHT_GRAY:Color.GRAY);
        }
    }
}

