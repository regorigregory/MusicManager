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
        COPY, EXIT, REMOVE_DUPLICATES, REFILE, CREATE_PLAYLIST
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
