/**
 *
 * Copyright (c) 2017-2017 SLICE project team (yhsuh@etri.re.kr)
 * http://slice.etri.re.kr
 *
 * This file is part of The SLICE components
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
 * along with The SLICE components; see the file COPYING.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.etri.slice.devices.car.humansensor;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.car.context.Pressure;
import org.etri.slice.commons.device.DataListener;
import org.etri.slice.commons.device.mqtt.AbstractMqttSubscriber;
import org.etri.slice.commons.device.mqtt.MqttDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(publicFactory=false, immediate=true)
@Instantiate
public class PressureSubscriber extends AbstractMqttSubscriber<Pressure> {

	private static final long serialVersionUID = 8534794047124788261L;
	private static Logger s_logger = LoggerFactory.getLogger(PressureSubscriber.class);		

	@Property(name="topic", value="e/org.etri.slice.commons.car.context.Pressure")
	private String m_topic;
	
	@Requires
	private MqttDevice m_device;
	
	@Requires(filter="(dataType=org.etri.slice.commons.car.context.Pressure)")
	private DataListener<Pressure> m_listener;
	
	@Override
	@Validate
	public void start(){
		super.start();		
		s_logger.info("STARTED: " + PressureSubscriber.class.getSimpleName());			
	}
	
	@Override
	@Invalidate
	public void stop() {
		super.stop();
		s_logger.info("STOPPED: " + PressureSubscriber.class.getSimpleName());	
	}		
	
	@Override
	protected DataListener<Pressure> getDataListener() {
		return m_listener;
	}

	@Override
	protected MqttDevice getDevice() {
		return m_device;
	}

	@Override
	protected Class<?> getEventType() {
		return Pressure.class;
	}

	@Override
	protected String getTopicName() {
		return m_topic;
	}

}
