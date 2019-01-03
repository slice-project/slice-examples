package org.etri.slice.commons.room.service;

import javax.management.MXBean;

@MXBean
public interface ElectronicFan {
	
	static final String id = "electronicFan";
	static final String setPower = "electronicFan.setPower";
	static final String setLevel = "electronicFan.setLevel";
	
	boolean getPower();
				        
	void setPower(boolean power);
	
	int getLevel();
				        
	void setLevel(int level);
	
}
