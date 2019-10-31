package model.startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class SkedaddleLauncher {

  private static final Logger logger = LogManager.getLogger(SkedaddleLauncher.class);

  public static void main(String[] args) {
    // LogManager.getRootLogger().trace(
    //     "Configuration File Defined To Be :: " + System.getProperty("log4j.configurationFile"));

    logger.trace("SkedaddleLauncher started.");
    logger.debug("Calling Skedaddle.main(args).");
    Skedaddle.main(args);
  }
}