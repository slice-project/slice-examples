package org.etri.slice.devices.car.carseatcontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.context.SeatPosture;
import org.etri.slice.commons.car.service.SeatControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hileco.drpc.generic.SilentCloseable;
import com.hileco.drpc.mqtt.MqttDrpcClient;
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate	
public class SeatControlServiceImpl implements SeatControl {
	
	private static Logger s_logger = LoggerFactory.getLogger(SeatControlServiceImpl.class);	
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="identifier", value="innosim")
	private String m_identifier;

	double m_height;
	double m_position;
	double m_tilt;
	SeatPosture m_posture;
	
	SilentCloseable m_publishedService;
	
	@Validate
	public void init() throws SliceException {
		try {
	        MqttDrpcClient mqttDrpcClient = new MqttDrpcClientBuilder().build(m_broker);
	        mqttDrpcClient.connect();
	        m_publishedService = mqttDrpcClient.publish(SeatControl.class,  m_identifier,  this);
		}
		catch ( Throwable e ) {
			throw new SliceException(e);
		}
		
		s_logger.info("STARTED: " + SeatControlServiceImpl.class.getSimpleName());				
	}

	@Invalidate
	public void fini() throws SliceException {
		m_publishedService.close();
		s_logger.info("STOPPED: " + SeatControlServiceImpl.class.getSimpleName());		
	}
	
	@Override
	public double getHeight() {
		return m_height;
	}
	
	@Override		        
	public void setHeight(double height) {
		m_height = height;
	}
	
	@Override
	public double getPosition() {
		return m_position;
	}
	
	@Override		        
	public void setPosition(double position) {
		m_position = position;
	}
	
	@Override
	public double getTilt() {
		return m_tilt;
	}
	
	@Override		        
	public void setTilt(double tilt) {
		m_tilt = tilt;
	}
	
	@Override
	public SeatPosture getPosture() {
		return m_posture;
	}
	
	@Override		        
	public void setPosture(SeatPosture posture) {
		m_posture = posture;
	}

	@Override
	public void initialize() {
		m_position = 0;
		m_height = 0;
		m_tilt = 0;
	}
	
}
