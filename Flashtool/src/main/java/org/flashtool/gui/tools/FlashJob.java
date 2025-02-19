package org.flashtool.gui.tools;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Shell;
import org.flashtool.flashsystem.Bundle;
import org.flashtool.flashsystem.Flasher;
import org.flashtool.flashsystem.FlasherFactory;
import org.flashtool.gui.TARestore;
import org.flashtool.system.DeviceChangedListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlashJob extends Job {

	Flasher flash = null;
	Bundle _bundle;
	boolean canceled = false;
	Shell _shell;
	
	public FlashJob(String name) {
		super(name);
	}
	
	public void setBundle(Bundle b) {
		_bundle = b;
	}
	
	public void setShell(Shell shell) {
		_shell = shell;
	}
	
	public Flasher getFlasher() {
		return flash;
	}

    protected IStatus run(IProgressMonitor monitor) {
    	try {
    		if (_bundle.open()) {
    			log.info("Please connect your device into flashmode.");
    			String result = "";
    			result = (String)WidgetTask.openWaitDeviceForFlashmode(_shell);
    			if (result.equals("OK")) {
    				flash = FlasherFactory.getFlasher(_bundle, _shell);
    				if (flash.open())
    					flash.flash();
    				_bundle.close();
    			}
    			else {
    				_bundle.close();
    				log.info("Flash canceled");
    			}
    		}
    		else {
    			log.info("Cannot open bundle. Flash operation canceled");
    		}
    		DeviceChangedListener.enableDetection();
			return Status.OK_STATUS;
    	}
    	catch (Exception e) {
    		log.error(e.getMessage());
    		DeviceChangedListener.enableDetection();
    		return Status.CANCEL_STATUS;
    	}
    }
}
