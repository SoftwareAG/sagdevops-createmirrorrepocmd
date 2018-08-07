/*
* Copyright � 2013 - 2018 Software AG, Darmstadt, Germany and/or its licensors
*
* SPDX-License-Identifier: Apache-2.0
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.                                                            
*
*/

package com.saguk.cce.tools;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CmdLineParser {
  /**
   * Simple utility class to process the arguments passed from the command line.
   * 
   * @param args Command line arguments. 
   * @return null for error
   * @throws IllegalArgumentException Invalid command line arguments had been processed.
   */
  public CmdLineArgs parse(String cmdName, String[] args) 
      throws IllegalArgumentException {

    final CmdLineArgs cmdLineArgs = new CmdLineArgs();

    final Option yamlFileOpt = Option.builder("file")
                                     .required()
                                     .hasArg()
                                     .valueSeparator()
                                     .desc("Required. The YAML template filename")
                                     .build();

    final Option platformsOpt = Option.builder("platforms")
                                      .required(false)
                                      .hasArg()
                                      .desc("Optional. A list of the IDs of the" 
                                      + "operating systems. For example: LNXAMD64,W64")
                                      .build();

    final Option mirrorNameOpt = Option.builder("mirrorRepo")
                                       .required(false)
                                       .hasArg()
                                       .desc("Optional. The name of the mirror product"
                                       + " repository to add.")
                                       .build();

    Option sourceReposOpt = Option.builder("sourceRepo")
                                  .required(false)
                                  .hasArg()
                                  .desc("Optional. A list of the source repositories "
                                      + "to include in the new mirror repository.")
                                  .build();

    Options options = new Options();
    options.addOption(yamlFileOpt);
    options.addOption(platformsOpt);
    options.addOption(mirrorNameOpt);
    options.addOption(sourceReposOpt);

    // create the parser
    CommandLineParser parser = new DefaultParser();
    try {
      // parse the command line arguments
      CommandLine line = parser.parse(options, args);
      
      if (line.hasOption("file")) {
        cmdLineArgs.setYamlFile(line.getOptionValue("file"));
      }
      if (line.hasOption("platforms")) {
        cmdLineArgs.setPlatforms(line.getOptionValue("platforms"));
      }
      if (line.hasOption("sourceRepo")) {
        cmdLineArgs.setSourceRepo(line.getOptionValue("sourceRepo"));
      }
      if (line.hasOption("mirrorRepo")) {
        cmdLineArgs.setMirrorRepo(line.getOptionValue("mirrorRepo"));
      }
    } catch (MissingOptionException e) {
      new HelpFormatter().printHelp(cmdName, options, true);
      System.err.println();
      System.err.println("Missing Option Exception: " + e);
      throw new IllegalArgumentException();
    } catch (MissingArgumentException e) {
      new HelpFormatter().printHelp(cmdName, options, true);
      System.err.println();
      System.err.println("Illegal Argument Exception: " + e);
      throw new IllegalArgumentException();
    } catch (ParseException e) {
      new HelpFormatter().printHelp(cmdName, options, true);
      System.err.println();
      System.err.println("Parse Exception: " + e);
      throw new IllegalArgumentException();
    }
    return cmdLineArgs;
  }
}


