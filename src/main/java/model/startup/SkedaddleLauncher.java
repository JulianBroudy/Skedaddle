package model.startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SkedaddleLauncher {

  private static Logger logger = LogManager.getLogger(SkedaddleLauncher.class);

  public static void main(String[] args) {
    // LogManager.getRootLogger().trace(
    //     "Configuration File Defined To Be :: " + System.getProperty("log4j.configurationFile"));

    logger.trace("SkedaddleLauncher started.");
    logger.debug("Calling Skedaddkle.main(args).");
    Skedaddle.main(args);
  }
}