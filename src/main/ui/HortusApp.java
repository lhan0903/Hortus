package ui;

import model.Garden;
import model.Plant;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HortusApp {
    private Garden myGarden;
    private Scanner input;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/garden.json";

    boolean keepGoing = true;

    private boolean displayGardenMenu = false;
    private boolean displayTodoMenu = false;

    private JFrame frame;


    // EFFECTS: runs the Hortus application
    public HortusApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runHortus();
    }

    // EFFECTS: processes user inputs
    // MODIFIES: this
    private void runHortus() {
        String command;
        System.out.println("Welcome to Hortus - Let's keep your plants hydrated!");
        init();

        while (keepGoing) {
            if (displayGardenMenu) {
                displayGardenMenu();
            } else if (displayTodoMenu) {
                displayTodoListMenu();
            } else {
                displayMainMenu();
            }

            trackTime();

            command = input.next();
            command = command.toLowerCase();

            processCommand(command);
        }
        System.out.println("\nGoodbye!");
    }

    //  ----------------------------------------------------------------------------

    // EFFECTS: initializes the program
    private void init() {
        myGarden = new Garden(Calendar.DAY_OF_YEAR);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // ---------------------------------------------------------------------

    // EFFECTS: tracks time so that if one day has passed, increment one day for garden
    // MODIFIES: Garden
    private void trackTime() {
        Calendar currentDay = Calendar.getInstance();
        while (currentDay.get(Calendar.DAY_OF_YEAR) != myGarden.getInitialDay()) {
            myGarden.incrementOneDayForGarden();
            myGarden.incrementInitialDayByOne();
        }
    }

    // EFFECTS: processes user commands
    // MODIFIES: this
    private void processCommand(String command) {

        if (command.equals("s")) {
            saveGarden();
        } else if (command.equals("l")) {
            loadGarden();
        } else if (displayGardenMenu) {
            processGardenCommand(command);
        } else if (displayTodoMenu) {
            processTodoCommand(command);
        } else {
            processMainCommand(command);
        }
    }
    //  -------------------------- COMMAND PROCESSING METHODS ---------------------------------

    // EFFECTS: processes user commands for main page
    // MODIFIES: this
    private void processMainCommand(String command) {
        switch (command) {
            case "g":
                displayGardenMenu = true;
                displayTodoMenu = false;
                break;
            case "t":
                displayGardenMenu = false;
                displayTodoMenu = true;
                break;
            case "q":
                keepGoing = false;
                break;
            default:
                System.out.println("Selection not valid... Try again");
        }
    }

    // EFFECTS: processes user commands for plant page
    // MODIFIES: this
    private void processGardenCommand(String command) {
        switch (command) {
            case "r":
                displayGardenMenu = false;
                break;
            case "a":
                doAddPlant();
                break;
            case "d":
                doDeletePlant();
                break;
            case "e":
                doEditPlant();
                break;
            case "v":
                doViewPlant();
                break;
            default:
                System.out.println("Selection not valid... Try again");
        }
    }


    // EFFECTS: processes user commands for todo_page
    // MODIFIES: this
    private void processTodoCommand(String command) {
        switch (command) {
            case "r":
                displayTodoMenu = false;
                break;
            case "v":
                doViewAllTodo();
                break;
            case "a":
                doViewAllTodoToday();
                break;
            case "w":
                doCheckOffTodo();
                break;
            default:
                System.out.println("Selection not valid... Try again");
        }
    }

    //  -------------------------- PLANT METHODS ---------------------------------

    // EFFECTS: adds a plant to the garden with the given user inputs as its fields
    // MODIFIES: Garden
    private void doAddPlant() {
        System.out.print("Enter your plant's name: \n > ");
        String name = input.next();

        System.out.print("Enter your plant's type: \n > ");
        String type = input.next();

        try {
            System.out.print("How many days ago did you last water " + name + " the "
                    + type + "? (Enter integer) \n > ");

            int numDaysLastWatered = input.nextInt();

            System.out.print("How often (in days) would you like to water it on a regular basis?\n > ");
            int waterFrequency = input.nextInt();

            System.out.println("Adding " + name + " to your garden...");

            Plant newPlant = new Plant(name, type, numDaysLastWatered, waterFrequency);
            myGarden.addPlant(newPlant);
            System.out.println("Add successful: You garden now has " + myGarden.size() + " plant(s)!");
        } catch (InputMismatchException e) {
            System.out.println("You need to enter an integer! Please try again\n");
            input.nextLine(); // consumes the false input above so it's not used for the next input
            doAddPlant();
        }
    }

    // EFFECTS: removes a plant from the garden with name the user inputs
    // MODIFIES: Garden
    private void doDeletePlant() {
        printPlantNameTypeList();
        if (myGarden.size() != 0) {
            System.out.print("\nEnter the name of the plant you would like to remove from your garden:\n > ");
            String nameOfPlantToRemove = input.next();

            try {
                myGarden.removePlantByName(nameOfPlantToRemove);
                System.out.println("Deletion successful: You garden now has " + myGarden.size() + " plant(s)! ");
            } catch (NullPointerException e) {
                System.out.println("No plant by that name was found, please try again.");
                doDeletePlant();
            }
        }
    }

    // EFFECTS: asks user for plant name they would like to edit, finds the plant and prompts user to
    //          select 1 of the 3 editable fields they would like to change;
    //          print alternative message if garden is empty
    // MODIFIES: Plant
    private void doEditPlant() {
        printPlantNameTypeList();
        if (myGarden.size() != 0) {
            System.out.print("Enter the name of the plant whose information you would like to edit: \n> ");
            String name = input.next();

            try {
                Plant p = myGarden.searchPlantByName(name);
                printEditableFieldsOfPlant(p);

                System.out.print("Enter the NUMBER in front of field that you would like to edit: \n> ");
                int field = input.nextInt();

                System.out.print("What would you like to change it to? \n> ");
                editPlantField(field, p);
            } catch (NullPointerException e) {
                System.out.println("No plant with that name was found, please try again\n");
                doEditPlant();
            } catch (InputMismatchException e) {
                System.out.println("You need to enter an integer! Please try again\n");
                input.nextLine();
                doEditPlant();
            }
        }
    }

    // EFFECTS: replaces the user-specified plant field with new field
    // MODIFIES: Plant
    private void editPlantField(int field, Plant p) {
        if (field == 1) {
            String newName = input.next();
            p.setName(newName);
            System.out.println("The plant's name has been set to: " + p.getName());

        } else if (field == 2) {
            String newTypeName = input.next();
            p.setType(newTypeName);
            System.out.println("The plant's type has been set to: " + p.getType());

        } else if (field == 3) {
            int newFrequency = input.nextInt();
            p.setWateringFrequency(newFrequency);
            System.out.println("The watering frequency has been set to: " + p.getWateringFrequency());
        } else {
            System.out.println("Invalid field, please try again.\n");
        }
    }

    // EFFECTS: prints the information for the user-specified plant in the garden,
    //          prints alternative message if garden is empty
    private void doViewPlant() {
        printPlantNameTypeList();
        if (myGarden.size() != 0) {
            System.out.print("\nEnter the name of the plant you would like to view:\n > ");
            String nameOfPlantToView = input.next();

            try {
                viewPlantByName(nameOfPlantToView);
            } catch (NullPointerException e) {
                System.out.println("No plant with given name was found, try again\n");
                doViewPlant();
            }
        }
    }

    // EFFECTS: prints out the name, type, and watering frequency of the plant
    public void printEditableFieldsOfPlant(Plant p) {
        System.out.println("\n1) Name: " + p.getName()
                + "\n2) Type: " + p.getType()
                + "\n3) Watering frequency: Every " + p.getWateringFrequency() + " days");
    }

    // EFFECTS: prints out a "card" with the plant's watering status
    public void printTodoCard(Plant p) {
        if (p.getNumDaysUntilWater() < 0) {
            System.out.println("\nName: " + p.getName()
                    + "\nYou should have watered " + (-1 * p.getNumDaysUntilWater()) + " day(s) ago"
                    + "\nTotal times Watered: " + p.getNumTimeWatered()
                    + "\nYOUR PLANT IS DEHYDRATED AND VERY ANGRY. GO WATER IT NOW! >:(\n");

        } else {
            System.out.println("\nName: " + p.getName()
                    + "\nWater in: " + p.getNumDaysUntilWater() + " day(s)"
                    + "\nTotal times Watered: " + p.getNumTimeWatered());

            if (p.getNumDaysUntilWater() <= 0) {
                System.out.println("\n[!] Dehydrated: Water Today!\n");
            } else {
                System.out.println("\n[*] Hydrated: Do not need to be watered\n");
            }
        }
    }

    // EFFECTS: prints all information of the plant with given name
    public void viewPlantByName(String name) {
        Plant p = myGarden.searchPlantByName(name);

        System.out.println("Retrieving plant profile for " + p.getName() + " the " + p.getType()
                + "\nName: " + p.getName()
                + "\nType: " + p.getType()
                + "\nNumber Of Days Last Watered: " + p.getNumDaysLastWatered()
                + "\nNumber of Days Until Next Watering: " + p.getNumDaysUntilWater()
                + "\nWatering frequency: Every " + p.getWateringFrequency() + " days"
                + "\nTotal number of times watered since app usage: " + p.getNumTimeWatered());
    }

    // EFFECTS: prints all plant names and their corresponding types in the garden,
    //          prints alternative message when garden is empty
    public void printPlantNameTypeList() {
        if (myGarden.size() != 0) {
            System.out.println("\nCurrent plants in your garden: ");

            for (Plant next : myGarden.getGarden()) {
                System.out.println("- " + next.getName() + " the " + next.getType());
            }
        } else {
            System.out.println("There are no plants in your garden right now");
        }
    }

    //  -------------------------- TODOLIST METHODS ---------------------------------

    // EFFECTS: prints a todo_card (watering-related info) for every plant in the garden
    private void doViewAllTodo() {
        printTodoList();
    }

    // EFFECTS: prints the todo_cards of every plant,
    //          print alternative message when garden is empty
    public void printTodoList() {
        if (myGarden.getGarden().isEmpty()) {
            System.out.println("The todo list is currently empty. Please go add a plant to your garden.");
        } else {
            for (Plant next : myGarden.getGarden()) {
                System.out.println("-----------------------");
                printTodoCard(next);
            }
        }
    }

    // EFFECTS: marks the watering task for a plant as complete
    // MODIFIES: Plant
    private void doCheckOffTodo() {
        System.out.println("Here is a list of all your todos today");

        if (printTodoListToday()) {
            System.out.println("Which plant did you water today? (enter PLANT NAME)\n> ");

            String name = input.next();
            Plant p = myGarden.searchPlantByName(name);
            p.water();

            System.out.println("Watering todo for " + name + " has been checked-off."
                    + "\nUpdating the todo list for today...");
            printTodoListToday();
        }
    }

    // EFFECTS: prints a todo_card (watering-related info) for each plant in the garden that with
    //          numDaysUntilWater <= 0
    private void doViewAllTodoToday() {
        printTodoListToday();
    }

    // EFFECTS: prints the todo_cards of every plant who needs to be watered
    //          (num of days until watering <= 0)
    //          prints alternative message when no plant needs to be watered
    public boolean printTodoListToday() {
        int numTodo = 0;
        for (Plant next : myGarden.getGarden()) {
            if (next.getNumDaysUntilWater() <= 0) {
                System.out.println("-----------------------");
                printTodoCard(next);
                numTodo++;
            }
        }
        if (numTodo == 0) {
            System.out.println("You have no todos in your list for today!");
            return false;
        }
        return true;
    }
    //  -------------------------- DISPLAY METHODS ---------------------------------

    // EFFECTS: prints menu for todo_page
    private void displayTodoListMenu() {
        System.out.println("\n___________________________________________");
        System.out.println("|         SELECT FROM TODO MENU:          |");
        System.out.println("|\tv -> View All Todos                   |");
        System.out.println("|\ta -> View All Todos For Today         |");
        System.out.println("|\tw -> Water A Plant (Check-Off Todo)   |");
        System.out.println("|\tr -> Return to main menu              |");
        System.out.println("|_________________________________________|");
        System.out.print("> ");
    }

    // EFFECTS: prints menu for main page
    private void displayMainMenu() {
        System.out.println("\n_________________________________");
        System.out.println("| SELECT FROM MAIN MENU:         |");
        System.out.println("|\t  g -> Garden                 |");
        System.out.println("|\t  t -> Todo List             |");
        System.out.println("|\t  s -> Save Garden To File   |");
        System.out.println("|\t  l -> Load Garden From File |");
        System.out.println("|\t  q -> Quit                  |");
        System.out.println("|________________________________|");
        System.out.print("> ");
    }

    // EFFECTS: prints menu for plant page
    private void displayGardenMenu() {
        System.out.println("\n___________________________________________");
        System.out.println("|         SELECT FROM GARDEN MENU:        |");
        System.out.println("|\ta -> Add a new plant to my garden     |");
        System.out.println("|\td -> Delete a plant from my garden    |");
        System.out.println("|\te -> Edit a plant's information       |");
        System.out.println("|\tv -> View one plant                   |");
        System.out.println("|\tr -> Return to main menu              |");
        System.out.println("|_________________________________________|");
        System.out.print("> ");
    }

    //  -------------------------- JSON METHODS ---------------------------------

    // These methods reference code from CPSC210/JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: saves the garden to file
    private void saveGarden() {
        try {
            jsonWriter.open();
            jsonWriter.write(myGarden);
            jsonWriter.close();
            System.out.println("Saved your garden to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads garden from file
    private void loadGarden() {
        try {
            myGarden = jsonReader.read();
            System.out.println("Loaded your garden from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}


