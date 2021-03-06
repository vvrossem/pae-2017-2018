package util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import util.AppContext.DependanceInjection;

public class LogFormatter extends Formatter {

  private Date date = new Date();
  private String format = "{0,date} {0,time}";
  private MessageFormat formatter;
  private Object[] args = new Object[1];

  @DependanceInjection
  private MonLogger monLogger;

  private String lineSeparator = "\n";

  /**
   * Format the given LogRecord.
   * 
   * @param record the log record to be formatted.
   * @return a formatted log record
   * 
   */
  public synchronized String format(LogRecord record) {

    // Minimize memory allocations here.
    date.setTime(record.getMillis());
    args[0] = date;


    // Date and time
    StringBuffer text = new StringBuffer();
    if (formatter == null) {
      formatter = new MessageFormat(format);
    }
    formatter.format(args, text, null);
    StringBuilder sb = new StringBuilder();
    sb.append(text);
    sb.append(" ");


    // Class name
    if (record.getSourceClassName() != null) {
      sb.append(record.getSourceClassName());
    } else {
      sb.append(record.getLoggerName());
    }

    // Method name
    if (record.getSourceMethodName() != null) {
      sb.append(" ");
      sb.append(record.getSourceMethodName());
    }
    sb.append(" - "); // lineSeparator


    // Level
    sb.append(record.getLevel().getLocalizedName());
    sb.append(": ");

    // Indent - the more serious, the more indented.
    // sb.append( String.format("% ""s") );
    int inOffset = (1000 - record.getLevel().intValue()) / 100;
    for (int i = 0; i < inOffset; i++) {
      sb.append(" ");
    }

    String message = formatMessage(record);
    sb.append(message);
    sb.append(lineSeparator);
    if (record.getThrown() != null) {
      try {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        record.getThrown().printStackTrace(pw);
        pw.close();
        sb.append(sw.toString());
      } catch (Exception ex) {
        monLogger.getMonLog().severe("Probleme formatter logger " + Util.stackTraceToString(ex));
      }
    }
    return sb.toString();
  }

}
