package csc372module3;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class UserInterface extends JFrame {

    private final JTextArea textBox;
    private final JMenuItem greenMenuItem;

    // Generate one random green hue when the program starts.
    private final float greenHue;

    public UserInterface() {
        super("User Interface");

        // Random green hue: 120°–180° on the HSB color wheel.
        Random random = new Random();
        greenHue = (120 + random.nextFloat() * 60) / 360.0f;

        // Text box
        textBox = new JTextArea(10, 40);
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);

        add(new JScrollPane(textBox), BorderLayout.CENTER);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        // 1. Display date and time
        JMenuItem dateTimeMenuItem = new JMenuItem("Display Date and Time");
        dateTimeMenuItem.addActionListener(e -> displayDateTime());

        // 2. Write text box to log.txt
        JMenuItem saveMenuItem = new JMenuItem("Save to log.txt");
        saveMenuItem.addActionListener(e -> saveToFile());

        // 3. Change background to the initial random green hue
        int hueDegrees = Math.round(greenHue * 360);
        greenMenuItem = new JMenuItem(
                "Green Background (Hue: " + hueDegrees + "°)"
        );
        greenMenuItem.addActionListener(e -> changeBackground());

        // 4. Exit
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));

        // Add the four menu items
        menu.add(dateTimeMenuItem);
        menu.add(saveMenuItem);
        menu.add(greenMenuItem);
        menu.add(exitMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
    }

    // Menu option 1
    private void displayDateTime() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateTime = LocalDateTime.now().format(formatter);

        textBox.setText(dateTime);
    }

    // Menu option 2
    private void saveToFile() {
        try (FileWriter writer = new FileWriter("log.txt")) {
            writer.write(textBox.getText());

            JOptionPane.showMessageDialog(
                    this,
                    "Text successfully saved to log.txt."
            );

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error writing to log.txt: " + ex.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Menu option 3
    private void changeBackground() {
        // Same random hue generated when the program started.
        Color greenColor = Color.getHSBColor(greenHue, 1.0f, 1.0f);

        getContentPane().setBackground(greenColor);
        textBox.setBackground(greenColor);

        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserInterface ui = new UserInterface();
            ui.setVisible(true);
        });
    }
}
