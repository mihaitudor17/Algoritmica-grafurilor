import java.util.*;
import java.lang.*;
import java.io.*;
import boruvka_mst.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static boruvka_mst.Boruvka_MST.Boruvka;

class main {
    private static void initUI() {
        JFrame f = new JFrame("Algoritmica Grafurilor");
        //sa se inchida aplicatia atunci cand inchid fereastra
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //imi creez ob MyPanel
        f.add(new MyPanel());
        //setez dimensiunea ferestrei
        f.setSize(500, 500);
        //fac fereastra vizibila
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() //new Thread()
        {
            public void run() {
                initUI();
            }
        });
    }
}

