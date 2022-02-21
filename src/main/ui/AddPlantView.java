package ui;

import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.event.ActionEvent;

// represents a page that allows user to add a new plant
public class AddPlantView extends ManagePlantView {


    // ------------- CONSTRUCTOR -------------
    public AddPlantView(Garden myGarden) {
        super(myGarden, null);
        frame.setTitle("Adding Plant");
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

                    Plant newPlant = new Plant(name, type, numDaysLastWatered, wateringFrequency);
                    myGarden.addPlant(newPlant);

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
