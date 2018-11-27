package org.etri.slice.devices.car.mirrorcontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.context.SeatPosture;
import org.etri.slice.commons.car.service.MirrorControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hileco.drpc.generic.SilentCloseable;
import com.hileco.drpc.mqtt.MqttDrpcClient;
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder;		

@Component(publicFactory=false, immediate=true)
@Provides
//@Instantiate	
public class MirrorControlServiceImpl implements MirrorControl {
	
	private static Logger s_logger = LoggerFactory.getLogger(MirrorControlServiceImpl.class);	
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="identifier", value="innosim/right")
	private String m_identifier;			

	double m_pan;
	double m_tilt;
	
	SilentCloseable m_publishedService;	
	
	@Validate
	public void init() throws SliceException {
		try {
	        MqttDrpcClient mqttDrpcClient = new MqttDrpcClientBuilder().build(m_broker);
	        mqttDrpcClient.connect();
	        m_publishedService = mqttDrpcClient.publish(MirrorControl.class,  m_identifier,  this);
		}
		catch ( Throwable e ) {
			throw new SliceException(e);
		}
		
		s_logger.info("STARTED: " + MirrorControlServiceImpl.class.getSimpleName());		
	}

	@Invalidate
	public void fini() throws SliceException {
		m_publishedService.close();
		s_logger.info("STOPPED: " + MirrorControlServiceImpl.class.getSimpleName());			
	}

	@Override
	public double getPan() {
		return m_pan;
	}

	@Override
	public void setPan(double pan) {
		m_pan = pan;
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
	public void setSittingHeight(double height) {
				
	}

	@Override
	public void adjustPosture(SeatPosture posture) {
		
	}
	

}
