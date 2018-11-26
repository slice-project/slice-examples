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

package org.etri.slice.agents.car.windowcontroller.wrapper;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.etri.slice.api.inference.WorkingMemory;

import org.etri.slice.commons.car.service.WindowControl;

@Component
@Instantiate
public class WindowControlWrapper implements WindowControl {
	
	@Requires
	private WindowControl m_proxy;
	
	@Requires
	private WorkingMemory m_wm;
	
	public WindowControlWrapper() {
		m_wm.addServiceWrapper(WindowControl.id, this);
	}
	
	@Override
	public double getPercent() {
		return m_proxy.getPercent();
	}
	
	@Override		        
	public void setPercent(double percent) {
		m_proxy.setPercent(percent);
	}
}
