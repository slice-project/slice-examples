package org.etri.slice.devices.car.wipercontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.service.WiperControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hileco.drpc.generic.ServiceConnector;
import com.hileco.drpc.mqtt.MqttDrpcClient;
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate	
public class WiperControlService implements WiperControl {
	
	private static Logger s_logger = LoggerFactory.getLogger(WiperControlService.class);	
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="identifier", value="innosim")
	private String m_identifier;			
	private WiperControl m_service;	
	
	@Validate
	public void init() throws SliceException {
		try {
	        MqttDrpcClient mqttDrpcClient = new MqttDrpcClientBuilder().build(m_broker);
	        mqttDrpcClient.connect();
	        ServiceConnector<WiperControl> connector = mqttDrpcClient.connector(WiperControl.class);
	        m_service = connector.connect(m_identifier);
		}
		catch ( Throwable e ) {
			throw new SliceException(e);
		}
		
		s_logger.info("STARTED: " + WiperControlService.class.getSimpleName());		
	}

	@Invalidate
	public void fini() throws SliceException {
		s_logger.info("STOPPED: " + WiperControlService.class.getSimpleName());			
	}
	
	@Override
	public boolean getPower() {
		return m_service.getPower();
	}

	@Override
	public void setPower(boolean power) {
		m_service.setPower(power);
	}

	@Override
	public int getSpeed() {
		return m_service.getSpeed();
	}

	@Override
	public void setSpeed(int speed) {
		m_service.setSpeed(speed);
	}

}
