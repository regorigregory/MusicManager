/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author regor
 */
public class InteractiveInterface {
    private int optionID = 0;
    List<MenuOption> menuOptions;
    public void interrogateUser(){
        Scanner sc = new Scanner(System.in);
        while(true){
            int selectedOption = sc.nextInt();
        }
    }
    
    public void setAvailableOptions(){
      MenuOption copy = new MenuOption(MenuOption.Option.COPY, optionID++, "Copy files from one folder to another"); 
      MenuOption exit = new MenuOption(MenuOption.Option.EXIT, optionID++, "Exit this program."); 

    };
    public void printOptions(){};
    public void processOptions(){};
    
}
