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
