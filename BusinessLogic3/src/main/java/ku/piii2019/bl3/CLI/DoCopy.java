/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import ku.piii2019.bl3.CustomLogging;
import ku.piii2019.bl3.DuplicateFindFromFilename;
import ku.piii2019.bl3.DuplicateFindFromID3;
import ku.piii2019.bl3.DuplicateFinder;
import ku.piii2019.bl3.MediaFileService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
//import org.apache.commons.cli.ParseException;

/**
 *
 * @author regor
 */
public class DoCopy implements CLICommandProcessor {
    
    public static CLICommandProcessor instance = null;
    private DoCopy(){};
    
    
    public static CLICommandProcessor getInstance(){
        if(instance==null){
            instance = new DoCopy();
        }
        return instance;
    }
    @Override
    public void processArgs(String... args) {
        CommandLineParser clp = DefaultParserSingleton.getInstance();
        
        Options opts = DefaultOptions.getDefaultCopyOptions();

        try {

            CommandLine cmd = clp.parse(opts, args);
            processArgsBody(cmd, opts);
        } catch (Exception pex) {
            CustomLogging.logIt(pex);

        }

    }

    @Override
    public void processArgsBody(CommandLine cmd, Options opts) {
       
        if(cmd.hasOption("h")){
            String usage = "-s <source_folder> -d <destination_folder> [ID3EX | FEX | NOEX]";
            CLIHelpFormatter.printHelp(opts, usage);
        } else if (cmd.hasOption("s") && cmd.hasOption("d")){
        String srcFolder = cmd.getOptionValue('s');
        String dstFolder = cmd.getOptionValue('d');
        DuplicateFinder df = null;
        if (cmd.hasOption("ID3EX")) {
            df = new DuplicateFindFromID3();
        } else if (cmd.hasOption("FEX")) {
            df = new DuplicateFindFromFilename();
        }

        MediaFileService fsi =  MediaFileService.getInstance();
        fsi.copyMediaFiles(srcFolder, dstFolder, df);
        } else {
            System.out.println("The provided arguments are not valid. Use \"-h\" to view usage");
        }
        return;

    }

  
}
