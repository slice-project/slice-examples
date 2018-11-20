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
package org.etri.slice.agents.car.humansensor.stream;

import org.apache.edgent.topology.TStream;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.etri.slice.api.perception.EventStream;
import org.etri.slice.commons.car.context.Pressure;

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate(name=PressureStream.SERVICE_NAME)
public class PressureStream implements EventStream<Pressure> {

	public static final String SERVICE_NAME = "PressureStream";
	private static long s_timeStamp = 0;
	private static double s_value = 0;	
	
	@Override
	public TStream<Pressure> process(TStream<Pressure> stream) {
		
		return stream.filter(tuple -> {
			
			if ( s_value == tuple.getValue() ) return false;
			s_value = tuple.getValue();
			
			long current = System.currentTimeMillis();
			long interval = current - s_timeStamp;
			s_timeStamp = current;
			
			return interval > 3000;
		});

	}

}

