package org.etri.slice.devices.room.temperaturedetector;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.device.AbstractPulledSensor;
import org.etri.slice.commons.room.context.Temperature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;		

@Component(immediate=true, managedservice="org.etri.slice.device")
@Provides
@Instantiate
public class TemperatureSensor extends AbstractPulledSensor<Temperature> {
	
	private static Logger s_logger = LoggerFactory.getLogger(TemperatureSensor.class);	
		
	@Publishes(name="TemperatureSensor", topics=Temperature.topic, dataKey=Temperature.dataKey)
	private Publisher m_publisher;
	private final TemperatureSensorEmulator m_emulator;
	
	@Property(name="device.temperature.mode", value="C")
	private String m_mode;	
	
	public TemperatureSensor() {
		m_emulator = new TemperatureSensorEmulator(this);
	}	
	
	@Override
	@Validate
	public void start() throws SliceException {
		super.start();
		m_emulator.setVisible(true);
	}

	@Override
	@Invalidate
	public void stop() throws SliceException {
		super.stop();
		m_emulator.setVisible(false);
	}

	@Override
	protected void publish(Temperature temperature) {
		m_publisher.sendData(temperature);
		s_logger.info("PUB: " + temperature);			
	}
	
	@Property(name="device.temperature.mode")
	public void setTemperatureMode(String mode) {
		m_mode = mode;
	}
}
