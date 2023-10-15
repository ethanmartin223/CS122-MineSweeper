import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setTitle("MineSweeper");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        MineSweeperBoard board = new MineSweeperBoard(20,20);
        add(board);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
