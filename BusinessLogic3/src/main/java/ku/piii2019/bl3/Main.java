/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;




/**
 *
 * @author regor
 */
public class Main {

    public static void main(String... args) {
        //-s c://test -d c://test2 -ID3EX
        //yes, redirection.
        CustomLogging.getInstance();
        ku.piii2019.bl3.CLI.Main.main(args);
        
        
    }

}
