package ui;

import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// represents the garden page of the Hortus app
public class GardenView implements ActionListener {
    JFrame frame;
    JPanel panel;

    JPanel cardsPanel;
    JPanel buttonsPanel;
    private Garden myGarden;

    private static final String ADD_PLANT_ACTION = "ADD_PLANT_ACTION";
    private static final String RETURN_ACTION = "RETURN_ACTION";
    private static final int COL_NUM = 3;
    int colCount = 0;
    int preferredHeight = 100;

    private static final Color GREEN = new Color(206, 212, 140);
    private static final Color BROWN = new Color(0x574537);


    // ---------------- CONSTRUCTOR ------------------
    public GardenView(Garden garden) {
        myGarden = garden;
        frame = new JFrame();
        panel = new JPanel();
        panel.setBackground(GREEN);

        buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.setBackground(GREEN);

        cardsPanel = new JPanel(new GridLayout(0, COL_NUM));
        cardsPanel.setBackground(GREEN);
        fillCardsPanel();

        createButtons();
        panel.add(buttonsPanel);
        panel.add(cardsPanel);

        panel.setPreferredSize(new Dimension(400, preferredHeight));

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        frame.add(scrollPane);
        frame.setSize(450, 500);

        frame.setTitle("Garden");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        HomeView.centerFrameLocation(frame);
    }

    // ---------------- BUTTONS ------------------
    // EFFECTS: creates the buttons for the garden page
    // MODIFIES: this
    private void createButtons() {
        JButton addButton = new JButton("+");
        configureButton(addButton, ADD_PLANT_ACTION);

        JButton returnButton = new JButton("Back");
        configureButton(returnButton, RETURN_ACTION);
    }

    // EFFECTS: adds the button to the buttonsPanel, sets action command and listener
    // MODIFIES: this
    private void configureButton(JButton button, String command) {
        buttonsPanel.add(button);
        button.setActionCommand(command);
        button.addActionListener(this);
    }

    // ---------------- CARD PANEL ------------------
    // EFFECTS: creates the plant profile cards
    // MODIFIES: this
    private void fillCardsPanel() {
        for (int i = 0; i < myGarden.size(); i++) {
            int num = (i % 8);

            ImageIcon icon = HomeView.resizeImage("src/main/ui/images/leaf/img_"
                    + num + ".png", 50, 60);
            Plant p = myGarden.getGarden().get(i);
            JButton profileCard = new JButton("<html>" + "<b>" + "Name: " + "</b>" + p.getName() + "<br>"
                    + "<b>" + "Type: " + "</b>" + p.getType() + "</html>", icon);

            profileCard.setForeground(BROWN);

            // sets the icon above texts
            profileCard.setHorizontalTextPosition(SwingConstants.CENTER);
            profileCard.setVerticalTextPosition(SwingConstants.BOTTOM);

            profileCard.setActionCommand(p.getName());
            profileCard.addActionListener(this);
            cardsPanel.add(profileCard);

            updateCol();
        }
    }

    // EFFECTS: if colCount is equal to COL_NUM, add 100 to preferred height,
    //          otherwise add 1 to colCount
    // MODIFIES: this
    public void updateCol() {
        if (colCount == COL_NUM) {
            preferredHeight += 180;
            colCount = 0;
        } else {
            colCount++;
        }
    }

    // ------------- MOUSE-EVENT HANDLING -------------
    // EFFECTS: handles mouse events within the frame
    // MODIFIES: this
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();

        switch (e.getActionCommand()) {
            case ADD_PLANT_ACTION:
                new AddPlantView(myGarden);
                break;

            case RETURN_ACTION:
                try {
                    new HomeView(myGarden);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;

            default:
                Plant p = myGarden.searchPlantByName(e.getActionCommand());
                PlantProfileView plantProfileView = new PlantProfileView(myGarden, p);
                break;
        }
    }
}
