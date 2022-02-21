package ui;

import model.Event;
import model.EventLog;
import model.Garden;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


// represents the home page of the Hortus app
public class HomeView implements ActionListener {
    JFrame frame;
    JPanel panel;
    JPanel buttonsPanel;

    JsonReader reader = new JsonReader(JSON_STORE);
    JsonWriter writer = new JsonWriter(JSON_STORE);

    private static final String JSON_STORE = "./data/garden.json";
    private static final String GARDEN_ACTION = "GARDEN_ACTION";
    private static final String TODO_ACTION = "TODO_ACTION";
    private static final String SAVE_ACTION = "SAVE_ACTION";
    private static final String LOAD_ACTION = "LOAD_ACTION";

    ImageIcon saveIcon = HomeView.resizeImage("src/main/ui/images/allergy_cat.png", 100, 100);
    ImageIcon loadIcon = HomeView.resizeImage("src/main/ui/images/allergy_dog.png", 100, 100);

    Garden myGarden;

    // ---------------- CONSTRUCTOR ------------------
    public HomeView(Garden myGarden) throws IOException {
        this.myGarden = myGarden;
        frame = new JFrame();
        panel = new JPanel();
        buttonsPanel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        buttonsPanel.setLayout(new GridLayout(2, 2));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hortus");
        panel.setBackground(new Color(206, 212, 140));

        setupLabel();
        createButtons();
        panel.add(buttonsPanel);

        frame.pack();
        frame.setVisible(true);
        frame.setSize(350, 500);
        centerFrameLocation(frame);
        frame.setResizable(false);

        promptDataSaving();
    }

    // EFFECTS: prompts the user to save data when they click on exit.
    // If user selects yes, data is saved to the Json file. Otherwise, data is not saved.
    // MODIFIES: this
    public void promptDataSaving() {
        // source: https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ImageIcon img = resizeImage("src/main/ui/images/capybara.png", 130, 130);
                int choice = JOptionPane.showConfirmDialog(frame,
                        "Do you want to save data before closing?", "Close Window?",
                        JOptionPane.YES_NO_OPTION, 0, img);

                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        saveData();
                    } catch (FileNotFoundException e) {
                        System.out.println("ERROR");
                    }
                    printLog(EventLog.getInstance());
                    System.exit(0);
                } else {
                    printLog(EventLog.getInstance());
                    JOptionPane.showMessageDialog(null, "Okay bye ;-;");
                }
            }
        });
    }

    // EFFECTS: prints all events on theLog to the consolev
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next + "\n");
        }
    }
    // ---------------- LABEL -------------------

    // EFFECTS: creates a label with app name and logo, adds the label to panel
    // MODIFIES: this
    public void setupLabel() {
        JLabel greetingsLabel = new JLabel("- H O R T U S -");

        greetingsLabel.setIcon(resizeImage("src/main/ui/images/plant_cactus.png", 130, 130));

        //sets the text on top of the image
        greetingsLabel.setHorizontalTextPosition(JLabel.CENTER);
        greetingsLabel.setVerticalTextPosition(JLabel.TOP);
        greetingsLabel.setVerticalAlignment(JLabel.CENTER);
        greetingsLabel.setHorizontalAlignment(JLabel.CENTER);

        greetingsLabel.setIconTextGap(25);

        greetingsLabel.setForeground(new Color(0x7b5d4d));
        greetingsLabel.setFont(new Font("Courier", Font.BOLD, 25));

        panel.add(greetingsLabel);
    }


    // --------------- BUTTONS -------------------
    // EFFECTS: creates and configures the buttons on home page
    // MODIFIES: this
    public void createButtons() {
        ImageIcon gardenIcon = HomeView.resizeImage("src/main/ui/images/allergy_kuri.png", 50, 50);
        ImageIcon todoIcon = HomeView.resizeImage("src/main/ui/images/allergy_water.png", 50, 50);
        ImageIcon saveIcon = HomeView.resizeImage("src/main/ui/images/allergy_cat.png", 50, 50);
        ImageIcon loadIcon = HomeView.resizeImage("src/main/ui/images/allergy_dog.png", 50, 50);

        //garden
        JButton gardenButton = new JButton("Garden", gardenIcon);
        configureButton(gardenButton, GARDEN_ACTION);

        //to-do
        JButton todoButton = new JButton(" Todo", todoIcon);
        configureButton(todoButton, TODO_ACTION);

        //save
        JButton saveButton = new JButton(" Save", saveIcon);
        configureButton(saveButton, SAVE_ACTION);

        //load
        JButton loadButton = new JButton(" Load", loadIcon);
        configureButton(loadButton, LOAD_ACTION);
    }

    // EFFECTS: styles the buttons, adds action command and listener, and adds it to the buttonsPanel
    // MODIFIES: this
    public void configureButton(JButton button, String command) {
        styleButton(button);
        buttonsPanel.add(button);
        button.setActionCommand(command);
        button.addActionListener(this);
    }

    // EFFECTS: sets the background, foreground, font, and border of the given button
    // MODIFIES: this
    public void styleButton(JButton btn) {
        btn.setBackground(new Color(206, 212, 140));
        btn.setForeground(new Color(0x717c43));
        btn.setFont(new Font("Courier", Font.PLAIN, 15));
        btn.setOpaque(true);
    }

    //-------------- OTHER STYLING METHODS --------------

    // EFFECTS: sets the frame location to the center of the screen
    // MODIFIES: this
    public static void centerFrameLocation(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2,
                dim.height / 2 - frame.getSize().height / 2);
    }

    // EFFECTS: creates an ImageIcon with the given width and height
    // MODIFIES: this
    public static ImageIcon resizeImage(String pathName, int width, int height) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(width, height,
                Image.SCALE_SMOOTH);

        ImageIcon imageIcon = new ImageIcon(dimg);
        return imageIcon;
    }

    // ------------- MOUSE-EVENT HANDLING -------------
    // EFFECTS: handles mouse events within the frame
    // MODIFIES: this
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case GARDEN_ACTION:
                frame.dispose();
                new GardenView(myGarden);
                break;

            case TODO_ACTION:
                frame.dispose();
                new TodoView(myGarden);
                break;

            case SAVE_ACTION:
                try {
                    saveData();
                } catch (FileNotFoundException ex) {
                    System.out.println("ERROR");
                }
                break;

            case LOAD_ACTION:
                try {
                    myGarden = reader.read();
                    JOptionPane.showMessageDialog(frame, "Your garden has been loaded :)",
                            null, 0, loadIcon);
                } catch (IOException ex) {
                    System.out.println("ERROR");
                }
                break;
        }
    }


    public void saveData() throws FileNotFoundException {
        writer.open();
        writer.write(myGarden);
        writer.close();
        JOptionPane.showMessageDialog(frame, "Your garden has been saved :)",
                null, 0, saveIcon);
    }
}



