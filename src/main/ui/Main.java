package ui;


import model.Garden;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

// Project image source: https://www.irasutoya.com/

public class Main {
    private static Garden myGarden;

    private static final String JSON_STORE = "./data/garden.json";

    public static void main(String[] args) throws IOException {
        //PHASE 2 CODE
//        try {
//            new HortusApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }

//        //PHASE 3 CODE

        // ----------- JOptionPane -------------------
        //pops up before main page, asks user if they want to load previous data
        String message = "Welcome to Hortus, would you like to load previous data?\n";

        ImageIcon img1 = HomeView.resizeImage("src/main/ui/images/allergy_cat.png", 100, 100);
        ImageIcon img2 = HomeView.resizeImage("src/main/ui/images/allergy_dog.png", 100, 100);

        int choice = JOptionPane.showConfirmDialog(null,
                message, "Load Data?", JOptionPane.YES_NO_OPTION, 0, img1);

        if (choice == JOptionPane.YES_OPTION) {
            JsonReader reader = new JsonReader(JSON_STORE);

            myGarden = reader.read();
            JOptionPane.showMessageDialog(null,
                    "Your garden has been loaded :)", null, 0, img2);
        } else {
            myGarden = new Garden(Calendar.DAY_OF_YEAR);
        }

        // ----------------------------------------

        try {
            new HomeView(myGarden);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
