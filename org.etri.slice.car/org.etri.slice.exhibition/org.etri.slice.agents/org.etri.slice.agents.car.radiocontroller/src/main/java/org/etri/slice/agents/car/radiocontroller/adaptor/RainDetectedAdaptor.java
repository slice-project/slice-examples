package org.etri.slice.agents.car.radiocontroller.adaptor;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.api.agent.Agent;
import org.etri.slice.api.inference.WorkingMemory;
import org.etri.slice.api.perception.EventStream;
import org.etri.slice.core.perception.MqttEventSubscriber;
import org.etri.slice.agents.car.radiocontroller.stream.RainDetectedStream;
import org.etri.slice.commons.car.event.RainDetected;

@Component
@Instantiate
public class RainDetectedAdaptor extends MqttEventSubscriber<RainDetected> {
	
	private static final long serialVersionUID = -2020709889450622218L;

	@Property(name="topic", value=RainDetected.TOPIC)
	private String m_topic;
	
	@Property(name="url", value="tcp://192.168.0.37:1883")
	private String m_url;
	
	@Requires
	private WorkingMemory m_wm;

	@Requires
	private Agent m_agent;
	
	@Requires(from=RainDetectedStream.SERVICE_NAME)
	private EventStream<RainDetected> m_streaming;	
	
	protected  String getTopicName() {
		return m_topic;
	}
	
	protected String getMqttURL() {
		return m_url;
	}
	
	protected WorkingMemory getWorkingMemory() {
		return m_wm;
	}
	
	protected Agent getAgent() {
		return m_agent;
	}
	
	protected Class<?> getEventType() {
		return RainDetected.class;
	}
	
	protected EventStream<RainDetected> getEventStream() {
		return m_streaming;
	}	
		
	@Validate
	public void start() {
		super.start();
	}
	
	@Invalidate
	public void stop() {
		super.stop();
	}
}

