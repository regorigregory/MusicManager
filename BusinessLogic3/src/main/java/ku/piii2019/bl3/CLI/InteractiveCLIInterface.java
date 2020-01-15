/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import ku.piii2019.bl3.CustomLogging;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author regor
 */
public class InteractiveCLIInterface {

    private static int optionID = 0;
    static List<MenuOption> availableMenuOptions;

    public static void interrogateUser() {
        Scanner sc = new Scanner(System.in);

        setAvailableOptions();

        MenuOption selectedOption = new MenuOption(MenuOption.Option.START, 0, "Starting interactive mode");

        while (selectedOption.getOp() != MenuOption.Option.EXIT) {
            System.out.println("What would you like to do today? Please select:");
            //What if the user enters something other than int?
            printAvailableOptions();
            String userSelectedOption = sc.nextLine();
            MenuOption selected = null;
            try {
                int selectedMenuID = Integer.parseInt(userSelectedOption);
                selected = getSelectedOption(selectedMenuID);
                //processOption(selected);
            } catch (Exception ex) {
                System.out.println("\r\nError. The entered option has to be a single integer value.\r\n");
            }
            if (selected != null) {
                while (selected.getOp() != MenuOption.Option.BACK) {

                    System.out.println("Your choice is:");
                    System.out.println(selected.getOptionText());
                    System.out.println("For help use \"-h\" or \"--help\".");

                    CLICommandProcessor clip = selected.getOp().getProcessor();
                    String[] args = sc.nextLine().split(" ");
                    try {
                        if (MenuOption.Option.BACK == getSelectedOption(Integer.parseInt(args[0])).getOp()) {
                            break;
                        }
                    } catch (Exception ex) {
                      //nothing to do here. I was just curious if you wanted to return to the main menu.:)))
                    }

                    //Arrays.asList(args).stream().forEach(System.out::println);

                    if (clip != null && args.length > 0) {
                        clip.processArgs(args);
                    }
                }

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
        MenuOption back = new MenuOption(MenuOption.Option.BACK, optionID++, "Back to the main menu. It only works if used from a submenu.");
        MenuOption copy = new MenuOption(MenuOption.Option.COPY, optionID++, "Copy files from one folder to another, optionally, without duplicates.");
        MenuOption refile = new MenuOption(MenuOption.Option.REFILE, optionID++, "Refile by artist-album a folder to a target folder.");
        MenuOption playlist = new MenuOption(MenuOption.Option.CREATE_PLAYLIST, optionID++, "Create playlists.");
        MenuOption exit = new MenuOption(MenuOption.Option.EXIT, optionID++, "Exit this program.");
         availableMenuOptions.add(back);
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
        
        System.out.println("\r\nThe selected option is invalid. Please use a single integer value when selecting it.\r\n");
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

        } else {
           System.out.println("The selected option is invalid. Retard.");
        }
    }
}
