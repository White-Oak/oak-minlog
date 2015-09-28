package me.whiteoak.minlog;

import java.io.*;

/**
 * Writes log to a file. A new file is created for every category.<br>
 * Logs are stored in ./logs/ by default. Logs have "_log.txt" suffix by default.
 *
 * @author White Oak
 */
public class FileLogger extends Log.Logger {

    private File logsDirectory = new File("./logs/");
    private String logFileSuffix = "_log.txt";

    public void setLogFileSuffix(String logFileSuffix) {
	this.logFileSuffix = logFileSuffix;
    }

    public void setLogsDirectory(File logsDirectory) {
	this.logsDirectory = logsDirectory;
    }

    @Override
    protected void print(CharSequence message, String category) {
	logsDirectory.mkdir();
	File file = new File(logsDirectory, category + logFileSuffix);
	if (file.canWrite()) {
	    try (FileWriter fileWriter = new FileWriter(file)) {
		fileWriter.append(message);
		fileWriter.append(System.lineSeparator());
	    } catch (IOException ex) {
		super.log(Log.LEVEL_ERROR, "minlog", "While writing to " + file.getPath(), ex);
	    }
	} else {
	    log(Log.LEVEL_ERROR, "minlog", "Cannot write to " + file.getPath(), null);
	}
    }
}
