/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author regor
 */
public enum CopyErrors {
    SRC_INVALID("Not supported."), DSC_INVALID("Not supported."), EXC_INVALID("Not supported."); 
    
    
    private final String message;
   
    private CopyErrors(String msg){
        this.message = msg;
    }
    public String toString(){
        return this.message;
    }
}
