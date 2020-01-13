/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import ku.piii2019.bl3.CustomLogging;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author regor
 */
public class InteractiveInterface {
    private int optionID = 0;
    List<MenuOption> availableMenuOptions;
    public void interrogateUser(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Please select on of the available options:");
            //What if the user enters something other than int?
            int selectedOption = sc.nextInt();
        }
    }
    
    public void setAvailableOptions(){
      MenuOption copy = new MenuOption(MenuOption.Option.COPY, optionID++, "Copy files from one folder to another"); 
      MenuOption exit = new MenuOption(MenuOption.Option.EXIT, optionID++, "Exit this program."); 
      availableMenuOptions.add(copy);
      availableMenuOptions.add(exit);
      optionID=0;
    };
    public MenuOption getSelectedOption(int selectedOption){
        for(MenuOption mo : availableMenuOptions){
           if(selectedOption == mo.getOptionID()){
               return mo;
           } 
        }
        return null;
    }
    public void printOptions(){};
    
    public void processOption(MenuOption mo)
    {
        if(mo!=null){
            MenuOption.Option selectedOp = mo.getOp();
            if(selectedOp==MenuOption.Option.COPY){
                //do something
            } else if(selectedOp==MenuOption.Option.EXIT)
            {
                //do something
            }

        } else {
            CustomLogging.logIt("Selected option does not exist");
            
            
        }
    
    };
    
}
