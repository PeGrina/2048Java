import javax.swing.*;
import java.awt.*;

public class Ray {
    public static JFrame window;
    public Ray() {
        window = new JFrame("Physics");
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.setSize(Const.W, Const.H);
        window.setLayout(new BorderLayout());
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        Log.access("Started.");
    }
    public static void add (JComponent g) {
        window.add(g);
    }
}
