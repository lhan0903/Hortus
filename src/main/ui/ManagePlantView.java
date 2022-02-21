package ui;

import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ManagePlantView implements ActionListener {
    JFrame frame;
    JPanel panel;
    Garden myGarden;
    Plant plant;

    JTextField nameField;
    JTextField typeField;
    JTextField numDaysLastWateredField;
    JTextField wateringFrequencyField;

    protected static final String CONFIRM_ACTION = "CONFIRM_ACTION";
    protected static final String CANCEL_ACTION = "CANCEL_ACTION";

    // ---------------- CONSTRUCTOR ------------------
    public ManagePlantView(Garden myGarden, Plant plant) {
        this.myGarden = myGarden;
        this.plant = plant;
        frame = new JFrame();
        panel = new JPanel(new GridLayout(15, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        setupLabelsAndTextFields();
        panel.add(new JSeparator());
        createButtons();

        frame.add(panel);
        frame.setSize(350, 500);
        frame.setResizable(false);
        frame.setVisible(true);
        HomeView.centerFrameLocation(frame);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // ------------- LABELS AND TEXT FIELDS -------------

    // EFFECTS: creates and configures the label and text fields
    // MODIFIES: this
    public void setupLabelsAndTextFields() {
        JLabel nameLabel = new JLabel("Name: ");
        nameField = new JTextField("");
        configureLabelAndTextfields(nameLabel, nameField);

        JLabel typeLabel = new JLabel("Type: ");
        typeField = new JTextField("");
        configureLabelAndTextfields(typeLabel, typeField);

        JLabel numDaysLastWateredLabel = new JLabel("Number of Days Last Watered: ");
        numDaysLastWateredField = new JTextField("");
        configureLabelAndTextfields(numDaysLastWateredLabel, numDaysLastWateredField);

        JLabel wateringFrequencyLabel = new JLabel("<html>" + "How often does it need to be watered?" + "<br>"
                + "(In days)" + "</html>");
        wateringFrequencyField = new JTextField("");
        configureLabelAndTextfields(wateringFrequencyLabel, wateringFrequencyField);
    }

    // EFFECTS: sets the size of text field and adds it to panel, adds label to panel
    // MODIFIES: this
    public void configureLabelAndTextfields(JLabel label, JTextField field) {
        field.setSize(200, 50);
        panel.add(label);
        panel.add(field);
    }

    // ------------- BUTTONS -------------
    // EFFECTS: creates and configures buttons
    // MODIFIES: this
    public void createButtons() {
        JButton confirmButton = new JButton("Confirm");
        configureButton(confirmButton, CONFIRM_ACTION);

        JButton cancelButton = new JButton("Cancel");
        configureButton(cancelButton, CANCEL_ACTION);
    }

    // EFFECTS: sets the action command and listener, adds button to panel
    // MODIFIES: this
    public void configureButton(JButton button, String command) {
        button.setActionCommand(command);
        button.addActionListener(this);
        panel.add(button);
    }


    // ------------- MOUSE-EVENT HANDLING -------------
    // EFFECTS: handles mouse events within the frame
    // MODIFIES: this
    @Override
    public abstract void actionPerformed(ActionEvent e);
}
