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

package org.etri.slice.agents.car.radiocontroller.wrapper;

import org.apache.edgent.execution.services.ControlService;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.etri.slice.api.agent.Agent;
import org.etri.slice.api.learning.Action;
import org.etri.slice.api.learning.Action.ActionBuilder;
import org.etri.slice.api.learning.ActionLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.etri.slice.commons.car.context.HumanInfo;
import org.etri.slice.commons.car.context.NoiseLevel;
import org.etri.slice.commons.car.service.RadioControl;

@Component
@Instantiate
public class RadioControlCommander implements RadioControl {
	private static Logger s_logger = LoggerFactory.getLogger(RadioControlCommander.class);
	
	@Requires
	private RadioControl m_proxy;
	
	@Requires
	private ActionLogger m_logger;
	
	@Requires
	private Agent m_agent;
	
	public RadioControlCommander() {
		ControlService control = m_agent.getService(ControlService.class);
		control.registerControl(this.getClass().getSimpleName(), RadioControl.id, null, RadioControl.class, this);
	}
	
	@Override
	public boolean getPower() {
		return m_proxy.getPower();
	}
	
	@Override		        
	public void setPower(boolean power) {
		m_proxy.setPower(power);
		
	}
	@Override
	public int getVolume() {
		return m_proxy.getVolume();
	}
	
	@Override		        
	public void setVolume(int volume) {
		m_proxy.setVolume(volume);
		
		ActionBuilder builder = Action.builder();
		builder.setRelation("radio_volume");
		builder.addContext(NoiseLevel.Field.value);
		builder.setAction(RadioControl.setVolume, volume);
		logAction(builder.build());		
	}
	@Override
	public int getChannel() {
		return m_proxy.getChannel();
	}
	
	@Override		        
	public void setChannel(int channel) {
		m_proxy.setChannel(channel);
		
	}
	private void logAction(Action action) {
		try {
			m_logger.log(action);
		} 
		catch ( Exception e ) {
			s_logger.error("ERR : " + e.getMessage());
		}			
	}

	@Override
	public int getRecommendedGanre(HumanInfo info) {
		return m_proxy.getRecommendedGanre(info);
	}			
}
