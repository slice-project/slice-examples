package org.etri.slice.devices.car.radiocontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.service.RadioControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hileco.drpc.generic.ServiceConnector;
import com.hileco.drpc.mqtt.MqttDrpcClient;
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate	
public class RadioControlService implements RadioControl {
	
	private static Logger s_logger = LoggerFactory.getLogger(RadioControlService.class);	
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="identifier", value="innosim")
	private String m_identifier;			
	private RadioControl m_service;	
	
	@Validate
	public void init() throws SliceException {
		try {
	        MqttDrpcClient mqttDrpcClient = new MqttDrpcClientBuilder().build(m_broker);
	        mqttDrpcClient.connect();
	        ServiceConnector<RadioControl> connector = mqttDrpcClient.connector(RadioControl.class);
	        m_service = connector.connect(m_identifier);
		}
		catch ( Throwable e ) {
			throw new SliceException(e);
		}
		
		s_logger.info("STARTED: " + RadioControlService.class.getSimpleName());			
	}

	@Invalidate
	public void fini() throws SliceException {
		s_logger.info("STOPPED: " + RadioControlService.class.getSimpleName());		
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
	public int getVolume() {
		return m_service.getVolume();
	}

	@Override
	public void setVolume(int volume) {
		m_service.setVolume(volume);
	}

	@Override
	public int getChannel() {
		return m_service.getChannel();
	}

	@Override
	public void setChannel(int channel) {
		m_service.setChannel(channel);
	}
	
}
