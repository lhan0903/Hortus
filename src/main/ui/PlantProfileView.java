package ui;

import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

// represents a page containing information of an individual plant
public class PlantProfileView implements ActionListener {
    JFrame frame;
    JPanel panel;
    Plant plant;
    Garden myGarden;

    private static final String DELETE_ACTION = "DELETE_ACTION";
    private static final String EDIT_ACTION = "EDIT_ACTION";
    private static final String RETURN_ACTION = "RETURN_ACTION";

    // ---------------- CONSTRUCTOR ------------------
    public PlantProfileView(Garden myGarden, Plant plant) {
        frame = new JFrame();
        panel = new JPanel();
        this.myGarden = myGarden;
        this.plant = plant;

        panel.add(createGifLabel("src/main/ui/images/walking_leaf.gif", 240, 250));

        JLabel plantInfo = new JLabel("<html>" + "Name: " + plant.getName() + "<br>"
                + "Type: " + plant.getType() + "<br>"
                + "Number of days last watered: " + plant.getNumDaysLastWatered() + "<br>"
                + "Number of days until next watering: " + plant.getNumDaysUntilWater() + "<br>"
                + "Watering frequency: " + plant.getWateringFrequency() + "<br>"
                + "Number of times watered: " + plant.getNumTimeWatered() + "<br>");
        plantInfo.setFont(new Font("", Font.PLAIN, 14));
        plantInfo.setForeground(new Color(0x574537));

        panel.add(plantInfo);

        // creates the edit, delete, and return buttons
        createButtons();

        stylePanel();

        frame.add(panel);
        frame.pack();
        frame.setSize(350, 500);
        HomeView.centerFrameLocation(frame);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void stylePanel() {
        panel.setBackground(new Color(206, 212, 140));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
    }

    // ---------------- LABEL ------------------

    // EFFECTS: creates a JLabel that is a gif
    // MODIFIES: this
    //Code reference: https://stackoverflow.com/questions/9392227/resizing-animated
    // -gif-while-keeping-its-animation-using-java
    //Image source: https://gifer.com/en/Io7
    public static JLabel createGifLabel(String pathname, int width, int height) {
        File f = new File(pathname);
        URL img = null;
        try {
            img = f.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);

        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));

        return new JLabel(icon);
    }

    // ---------------- BUTTONS ------------------
    // EFFECTS: instantiates the edit, delete, and return buttons displayed on the page
    // MODIFIES: this
    public void createButtons() {
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton returnButton = new JButton("Back");

        setupButtons(editButton, EDIT_ACTION);
        setupButtons(deleteButton, DELETE_ACTION);
        setupButtons(returnButton, RETURN_ACTION);
    }

    // EFFECTS: sets action command, adds action listener to the button, and adds the button to panel
    // MODIFIES: this
    public void setupButtons(JButton button, String actionCommand) {
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        panel.add(button);
    }


    // ------------- MOUSE-EVENT HANDLING -------------
    // EFFECTS: handles mouse events within the frame
    // MODIFIES: this
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();

        switch (e.getActionCommand()) {
            case DELETE_ACTION:
                myGarden.removePlantByName(plant.getName());
                new GardenView(myGarden);
                break;

            case EDIT_ACTION:
                new EditPlantView(myGarden, myGarden.searchPlantByName(plant.getName()));
                break;

            case RETURN_ACTION:
                new GardenView(myGarden);
                break;
        }
    }
}
