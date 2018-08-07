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

public class CmdLineArgs {
  private String yamlFile;
  private String platforms;
  private String mirrorRepo;
  private String sourceRepo;

  public String getYamlFile() {
    return yamlFile;
  }

  public void setYamlFile(String yamlFile) {
    this.yamlFile = yamlFile;
  }

  public String getPlatforms() {
    return platforms;
  }

  public void setPlatforms(String platforms) {
    this.platforms = platforms;
  }

  public String getMirrorRepo() {
    return mirrorRepo;
  }

  public void setMirrorRepo(String mirrorRepo) {
    this.mirrorRepo = mirrorRepo;
  }

  public String getSourceRepo() {
    return sourceRepo;
  }

  public void setSourceRepo(String sourceRepo) {
    this.sourceRepo = sourceRepo;
  }
}
