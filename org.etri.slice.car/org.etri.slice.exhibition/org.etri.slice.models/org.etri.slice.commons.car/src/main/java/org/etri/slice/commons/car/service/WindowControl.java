package org.etri.slice.commons.car.service;

import javax.management.MXBean;

@MXBean
public interface WindowControl {
	
	static final String id = "windowControl";
	static final String setPercent = "windowControl.setPercent";
	
	double getPercent();
				        
	void setPercent(double percent);
	
}
