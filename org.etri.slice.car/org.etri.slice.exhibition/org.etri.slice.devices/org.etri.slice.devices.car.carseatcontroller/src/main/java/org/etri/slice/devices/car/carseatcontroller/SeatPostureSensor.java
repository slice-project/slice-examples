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
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.context.SeatPosture;
import org.etri.slice.commons.car.service.SeatControl;
import org.etri.slice.commons.device.AbstractPollingSensor;
import org.etri.slice.commons.device.DataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;		

@Component(publicFactory=false, immediate=true)
@Provides(specifications= {DataListener.class})
@Instantiate
public class SeatPostureSensor extends AbstractPollingSensor<SeatPosture> {
	
	private static Logger s_logger = LoggerFactory.getLogger(SeatPostureSensor.class);
	private static int s_threshold = 5;

	@Property(name="interval", value="300")
	public long m_interval;	
	
	@Publishes(name="SeatPostureSensor", topics=SeatPosture.topic, dataKey=SeatPosture.dataKey)
	private Publisher m_publisher;
	@Requires
	private SeatControl m_service;
	private SeatPosture m_posture;
	
	@Override
	@Validate
	public void start() throws SliceException {
		super.start();
		m_posture = m_service.getPosture();
		s_logger.info("STARTED: " + SeatPostureSensor.class.getSimpleName());			
	}
	
	@Override
	@Invalidate
	public void stop() throws SliceException {
		super.stop();
		s_logger.info("STOPPED: " + SeatPostureSensor.class.getSimpleName());	
	}	

	@Override
	protected void publish(SeatPosture posture) {
		if ( isChanged(posture) ) {
			m_publisher.sendData(posture);
			s_logger.info("PUB: " + posture);
		}
		
		m_posture = posture;
	}

	@Override
	protected long interval() {
		return m_interval;
	}

	@Override
	protected SeatPosture poll() {
		return m_service.getPosture();
	}
	
	private boolean isChanged(SeatPosture posture) {	
		if ( Math.abs(m_posture.getHeight() - posture.getHeight()) > s_threshold ) {
			return true;
		}		
		if ( Math.abs(m_posture.getPosition() - posture.getPosition()) > s_threshold ) {
			return true;
		}		
		if ( Math.abs(m_posture.getTilt() - posture.getTilt()) > s_threshold ) {
			return true;
		}		
		
		return false;
	}
}
