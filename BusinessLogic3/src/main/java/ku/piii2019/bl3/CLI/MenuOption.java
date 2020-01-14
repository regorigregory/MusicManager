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

    enum Option {
        START(null),
        COPY(DoCopy.getInstance()),
        BACK(null),
        EXIT(null),
        REFILE(DoRefile.getInstance()),
        CREATE_PLAYLIST(CreateM3U.getInstance());
        
        
        CLICommandProcessor CLIPro = null;

        Option(CLICommandProcessor cli) {
            this.CLIPro = cli;
        }

        public CLICommandProcessor getProcessor() {
            return this.CLIPro;
        }

    }

    private int optionID;
    private String optionText;
    private Option op;

    public MenuOption(Option op, int id, String text) {
        this.op = op;
        this.optionID = id;
        this.optionText = text;

    }

    public int getOptionID() {
        return optionID;
    }

    public void setOptionID(int optionID) {
        this.optionID = optionID;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Option getOp() {
        return op;
    }

    public void setOp(Option op) {
        this.op = op;
    }

}
