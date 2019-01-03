/**
 * Copyright (c) 2017-2017 SLICE project team (yhsuh@etri.re.kr)
 * http://slice.etri.re.kr
 *
 * This file is part of The ROOT project of SLICE components and applications
 *
 * This Program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This Program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with The ROOT project of SLICE components and applications; see the file COPYING.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.etri.slice.agents.room.fancontroller.adaptor;

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
import org.etri.slice.agents.room.fancontroller.stream.TemperatureChangedStream;
import org.etri.slice.commons.room.event.TemperatureChanged;

@Component(managedservice="org.etri.slice.agent")
@Instantiate
public class TemperatureChangedAdaptor extends MqttEventSubscriber<TemperatureChanged> {
	
	private static final long serialVersionUID = -2641774684696263459L;

	@Property(name="topic", value=TemperatureChanged.TOPIC)
	private String m_topic;
	
	@Property(name="agent.agency.url", value="tcp://129.254.87.240:1883")
	private String m_url;
	private String m_prevUrl;
	
	@Requires
	private WorkingMemory m_wm;

	@Requires
	private Agent m_agent;
	
	@Requires(from=TemperatureChangedStream.SERVICE_NAME)
	private EventStream<TemperatureChanged> m_streaming;	
	
	protected  String getTopicName() {
		return m_topic;
	}
	
	protected synchronized String getMqttURL() {
		return m_url;
	}
	
	protected WorkingMemory getWorkingMemory() {
		return m_wm;
	}
	
	protected Agent getAgent() {
		return m_agent;
	}
	
	protected Class<?> getEventType() {
		return TemperatureChanged.class;
	}
	
	protected EventStream<TemperatureChanged> getEventStream() {
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
	
    @Property(name="agent.agency.url")
    public synchronized void setAgencyUrl(String url) {
    	if ( url.trim().equals(m_prevUrl) ) {
    		return;
    	}
    	
        m_prevUrl = url;   
        super.restart();
    }
}

