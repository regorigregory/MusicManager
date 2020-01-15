/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import ku.piii2019.bl3.CustomLogging;

/**
 *
 * @author regor
 */
public class Main {
    public static void main(String[] args){
        
        CustomLogging.getInstance();
        CustomLogging.defaultSetup();
        InteractiveCLIInterface.interrogateUser();
        
        
    }
}
