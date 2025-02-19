package org.flashtool.gui;

import org.flashtool.flashsystem.FlasherConsole;
import org.flashtool.jna.linux.JUsb;
import org.flashtool.libusb.LibUsbException;
import org.flashtool.logger.MyLogger;
import org.flashtool.system.AWTKillerThread;
import org.flashtool.system.GlobalConfig;
import org.flashtool.system.OS;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@Slf4j
public class Main {
    

    @Option(names = {"-console" }, paramLabel = "CONSOLE", description = "Console mode invocation")
    private boolean console;

    @Option(names = { "--action" }, paramLabel = "ACTION", description = "Console mode action type")
    String action;

    @Option(names = { "--file" }, paramLabel = "FILE", description = "Console mode action type")
    String file;

    @Option(names = { "--wipedata" }, paramLabel = "FILE", description = "Console mode action type")
    String wipedata;

    @Option(names = { "--wipecache" }, paramLabel = "WIPECACHE", description = "Console mode action type")
    String wipecache;

    @Option(names = { "--baseband" }, paramLabel = "BASEBAND", description = "Console mode action type")
    String baseband;

    @Option(names = { "--kernel" }, paramLabel = "KERNEL", description = "Console mode action type")
    String kernel;

    @Option(names = { "--system" }, paramLabel = "SYSTEM", description = "Console mode action type")
    String system;
    
    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested;
    
	public static void main(String[] args) {

		Main main = new Main();
		new CommandLine(main).parseArgs(args);
		main.run();
	
	}

	public void run () {
		MyLogger.setMode(MyLogger.CONSOLE_MODE);
		MyLogger.setLevel(GlobalConfig.getProperty("loglevel"));
		log.info("JAVA_HOME : "+System.getProperty("java.home"));
		OS.getFolderFirmwaresDownloaded();
		OS.getFolderFirmwaresPrepared();
		OS.getFolderFirmwaresSinExtracted();
		OS.getFolderMyDevices();
		OS.getFolderRegisteredDevices();
		AWTKillerThread k = new AWTKillerThread();
		k.start();
		try {
			Main.initLinuxUsb();
			if (console) {
				processConsole();
			}
			else {
				MyLogger.setMode(MyLogger.GUI_MODE);
				MainSWT window = new MainSWT();
				window.open();
			}
		}
	
		catch (Exception e) {
			e.printStackTrace();
		}
		k.done();
	}

	public static void initLinuxUsb() throws LibUsbException {
			if (OS.getName()!="windows") JUsb.init();
	}

/*	private static OptionSet parseCmdLine(String[] args) {
		OptionParser parser = new OptionParser();
		OptionSet options;
		parser.accepts( "console" );
		try {
			options = parser.parse(args);
		}
		catch (Exception e) {
			parser.accepts("action").withRequiredArg().required();
			parser.accepts("file").withOptionalArg().defaultsTo("");
			parser.accepts("method").withOptionalArg().defaultsTo("auto");
			parser.accepts("wipedata").withOptionalArg().defaultsTo("yes");
			parser.accepts("wipecache").withOptionalArg().defaultsTo("yes");
			parser.accepts("baseband").withOptionalArg().defaultsTo("yes");
			parser.accepts("system").withOptionalArg().defaultsTo("yes");
			parser.accepts("kernel").withOptionalArg().defaultsTo("yes");
			options = parser.parse(args);        	
		}
		return options;
	}*/

	public void processConsole() throws Exception {
		
		if (action == null) {
			System.out.println("An action is mandatory in console mode");
			FlasherConsole.exit();
		}
		if (action.toLowerCase().equals("flash")) {
			FlasherConsole.init(false);
			FlasherConsole.doFlash(file, wipedata.equals("yes"), wipecache.equals("yes"), baseband.equals("no"), kernel.equals("no"), system.equals("no"));
		}
		if (action.toLowerCase().equals("imei")) {
			FlasherConsole.init(false);
			FlasherConsole.doGetIMEI();
		}
		if (action.toLowerCase().equals("root")) {
			FlasherConsole.init(true);
			FlasherConsole.doRoot();
		}
		if (action.toLowerCase().equals("extract")) {
			FlasherConsole.init(true);
			FlasherConsole.doExtract(file);
		}
		//if (action.toLowerCase().equals("blunlock")) {
		//	FlasherConsole.init(true);
		//	FlasherConsole.doBLUnlock();        		
		//}
		FlasherConsole.exit();		
	}

}