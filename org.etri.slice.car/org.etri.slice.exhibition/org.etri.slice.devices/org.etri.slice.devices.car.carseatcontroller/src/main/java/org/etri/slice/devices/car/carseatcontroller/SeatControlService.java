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

import com.hileco.drpc.generic.ServiceConnector;
import com.hileco.drpc.mqtt.MqttDrpcClient;
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate	
public class SeatControlService implements SeatControl {
	
	private static Logger s_logger = LoggerFactory.getLogger(SeatControlService.class);	
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="identifier", value="innosim")
	private String m_identifier;			
	private SeatControl m_service;	
	
	@Validate
	public void init() throws SliceException {
		try {
	        MqttDrpcClient mqttDrpcClient = new MqttDrpcClientBuilder().build(m_broker);
	        mqttDrpcClient.connect();
	        ServiceConnector<SeatControl> connector = mqttDrpcClient.connector(SeatControl.class);
	        m_service = connector.connect(m_identifier);
		}
		catch ( Throwable e ) {
			throw new SliceException(e);
		}
		
		s_logger.info("STARTED: " + SeatControlService.class.getSimpleName());				
	}

	@Invalidate
	public void fini() throws SliceException {
		s_logger.info("STOPPED: " + SeatControlService.class.getSimpleName());		
	}
	
	@Override
	public double getHeight() {
		return m_service.getHeight();
	}
	
	@Override		        
	public void setHeight(double height) {
		m_service.setHeight(height);
	}
	
	@Override
	public double getPosition() {
		return m_service.getPosition();
	}
	
	@Override		        
	public void setPosition(double position) {
		m_service.setPosition(position);
	}
	
	@Override
	public double getTilt() {
		return m_service.getTilt();
	}
	
	@Override		        
	public void setTilt(double tilt) {
		m_service.setTilt(tilt);
	}
	
	@Override
	public SeatPosture getPosture() {
		SeatPosture posture = SeatPosture.builder().height(getHeight()).position(getPosition()).tilt(getTilt()).build();
		return posture;
	}
	
	@Override		        
	public void setPosture(SeatPosture posture) {
		m_service.setPosture(posture);
	}

	@Override
	public void initialize() {
		m_service.initialize();
	}
	
}
