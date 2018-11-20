package org.etri.slice.commons.car.service;

import javax.management.MXBean;

@MXBean
public interface RadioControl {
	
	static final String id = "radioControl";
	static final String setPower = "radioControl.setPower";
	static final String setVolume = "radioControl.setVolume";
	static final String setChannel = "radioControl.setChannel";
	
	boolean getPower();
				        
	void setPower(boolean power);
	
	int getVolume();
				        
	void setVolume(int volume);
	
	int getChannel();
				        
	void setChannel(int channel);
	
}
