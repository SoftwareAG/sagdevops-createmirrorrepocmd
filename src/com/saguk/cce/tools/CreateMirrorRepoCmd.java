/*
* Copyright © 2013 - 2018 Software AG, Darmstadt, Germany and/or its licensors
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 * The following utility extracts all of the products from a Command Central template and constructs
 * a repository mirror add command with the required products.
 * 
 * @version 0.1
 */
public class CreateMirrorRepoCmd {
  
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    try {
      // Discover the classname
      String className = CreateMirrorRepoCmd.class.getName();
      className = className.substring(className.lastIndexOf('.') + 1);
      
      // Parse the command Line
      CmdLineParser cmdLine = new CmdLineParser();
      CmdLineArgs cmdArgs = cmdLine.parse(className,args);
      
      // Create a list to hold the master list of products
      List<String> productList = new ArrayList<String>(); 
      InputStream yamlFile = new FileInputStream(new File(cmdArgs.getYamlFile()));  

      // Parse the YAML document into a String Array 
      Yaml yaml = new Yaml();

      Map<String, ArrayList<String>> ccTemplateYaml = 
          (Map<String, ArrayList<String>>) yaml.load(yamlFile);

      CompositeTemplateParser ccTemplate = new CompositeTemplateParser(ccTemplateYaml);
      productList = ccTemplate.getProducts();
      
      // Create the artifacts output line
      StringBuffer artifacts = new StringBuffer();
      artifacts.append("sagcc add repository products mirror name=");
      artifacts.append(cmdArgs.getMirrorRepo());
      artifacts.append(" sourceRepos=");
      artifacts.append(cmdArgs.getSourceRepo());
      artifacts.append(" artifacts=");
      int count = 0;
      for (String product : productList) {
        artifacts.append(product);
        count++;
        if (count < productList.size()) {
          artifacts.append(",");
        }
      }
      artifacts.append(" platforms=");
      artifacts.append(cmdArgs.getPlatforms());
      System.out.println(artifacts);
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("Error parsing command line parameters");
    }
  }
 
}
