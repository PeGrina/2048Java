import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;

public class Game extends JComponent implements ActionListener {
    public static final int
            FIELD_EMPTY = 0,
            DIR_UP = 0,
            DIR_RIGHT = 1,
            DIR_DOWN = 2,
            DIR_LEFT = 3;
    public static int score = 0;
    public static int[][] fields, was;


    public void add_random () {
        ArrayList<Integer> t = new ArrayList<Integer>();
        for (int i = 0; i < Const.CEIL_CNT; ++i) {
            for (int j = 0; j < Const.CEIL_CNT; ++j) {
                if (fields[i][j] == FIELD_EMPTY) {
                    t.add(i * Const.CEIL_CNT + j);
                }
            }
        }
        int x = (int)(Math.random() * t.size());
        fields[t.get(x) / Const.CEIL_CNT][t.get(x) % Const.CEIL_CNT] = 2;
    }

    public void init () {
        for (int i = 0; i < Const.CEIL_CNT; ++i) {
            for (int j = 0; j < Const.CEIL_CNT; ++j) {
                fields[i][j] = FIELD_EMPTY;
            }
        }
        add_random(); add_random();
    }

    public void drawCeil (Graphics graph, int i, int j) {
        graph.setFont(new Font("Arial", Font.PLAIN, Const.TEXT_SIZE));
        graph.setColor(Color.BLACK);
        graph.drawString(String.valueOf(fields[i][j]), Const.SX + i * Const.CEIL_SIZE, Const.SY + (j + 1) * Const.CEIL_SIZE);
    }

    public void goUp () {
        for (int j = 0; j < Const.CEIL_CNT; ++j) {
            int[] used = new int[Const.CEIL_CNT];
            for (int i = 0; i < Const.CEIL_CNT; ++i) {
                for (int k = i - 1; k >= -1; --k) {
                    if (k == -1 || fields[j][k] != FIELD_EMPTY) {
                        if (k == -1 || fields[j][k] != fields[j][i] || used[k] == 1) {
                            int x = fields[j][i];
                            fields[j][i] = FIELD_EMPTY;
                            fields[j][k + 1] = x;
                        } else {
                            fields[j][k] *= 2;
                            fields[j][i] = FIELD_EMPTY;
                            used[k] = 1;
                        }
                        break;
                    }
                }
            }
        }
    }


    public void goLeft () {
        for (int j = 0; j < Const.CEIL_CNT; ++j) {
            int[] used = new int[Const.CEIL_CNT];
            for (int i = 0; i < Const.CEIL_CNT; ++i) {
                for (int k = i - 1; k >= -1; --k) {
                    if (k == -1 || fields[k][j] != FIELD_EMPTY) {
                        if (k == -1 || fields[k][j] != fields[i][j] || used[k] == 1) {
                            int x = fields[i][j];
                            fields[i][j] = FIELD_EMPTY;
                            fields[k + 1][j] = x;
                        } else {
                            fields[k][j] *= 2;
                            fields[i][j] = FIELD_EMPTY;
                            used[k] = 1;
                        }
                        break;
                    }
                }
            }
        }
    }

    public void goRight () {
        for (int j = 0; j < Const.CEIL_CNT; ++j) {
            int[] used = new int[Const.CEIL_CNT];
            for (int i = Const.CEIL_CNT - 1; i >= 0; --i) {
                for (int k = i + 1; k <= Const.CEIL_CNT; ++k) {
                    if (k == Const.CEIL_CNT || fields[k][j] != FIELD_EMPTY) {
                        if (k == Const.CEIL_CNT || fields[k][j] != fields[i][j] || used[k] == 1) {
                            int x = fields[i][j];
                            fields[i][j] = FIELD_EMPTY;
                            fields[k - 1][j] = x;
                        } else {
                            fields[k][j] *= 2;
                            fields[i][j] = FIELD_EMPTY;
                            used[k] = 1;
                        }
                        break;
                    }
                }
            }
        }
    }

    public void goDown () {
        for (int j = 0; j < Const.CEIL_CNT; ++j) {
            int[] used = new int[Const.CEIL_CNT];
            for (int i = Const.CEIL_CNT - 1; i >= 0; --i) {
                for (int k = i + 1; k <= Const.CEIL_CNT; ++k) {
                    if (k == Const.CEIL_CNT || fields[j][k] != FIELD_EMPTY) {
                        if (k == Const.CEIL_CNT || fields[j][k] != fields[j][i] || used[k] == 1) {
                            int x = fields[j][i];
                            fields[j][i] = FIELD_EMPTY;
                            fields[j][k - 1] = x;
                        } else {
                            fields[j][k] *= 2;
                            fields[j][i] = FIELD_EMPTY;
                            used[k] = 1;
                        }
                        break;
                    }
                }
            }
        }
    }

    public Game () {
        addKeyListener(new TAdapter());
        setFocusable(true);
        fields = new int[Const.CEIL_CNT][Const.CEIL_CNT];
        was = new int[Const.CEIL_CNT][Const.CEIL_CNT];
        init();
    }

    @Override
    protected void paintComponent (Graphics graph) {
        super.paintComponent(graph);
        graph.setColor(Color.BLACK);
        for (int Y = Const.SY, X = Const.SX, i = 0; i <= Const.CEIL_CNT; ++i, Y += Const.CEIL_SIZE, X += Const.CEIL_SIZE) {
            graph.drawLine(Const.SX, Y, Const.SX + Const.CEIL_SIZE * Const.CEIL_CNT, Y);
            graph.drawLine(X, Const.SY, X, Const.SY + Const.CEIL_SIZE * Const.CEIL_CNT);
        }
        for (int i = 0; i < Const.CEIL_CNT; ++i) {
            for (int j = 0; j < Const.CEIL_CNT; ++j) {
                if (fields[i][j] != FIELD_EMPTY) {
                    drawCeil(graph, i, j);
                }
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public void save () {
        for (int i = 0; i < Const.CEIL_CNT; ++i) {
            for (int j = 0; j < Const.CEIL_CNT; ++j) {
                was[i][j] = fields[i][j];
            }
        }
    }

    public boolean isEq () {
        int f = 0;
        for (int i = 0; i < Const.CEIL_CNT; ++i) {
            for (int j = 0; j < Const.CEIL_CNT; ++j) {
                if (fields[i][j] != was[i][j]) {
                    f = 1;
                }
            }
        }
        if (f == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void paste () {
        for (int i = 0; i < Const.CEIL_CNT; ++i) {
            for (int j = 0; j < Const.CEIL_CNT; ++j) {
                fields[i][j] = was[i][j];
            }
        }
    }

    public boolean check () {
        save();
        goUp();
        if (!isEq()) {
            paste();
            return false;
        }
        goDown();
        if (!isEq()) {
            paste();
            return false;
        }
        goLeft();
        if (!isEq()) {
            paste();
            return false;
        }
        goRight();
        if (!isEq()) {
            paste();
            return false;
        }
        paste();
        return true;
    }

    public void sendMessage (String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    //как же он чувствует

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            Log.status(String.valueOf(key) + " " + String.valueOf(KeyEvent.VK_UP));
            save();
            //как же он костылит
            if (key == KeyEvent.VK_UP) {
                goUp();
            }
            if (key == KeyEvent.VK_RIGHT) {
                goRight();
            }
            if (key == KeyEvent.VK_LEFT) {
                goLeft();
            }
            if (key == KeyEvent.VK_DOWN) {
                goDown();
            }
            if (!isEq()) {
                add_random();
                repaint();
            }
            if (check()) {
                sendMessage("Проигрыш!", "У вас больше нет хода");
                init();
                repaint();
            }
        }
    }
}
