package org.etri.slice.devices.car.windowcontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.service.WindowControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hileco.drpc.generic.ServiceConnector;
import com.hileco.drpc.mqtt.MqttDrpcClient;
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate	
public class WindowControlService implements WindowControl {
	
	private static Logger s_logger = LoggerFactory.getLogger(WindowControlService.class);	
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="identifier", value="innosim")
	private String m_identifier;			
	private WindowControl m_service;	
	
	@Validate
	public void init() throws SliceException {
		try {
	        MqttDrpcClient mqttDrpcClient = new MqttDrpcClientBuilder().build(m_broker);
	        mqttDrpcClient.connect();
	        ServiceConnector<WindowControl> connector = mqttDrpcClient.connector(WindowControl.class);
	        m_service = connector.connect(m_identifier);
		}
		catch ( Throwable e ) {
			throw new SliceException(e);
		}
		
		s_logger.info("STARTED: " + WindowControlService.class.getSimpleName());		
	}

	@Invalidate
	public void fini() throws SliceException {
		s_logger.info("STOPPED: " + WindowControlService.class.getSimpleName());				
	}

	@Override
	public double getPercent() {
		return m_service.getPercent();
	}

	@Override
	public void setPercent(double percent) {
		m_service.setPercent(percent);
	}
	
}
