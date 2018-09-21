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

import com.hileco.drpc.generic.SilentCloseable;
import com.hileco.drpc.mqtt.MqttDrpcClient;
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate	
public class WiperControlServiceImpl implements WiperControl {
	
	private static Logger s_logger = LoggerFactory.getLogger(WiperControlServiceImpl.class);	
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="identifier", value="innosim")
	private String m_identifier;			

	boolean m_power;
	int m_speed;
	
	SilentCloseable m_publishedService;	
	
	@Validate
	public void init() throws SliceException {
		try {
	        MqttDrpcClient mqttDrpcClient = new MqttDrpcClientBuilder().build(m_broker);
	        mqttDrpcClient.connect();
	        m_publishedService = mqttDrpcClient.publish(WiperControl.class,  m_identifier,  this);
		}
		catch ( Throwable e ) {
			throw new SliceException(e);
		}
		
		s_logger.info("STARTED: " + WiperControlServiceImpl.class.getSimpleName());		
	}

	@Invalidate
	public void fini() throws SliceException {
		m_publishedService.close();
		s_logger.info("STOPPED: " + WiperControlServiceImpl.class.getSimpleName());			
	}
	
	@Override
	public boolean getPower() {
		return m_power;
	}

	@Override
	public void setPower(boolean power) {
		m_power = power;
	}

	@Override
	public int getSpeed() {
		return m_speed;
	}

	@Override
	public void setSpeed(int speed) {
		m_speed = speed;
	}

}
