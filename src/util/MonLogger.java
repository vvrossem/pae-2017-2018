package util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonLogger {

  Logger monLog;

  /**
   * Construit le logger de l'application avec ses paramètres.
   */
  public MonLogger() {
    monLog = Logger.getLogger("stagifyLogger");
    // Création logger et handler
    monLog.setLevel(Level.ALL);
    monLog.setUseParentHandlers(false);
    ConsoleHandler ch = new ConsoleHandler();
    ch.setLevel(Level.CONFIG);
    ch.setFormatter(new LogFormatter());
    monLog.addHandler(ch);
    Handler fh;
    try {
      fh = new FileHandler("Logs.txt");
      fh.setFormatter(new LogFormatter());
      fh.setLevel(Level.ALL);
      monLog.addHandler(fh);
    } catch (SecurityException ex) {
      monLog.severe("Impossible d'associer le FileHandler\n\t" + Util.stackTraceToString(ex));
    } catch (IOException ex) {
      monLog.severe("Impossible d'associer le FileHandler\n\t" + Util.stackTraceToString(ex));
    }
    monLog.info("Lancement du serveur");
  }

  public Logger getMonLog() {
    return monLog;
  }

}
