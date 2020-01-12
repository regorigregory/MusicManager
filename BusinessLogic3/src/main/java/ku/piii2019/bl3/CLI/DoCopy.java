/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import ku.piii2019.bl3.DuplicateFindFromFilename;
import ku.piii2019.bl3.DuplicateFindFromID3;
import ku.piii2019.bl3.DuplicateFinder;
import ku.piii2019.bl3.FileServiceImpl;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author regor
 */
public class DoCopy {

    public static void processArgs(String... args) {
        CommandLineParser clp = DefaultParserSingleton.getInstance();
        Options opts = DefaultOptions.getDefaultCopyOptions();

        try {

            CommandLine cmd = clp.parse(opts, args);
            processArgsBody(cmd);
        } catch (ParseException pex) {
            CustomLogging.logIt(pex);

        }

    }

    
    public static void processArgsBody(CommandLine cmd){
        String srcFolder = cmd.getOptionValue('s');
        String dstFolder = cmd.getOptionValue('d');
        DuplicateFinder df = null;
        if(cmd.hasOption("ID3EX")){
            df = new DuplicateFindFromID3();
        } else if(cmd.hasOption("FEX")){
            df = new DuplicateFindFromFilename();
        }
        
        FileServiceImpl fsi = new FileServiceImpl();
        fsi.copyMediaFiles(srcFolder, dstFolder, df);
        
    }

    public static void checkForError() {
    }
}
