/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.util.HashSet;
import java.util.LinkedList;
import ku.piii2019.bl3.CustomLogging;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author regor
 */
public class InteractiveInterface {

    private static int optionID = 0;
    static List<MenuOption> availableMenuOptions;

    public static void interrogateUser() {
        Scanner sc = new Scanner(System.in);

        setAvailableOptions();

        MenuOption selectedOption = new MenuOption(MenuOption.Option.START, 0, "Starting interactive mode");

        while (selectedOption.getOp() != MenuOption.Option.EXIT) {
            System.out.println("Please select one of the available options:");
            //What if the user enters something other than int?
            printAvailableOptions();
            int userSelectedOption = sc.nextInt();
            MenuOption selected = getSelectedOption(userSelectedOption);
            processOption(selected);
            while(selected.getOp() != MenuOption.Option.BACK){
                System.out.println("Your choice is:");
                System.out.println(selected.getOptionText());
                System.out.println("For help use -h");
                String[] args = sc.nextLine().split(" ");
            }
        }

    }



    public static void printAvailableOptions() {
        for (MenuOption mi : availableMenuOptions) {
            StringBuilder output = new StringBuilder();
            output.append(mi.getOptionID());
            output.append(")");
            output.append(mi.getOptionText());
            System.out.println(output);
        }
    }

    public static void setAvailableOptions() {
        availableMenuOptions = new LinkedList<>();
        MenuOption copy = new MenuOption(MenuOption.Option.COPY, ++optionID, "Copy files from one folder to another, optionally, without duplicates.");
        MenuOption refile = new MenuOption(MenuOption.Option.REFILE, ++optionID, "Refile by artist-album a folder to a target folder.");
        MenuOption playlist = new MenuOption(MenuOption.Option.CREATE_PLAYLIST, ++optionID, "Refile by artist-album a folder to a target folder.");
        
        MenuOption exit = new MenuOption(MenuOption.Option.EXIT, ++optionID, "Exit this program.");
        availableMenuOptions.add(copy);
        availableMenuOptions.add(refile);
        availableMenuOptions.add(playlist);
        availableMenuOptions.add(exit);
        optionID = 0;
    }

    ;
    public static MenuOption getSelectedOption(int selectedOption) {
        for (MenuOption mo : availableMenuOptions) {
            if (selectedOption == mo.getOptionID()) {
                return mo;
            }
        }
        return null;
    }

    public static void processOption(MenuOption mo) {
        if (mo != null) {
            MenuOption.Option selectedOp = mo.getOp();

            //As Paul is on board again and he is allergic to switch statements, as I was told (seriously, how can you teach that "switch statements are evil and condemn someone who uses them?". Ridicoulous. 
            //Had I known earlier, I would have used only switch statements across this assignment.
            switch (selectedOp) {
                case COPY:
                    System.out.println("You wanna copy something, woudln't ya?");
                    break;
                case REFILE:
                    System.out.println("You wanna refile something, woudln't ya?");
                    break;
                case CREATE_PLAYLIST:
                    System.out.println("You wanna create a playlist thingy, woudln't ya?");
                    break;
                case EXIT:
                    System.out.println("You wanna leave. Finally.");
                    break;
                case BACK:
                    System.out.println("You wanna go back to the main menu, bastard.");

                    break;
                default:
                    System.out.println("The selected option is invalid. Retard.");
                    return;
            }

           

    }
    }
}


