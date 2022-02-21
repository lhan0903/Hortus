package ui;

import model.Garden;
import model.Plant;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

// represents the to-do page of the Hortus app
public class TodoView implements ActionListener {
    JFrame frame;
    JPanel panel;
    JPanel cardsPanel;
    JPanel buttonsPanel;
    private Garden myGarden;
    ArrayList<JPanel> profileCardContainers = new ArrayList<>();

    private static final String RETURN_ACTION = "RETURN_ACTION";
    private static final int COL_NUM = 2;
    int colCount = 0;
    int preferredHeight = 150;

    private static final Color GREEN2 = new Color(221, 227, 151);
    private static final Color WHITE = new Color(0xFFFFFF);
    private static final Color BROWN = new Color(0x574537);


    // ---------------- CONSTRUCTOR ------------------
    public TodoView(Garden garden) {
        myGarden = garden;
        frame = new JFrame();
        panel = new JPanel();
        panel.setBackground(GREEN2);

        buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.setBackground(GREEN2);

        cardsPanel = new JPanel(new GridLayout(0, COL_NUM));
        cardsPanel.setBackground(GREEN2);
        fillCardsPanel();

        setupButton();
        panel.add(buttonsPanel);
        panel.add(cardsPanel);

        panel.setPreferredSize(new Dimension(460, preferredHeight + 200));

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        frame.add(scrollPane);
        frame.setSize(480, 500);

        frame.setTitle("Garden");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        HomeView.centerFrameLocation(frame);
    }

    // ---------------- BUTTON ------------------
    // EFFECTS: sets up the control buttons
    private void setupButton() {
        JButton returnButton = new JButton("Back");
        buttonsPanel.add(returnButton);
        returnButton.setActionCommand(RETURN_ACTION);
        returnButton.addActionListener(this);
    }


    // ---------------- CARD PANEL ------------------
    // EFFECTS: creates the plant to-do cards
    // MODIFIES: this
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void fillCardsPanel() {
        for (Plant p : myGarden.getGarden()) {
            JPanel profileCardContainer = new JPanel(new GridLayout(2, 0));
            profileCardContainer.setBackground(WHITE);
            String imageDisplayed = "";
            String messageDisplayed = "";

            JCheckBox checkWateredtoday;
            Boolean displayCheckbox = false;

            int days = p.getNumDaysUntilWater();
            if (days > 0) {
                imageDisplayed = "happy_face";
                displayCheckbox = false;
                messageDisplayed = "Water in " + p.getNumDaysUntilWater() + " day(s)!";
            } else if (days == 0) {
                imageDisplayed = "water_droplet";
                displayCheckbox = true;
                messageDisplayed = "Water today!";
            } else if (days < 0) {
                imageDisplayed = "angry_face";
                displayCheckbox = true;
                messageDisplayed = "<html>" + "ANGRY PLANT," + "<br>" + "WATER NOW >:(" + "</html>";
            }

            ImageIcon icon = HomeView.resizeImage("src/main/ui/images/"
                    + imageDisplayed + ".png", 50, 50);
            JLabel profileCard = new JLabel("<html>" + "<b>" + "Name: " + "</b>" + p.getName() + "<br>"
                    + "<b>" + "Type: " + "</b>" + p.getType() + "<br>"
                    + "<b>" + messageDisplayed + "</b>" + "</html>");
            profileCard.setIcon(icon);
            profileCard.setForeground(BROWN);
            profileCard.setBorder(new EmptyBorder(2, 2, 0, 2));

            profileCardContainer.add(profileCard);

            if (displayCheckbox) {
                checkWateredtoday = new JCheckBox("Watered today");
                profileCardContainer.add(checkWateredtoday);

                checkWateredtoday.addItemListener(new ItemListener() {

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        p.water();
                        frame.dispose();
                        new TodoView(myGarden);
                    }
                });
            }

            Border loweredEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
            profileCardContainer.setBorder(loweredEtched);
            cardsPanel.add(profileCardContainer);
            profileCardContainers.add(profileCardContainer);

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
        switch (e.getActionCommand()) {
            case RETURN_ACTION:
                frame.dispose();
                try {
                    new HomeView(myGarden);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }
}
