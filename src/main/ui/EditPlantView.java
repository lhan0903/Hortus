package ui;

import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.event.ActionEvent;

// represents a page that allows user to edit a plant's information
public class EditPlantView extends ManagePlantView {

    // ---------------- CONSTRUCTOR ------------------
    public EditPlantView(Garden myGarden, Plant plant) {
        super(myGarden, plant);
        frame.setTitle("Editing Plant");
    }

    // ------------- LABELS AND TEXT FIELDS -------------
    @Override
    // EFFECTS: creates and configures the label and text fields
    // MODIFIES: this
    public void setupLabelsAndTextFields() {
        super.setupLabelsAndTextFields();

        nameField.setText(plant.getName());
        typeField.setText(plant.getType());
        numDaysLastWateredField.setText(String.valueOf(plant.getNumDaysLastWatered()));
        wateringFrequencyField.setText(String.valueOf(plant.getWateringFrequency()));
    }

    // ------------- MOUSE-EVENT HANDLING -------------
    // EFFECTS: handles mouse events within the frame
    // MODIFIES: this
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case CONFIRM_ACTION:
                String name = nameField.getText();
                String type = typeField.getText();
                ImageIcon img = HomeView.resizeImage("src/main/ui/images/hippo.png", 100, 100);

                try {
                    int numDaysLastWatered = Integer.parseInt(numDaysLastWateredField.getText());
                    int wateringFrequency = Integer.parseInt(wateringFrequencyField.getText());

                    myGarden.editPlant(plant, name, type, numDaysLastWatered, wateringFrequency);

                    frame.dispose();
                    new GardenView(myGarden);

                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null,
                            "<html> You cannot have empty or non-integer values <br> "
                                    + "for the last two fields, try again! </html>",
                            null, 0, img);
                }

                break;

            case CANCEL_ACTION:
                frame.dispose();
                new GardenView(myGarden);
                break;
        }
    }
}
