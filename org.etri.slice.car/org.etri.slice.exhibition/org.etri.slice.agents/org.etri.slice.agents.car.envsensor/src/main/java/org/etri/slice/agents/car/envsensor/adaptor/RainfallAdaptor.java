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
package org.etri.slice.agents.car.envsensor.adaptor;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Subscriber;
import org.etri.slice.api.agent.Agent;
import org.etri.slice.api.inference.WorkingMemory;
import org.etri.slice.api.perception.EventStream;
import org.etri.slice.core.perception.EventSubscriber;
import org.etri.slice.agents.car.envsensor.stream.RainfallStream;
import org.etri.slice.commons.car.context.Rainfall;

@Component
@Instantiate
public class RainfallAdaptor extends EventSubscriber<Rainfall> {
	
	private static final long serialVersionUID = 1801520194616903615L;

	@Property(name="topic", value=Rainfall.topic)
	private String m_topic;
	
	@Requires
	private WorkingMemory m_wm;

	@Requires
	private Agent m_agent;
	
	@Requires(from=RainfallStream.SERVICE_NAME)
	private EventStream<Rainfall> m_streaming;	
	
	protected  String getTopicName() {
		return m_topic;
	}
	
	protected WorkingMemory getWorkingMemory() {
		return m_wm;
	}
	
	protected Agent getAgent() {
		return m_agent;
	}
	
	protected EventStream<Rainfall> getEventStream() {
		return m_streaming;
	}
		
	@Subscriber(name="RainfallAdaptor", topics=Rainfall.topic,
			dataKey=Rainfall.dataKey, dataType=Rainfall.dataType)
	public void receive(Rainfall event) {
		super.subscribe(event);
	}
	
	@Validate
	public void start() {
		super.start(event -> m_wm.insert(event));
	}
	
	@Invalidate
	public void stop() {
		super.stop();
	}
}		

