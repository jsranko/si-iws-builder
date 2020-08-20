package de.sranko_informatik.ibmi.iwsbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class PaseCommand {
	
	private InputStream output;
	private int exitCode;
	
	public int run(List<String> command) throws IOException, InterruptedException {
		
		ProcessBuilder builder = new ProcessBuilder(command);
		//builder.directory(new File(shellPath));
		
		Process process = builder.start();
		
		this.exitCode = process.waitFor();
		
		//builder.redirectOutput();
		
		if (exitCode != 0) {
			this.output = process.getErrorStream();
		} else {
			this.output = process.getInputStream();
		}
		
		return exitCode;
	}

	public InputStream getOutput() {
		return output;
	}

	public void setOutput(InputStream output) {
		this.output = output;
	}

	public int getExitCode() {
		return exitCode;
	}

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}
	
}
