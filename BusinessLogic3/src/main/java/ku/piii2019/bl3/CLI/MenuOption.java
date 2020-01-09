/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

/**
 *
 * @author regor
 */
public class MenuOption {
    enum Option{
        COPY, EXIT
    }
    private int optionID;
    private String optionText;
    private Option op;
    public MenuOption(Option op, int id, String text){
        this.op =op;
        this.optionID =id;
        this.optionText = text;
        
    }
    
}
