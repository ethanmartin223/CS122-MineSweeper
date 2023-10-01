import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {

        //win settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setTitle("MineSweeper");
        setLocationRelativeTo(null);
        setLayout(new GridLayout());
        setResizable(false);

        MineSweeperBoard board = new MineSweeperBoard(20,10);
        add(board);


        //set main to visible
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
