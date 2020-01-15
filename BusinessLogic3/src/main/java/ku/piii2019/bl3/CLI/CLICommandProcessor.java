/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 *
 * @author regor
 */
public interface CLICommandProcessor {
    public  void processArgs(String... args);
    public  void processArgsBody(CommandLine cmd, Options opts);
}
