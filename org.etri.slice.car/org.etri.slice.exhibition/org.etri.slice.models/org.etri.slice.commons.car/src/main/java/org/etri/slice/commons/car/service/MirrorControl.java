package org.etri.slice.commons.car.service;

import javax.management.MXBean;
import org.etri.slice.commons.car.context.SeatPosture;

@MXBean
public interface MirrorControl {
	
	static final String id = "mirrorControl";
	static final String setPan = "mirrorControl.setPan";
	static final String setTilt = "mirrorControl.setTilt";
	
	double getPan();
				        
	void setPan(double pan);
	
	double getTilt();
				        
	void setTilt(double tilt);
	
	void setSittingHeight(double height);
	
	void adjustPosture(SeatPosture posture);	
	
}
