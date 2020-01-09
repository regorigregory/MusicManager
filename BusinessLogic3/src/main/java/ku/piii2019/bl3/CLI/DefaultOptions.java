/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import org.apache.commons.cli.Options;

/**
 *
 * @author Gergo Endresz <k1721863@kingston.ac.uk>
 */
public class DefaultOptions {

    private static Options defaultOptions = null;
    private static Options defaultCopyOptions = null;
    private static String newLine = "\r\n";


    public static Options getDefaultCopyOptions() {
        if (defaultCopyOptions == null) {
            defaultCopyOptions = new Options();
            defaultCopyOptions.addOption("h", "help", true, "Usage: CLI -option <args>" + newLine);
            defaultCopyOptions.addOption("src", "source", true, "-src <path>                          source folder" + newLine);
            defaultCopyOptions.addOption("dst", "destination", true, "-dst <path>                          destination folder" + newLine);
            defaultCopyOptions.addOption("copy", "source", true, "-copy                                copy contents of src to dst" + newLine);
            defaultCopyOptions.addOption("ex", "source", true, "-ex <ID3 | fname | none >            duplicate exclusion mode, can be based on ID3, filename or none" + newLine);
            defaultCopyOptions.addOption("src", "source", true, "-src <path>                          source folder" + newLine);

        }
        return defaultCopyOptions;
    }

}
