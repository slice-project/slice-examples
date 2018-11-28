package org.etri.slice.devices.room.temperaturedetector;

import java.awt.EventQueue;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.etri.slice.commons.Sensor;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.room.context.Temperature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate
public class TemperatureSensor implements Runnable {
	
	private static Logger s_logger = LoggerFactory.getLogger(TemperatureSensor.class);	
		
	@Publishes(name="TemperatureSensor", topics=Temperature.topic, dataKey=Temperature.dataKey)
	private Publisher m_publisher;
	
	@Validate
	public void start() throws SliceException {
		EventQueue.invokeLater(this);
		s_logger.info("Temperature Sensor started");
		
	}

	@Invalidate
	public void stop() throws SliceException {
		s_logger.info("Temperature Sensor stoppted");
	}
	
	@Override
	public void run() {
		try {
			TemperatureSensorGUI window = new TemperatureSensorGUI(m_publisher);
			window.m_frame.setVisible(true);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
