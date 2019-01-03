package org.etri.slice.devices.room.fancontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Updated;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.room.service.ElectronicFan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;		

@Component(publicFactory=false, immediate=true, managedservice="org.etri.slice.device")
@Provides
@Instantiate
		
public class ElectronicFanService implements ElectronicFan {
	
	private static Logger s_logger = LoggerFactory.getLogger(ElectronicFanService.class);	
	
	private final ElectronicFan m_service = new DummyElectronicFan();	
	@Property(name="device.fan.mode", value="weak")
	private String m_mode;		
			
	@Validate
	public void init() throws SliceException {
		s_logger.info("STARTED: " + this.getClass().getSimpleName() );
	}
			
	@Invalidate
	public void fini() throws SliceException {
		s_logger.info("STOPPED: " + this.getClass().getSimpleName() );		
	}
		
	@Override
	public boolean getPower() {
		s_logger.info("CALLED: getPower() - " + m_service.getPower());
		return m_service.getPower();
	}
	
	@Override		        
	public void setPower(boolean power) {
		s_logger.info("CALLED: setPower(" + power + ")");		
		m_service.setPower(power);
	}
	
	@Override
	public int getLevel() {
		s_logger.info("CALLED: getLevel() - " + m_service.getLevel());		
		return m_service.getLevel();
	}
	
	@Override		        
	public void setLevel(int level) {
		s_logger.info("CALLED: setLevel(" + level + ")");			
		m_service.setLevel(level);
	}
	
	@Updated
	public void updated() {
		System.out.println("MODE = " + m_mode);
	}	
	
	
	static class DummyElectronicFan implements ElectronicFan {

		private boolean m_power;
		private int m_level;
		
		@Override
		public boolean getPower() {
			return m_power;
		}

		@Override
		public void setPower(boolean power) {
			m_power = power;
		}

		@Override
		public int getLevel() {
			return m_level;
		}

		@Override
		public void setLevel(int level) {
			m_level = level;
		}
		
	}
	
}
