import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            String operatingSystem = System.getProperty("os.name").toLowerCase();

            if (operatingSystem.contains("win")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if (operatingSystem.contains("nix") || operatingSystem.contains("nux") ||
                    operatingSystem.contains("aix")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } else {
                return;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame mf = new JFrame("Sudoku Solver");
        mf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainForm mainForm = new MainForm();
        mf.setContentPane(mainForm.mainPanel);
        mf.pack();
        mf.setVisible(true);
    }

}