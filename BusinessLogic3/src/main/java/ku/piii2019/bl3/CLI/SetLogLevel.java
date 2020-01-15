/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

/**
 *
 * @author regor
 */
public class SetLogLevel implements CLICommandProcessor {

    private static SetLogLevel instance = null;

    private SetLogLevel() {
    }

    public static void main(String[] args) {
        SetLogLevel.getInstance().processArgs(args);
    }

    public static CLICommandProcessor getInstance() {
        if (instance == null) {
            instance = new SetLogLevel();
        }
        return instance;
    }

    @Override

    public void processArgs(String... args) {

    }

    ;
        @Override

    public void processArgsBody(CommandLine cmd, Options opts) {

    }

    ;
    @Override
    public Options getDefaultOptions() {
        Options defaultCopyOptions = new Options();

        Option o = null;

        o = Option.builder("h").hasArg(false).longOpt("help").desc("This screen.").build();
        defaultCopyOptions.addOption(o);
//            o = Option.builder("c").hasArg(false).longOpt("copy").desc("command to copy:)").required().build();
//            defaultOptions.addOption(o);

        o = Option.builder("s").hasArg(true).argName("Path").longOpt("src").desc("Source folder path.").required(false).build();
        defaultCopyOptions.addOption(o);

        o = Option.builder("d").hasArg(true).argName("Path").longOpt("dsc").desc("Destination folder path.").required(false).build();
        defaultCopyOptions.addOption(o);

        OptionGroup og = new OptionGroup();
        o = Option.builder("SEVERE").hasArg(false).desc("Set the lowest logging threshold to this level.").required(false).build();
        og.addOption(o);

        o = Option.builder("FEX").hasArg(false).longOpt("exclude-FNAME").desc("Excluding duplicates based on Filename.").required(false).build();

        og.addOption(o);

        o = Option.builder("NOEX").hasArg(false).longOpt("no-exclusion").desc("No exclusion, duplicates will be copied.").required(false).build();

        og.addOption(o);
        defaultCopyOptions.addOptionGroup(og);

//             o = Option.builder("m").hasArg(false).longOpt("move").desc("Using this flag, it will move instead of copying it.").required(false).build();
//            defaultOptions.addOption(o);
        return defaultCopyOptions;
    }
}
