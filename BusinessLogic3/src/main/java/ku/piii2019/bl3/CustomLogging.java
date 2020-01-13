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
public interface CustomLogging {

    public static void logIt(Exception ex) {
        ex.printStackTrace();
    }

    public static void logIt(String msg) {
        System.out.println(msg);
    }
}
