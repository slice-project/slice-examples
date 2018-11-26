package org.etri.slice.devices.car.mirrorcontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.service.MirrorControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hileco.drpc.generic.ServiceConnector;
import com.hileco.drpc.mqtt.MqttDrpcClient;
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate	
public class MirrorControlService implements MirrorControl {
	
	private static Logger s_logger = LoggerFactory.getLogger(MirrorControlService.class);	
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="identifier", value="innosim/left")
	private String m_identifier;			
	private MirrorControl m_service;	
	
	@Validate
	public void init() throws SliceException {
		try {
	        MqttDrpcClient mqttDrpcClient = new MqttDrpcClientBuilder().build(m_broker);
	        mqttDrpcClient.connect();
	        ServiceConnector<MirrorControl> connector = mqttDrpcClient.connector(MirrorControl.class);
	        m_service = connector.connect(m_identifier);
		}
		catch ( Throwable e ) {
			throw new SliceException(e);
		}
		
		s_logger.info("STARTED: " + MirrorControlService.class.getSimpleName());		
	}

	@Invalidate
	public void fini() throws SliceException {
		s_logger.info("STOPPED: " + MirrorControlService.class.getSimpleName());			
	}
	
	@Override
	public double getPan() {
		return m_service.getPan();
	}

	@Override
	public void setPan(double pan) {
		m_service.setPan(pan);
	}

	@Override
	public double getTilt() {
		return m_service.getTilt();
	}

	@Override
	public void setTilt(double tilt) {
		m_service.setTilt(tilt);
	}

}
