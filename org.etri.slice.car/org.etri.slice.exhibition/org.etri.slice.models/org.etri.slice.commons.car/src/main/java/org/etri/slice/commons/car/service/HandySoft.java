package org.etri.slice.commons.car.service;

import javax.management.MXBean;

@MXBean
public interface HandySoft {
	
	static final String id = "handySoft";
	static final String setChannel = "handySoft.setChannel";
	static final String setHeight = "handySoft.setHeight";
	static final String setPosition = "handySoft.setPosition";
	static final String setTilt = "handySoft.setTilt";
	static final String setDrowsy = "handySoft.setDrowsy";
	
	int getChannel();
				        
	void setChannel(int channel);
	
	double getHeight();
				        
	void setHeight(double height);
	
	double getPosition();
				        
	void setPosition(double position);
	
	double getTilt();
				        
	void setTilt(double tilt);
	
	int getDrowsy();
	
	void setDrowsy(int drowsy);
	
}
