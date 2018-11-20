package org.etri.slice.devices.car.humansensor;

import java.nio.charset.StandardCharsets;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.service.HandySoft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate	
public class HandySoftService implements HandySoft {
	
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	private static class Height {
		public double height;
	}
	
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	private static class Position {
		public double position;
	}
	
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	private static class Tilt {
		public double tilt;
	}
	
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	private static class Channel {
		public int channel;
	}
	
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	private static class Drowsy {
		public int drowsy;
	}		
	
	private static Logger s_logger = LoggerFactory.getLogger(HandySoftService.class);
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="topic", value="handysoft")
	private String m_topic;	
	
	Channel m_channel = new Channel();
	Height m_height = new Height();
	Position m_position = new Position();
	Tilt m_tilt = new Tilt();
	Drowsy m_drowsy = new Drowsy();
	
	private MqttClient m_client;
	private ObjectMapper m_jsonMapper = new ObjectMapper();
	
	@Validate
	public void init() throws SliceException {
		try {
			m_client = new MqttClient(m_broker, "HumanSensor");
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            m_client.connect(connOpts);
            
    		s_logger.info("STARTED: " + HandySoftService.class.getSimpleName());
		} 
		catch (Throwable e) {
			s_logger.error("ERR : " + e.getMessage());
		}		
	}

	@Invalidate
	public void fini() throws SliceException {
		try {
			m_client.disconnect();
    		s_logger.info("STOPPED: " + HandySoftService.class.getSimpleName());				
		} 
		catch (Throwable e) {
			s_logger.error("ERR : " + e.getMessage());
		}
	}
	
	@Override
	public int getChannel() {
		return m_channel.channel;
	}
	
	@Override		        
	public void setChannel(int channel) {
		try {
			m_channel.channel = channel;
			String topic = m_topic + "/channel";		
			publish(topic, m_jsonMapper.writeValueAsString(m_channel));
		}
		catch ( Throwable e ) {
			s_logger.error("ERR : " + e.getMessage());
		}
	}
	
	@Override
	public double getHeight() {
		return m_height.height;
	}
	
	@Override		        
	public void setHeight(double height) {
		try {
			m_height.height = height;
			String topic = m_topic + "/height";
			publish(topic, m_jsonMapper.writeValueAsString(m_height));
		}
		catch ( Throwable e ) {
			s_logger.error("ERR : " + e.getMessage());
		}
	}
	
	@Override
	public double getPosition() {
		return m_position.position;
	}
	
	@Override		        
	public void setPosition(double position) {
		try {
			m_position.position = position;
			String topic = m_topic + "/position";
			publish(topic, m_jsonMapper.writeValueAsString(m_position));
		}
		catch ( Throwable e ) {
			s_logger.error("ERR : " + e.getMessage());
		}
	}
	
	@Override
	public double getTilt() {
		return m_tilt.tilt;
	}
	
	@Override		        
	public void setTilt(double tilt) {
		try {
			m_tilt.tilt = tilt;
			String topic = m_topic + "/tilt";
			publish(topic, m_jsonMapper.writeValueAsString(m_tilt));
		}
		catch ( Throwable e ) {
			s_logger.error("ERR : " + e.getMessage());
		}
	}
	
	@Override
	public int getDrowsy() {
		return m_drowsy.drowsy;
	}	

	@Override
	public void setDrowsy(int drowsy) {
		try {
			m_drowsy.drowsy = drowsy;
			String topic = m_topic + "/drowsy";		
			publish(topic, m_jsonMapper.writeValueAsString(m_drowsy));
		}
		catch ( Throwable e ) {
			s_logger.error("ERR : " + e.getMessage());
		}
	}	
	
	private void publish(String topic, String payload) {
		MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
		message.setQos(2);
		try {
			m_client.publish(topic, message);
		} 
		catch (Throwable e) {
			s_logger.error("ERR : " + e.getMessage());
		}
	}	
}
