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
package org.etri.slice.devices.car.carseatcontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.car.service.SeatControl;
import org.etri.slice.commons.device.DataListener;
import org.etri.slice.commons.device.mqtt.AbstractMqttSubscriber;
import org.etri.slice.commons.device.mqtt.MqttCommand;
import org.etri.slice.commons.device.mqtt.MqttCommandInvoker;
import org.etri.slice.commons.device.mqtt.MqttDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(publicFactory=false, immediate=true)
@Instantiate
public class SeatControlCommandAdaptor extends AbstractMqttSubscriber<MqttCommand> {


	private static final long serialVersionUID = -7393977422766385098L;
	private static Logger s_logger = LoggerFactory.getLogger(SeatControlCommandAdaptor.class);		

	@Property(name="topic", value="d/SeatControl")
	private String m_topic;
	
	@Property(name="port", value="3403")
	private int m_port;	
	
	@Requires
	private MqttDevice m_device;
	
	private MqttCommandInvoker<SeatControl> m_invoker;
	
	@Override
	@Validate
	public void start(){
		super.start();
		
		try {
			m_invoker = new MqttCommandInvoker<SeatControl>(SeatControl.class, m_port);
		} 
		catch (Throwable e) {
			s_logger.error("ERR : " + e.getMessage());
		}		
		s_logger.info("STARTED: " + SeatControlCommandAdaptor.class.getSimpleName());			
	}
	
	@Override
	@Invalidate
	public void stop() {
		super.stop();
		s_logger.info("STOPPED: " + SeatControlCommandAdaptor.class.getSimpleName());	
	}		
	
	@Override
	protected DataListener<MqttCommand> getDataListener() {
		return m_invoker;
	}

	@Override
	protected MqttDevice getDevice() {
		return m_device;
	}

	@Override
	protected Class<?> getEventType() {
		return MqttCommand.class;
	}

	@Override
	protected String getTopicName() {
		return m_topic;
	}
}
