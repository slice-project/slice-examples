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

package org.etri.slice.agents.car.carseatcontroller.wrapper;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.etri.slice.api.inference.WorkingMemory;

import org.etri.slice.commons.car.context.SeatPosture;
import org.etri.slice.commons.car.service.SeatControl;

@Component
@Instantiate
public class SeatControlWrapper implements SeatControl {
	
	@Requires
	private SeatControl m_proxy;
	
	@Requires
	private WorkingMemory m_wm;
	
	public SeatControlWrapper() {
		m_wm.addServiceWrapper(SeatControl.id, this);
	}
	
	@Override
	public double getHeight() {
		return m_proxy.getHeight();
	}
	
	@Override		        
	public void setHeight(double height) {
		m_proxy.setHeight(height);
	}
	@Override
	public double getPosition() {
		return m_proxy.getPosition();
	}
	
	@Override		        
	public void setPosition(double position) {
		m_proxy.setPosition(position);
	}
	@Override
	public double getTilt() {
		return m_proxy.getTilt();
	}
	
	@Override		        
	public void setTilt(double tilt) {
		m_proxy.setTilt(tilt);
	}
	@Override
	public SeatPosture getPosture() {
		return m_proxy.getPosture();
	}
	
	@Override		        
	public void setPosture(SeatPosture posture) {
		m_proxy.setPosture(posture);
	}
}
