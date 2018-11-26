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
package org.etri.slice.devices.car.radiocontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.handlers.event.Publishes;
import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.context.RadioState;
import org.etri.slice.commons.device.AbstractPulledSensor;
import org.etri.slice.commons.device.DataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;		

@Component(publicFactory=false, immediate=true)
@Provides(specifications= {DataListener.class})
@Instantiate
public class RadioStateSensor extends AbstractPulledSensor<RadioState> {
	
	private static Logger s_logger = LoggerFactory.getLogger(RadioStateSensor.class);	

	@Publishes(name="RadioStateSensor", topics=RadioState.topic, dataKey=RadioState.dataKey)
	private Publisher m_publisher;
	
	@Override
	@Validate
	public void start() throws SliceException {
		super.start();
		s_logger.info("STARTED: " + RadioStateSensor.class.getSimpleName());			
	}
	
	@Override
	@Invalidate
	public void stop() throws SliceException {
		super.stop();
		s_logger.info("STOPPED: " + RadioStateSensor.class.getSimpleName());	
	}	

	@Override
	protected void publish(RadioState state) {
		m_publisher.sendData(state);
		s_logger.info("PUB: " + state);	
	}

}
