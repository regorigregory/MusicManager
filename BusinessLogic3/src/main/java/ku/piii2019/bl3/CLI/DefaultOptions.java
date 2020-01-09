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
    private static String newLine = "";

    public static Options getDefaultCopyOptions() {
        if (defaultCopyOptions == null) {
            defaultCopyOptions = new Options();
            defaultCopyOptions.addOption("h", "help", true, "Usage: CLI -option <args>" + newLine);
            defaultCopyOptions.addOption("src", "source", true, "source folder" + newLine);
            defaultCopyOptions.addOption("dst", "destination", true, "destination folder" + newLine);
            defaultCopyOptions.addOption("copy", "source", true, "copy contents of src to dst" + newLine);
            defaultCopyOptions.addOption("ex", "source", true, "<ID3 | FNAME | NONE > duplicate exclusion mode, can be based on ID3, filename or none" + newLine);
            defaultCopyOptions.addOption("m", "move", false, "Whether to move the whole folder or copy. Be careful, if used with exclusion, the duplicate files will be deleted from the original folder as well." + newLine);
            defaultCopyOptions.addOption("r", "recursive", false, "Whether to perform the copy recursively. If no, only the root folder will be processed." + newLine);
            defaultCopyOptions.addOption("p", "playlist", false, "A playlist will be generated in the destination root folder." + newLine);
            defaultCopyOptions.addOption("ref", "refile", false, "Rearrange source folder contents and file them first based on artist then album" + newLine);
        
        }
        return defaultCopyOptions;
    }

    public static Options getDefaultRefileOptions() {
        
        throw new UnsupportedOperationException("Has not been implemented.");
    }

    public static Options getDefaultPlaylistCreationOptions() {
        throw new UnsupportedOperationException("Has not been implemented.");
    }

}
