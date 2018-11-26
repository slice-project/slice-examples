package org.etri.slice.commons.car.service;

import javax.management.MXBean;

@MXBean
public interface WiperControl {
	
	static final String id = "wiperControl";
	static final String setPower = "wiperControl.setPower";
	static final String setSpeed = "wiperControl.setSpeed";
	
	boolean getPower();
				        
	void setPower(boolean power);
	
	int getSpeed();
				        
	void setSpeed(int speed);
	
}
