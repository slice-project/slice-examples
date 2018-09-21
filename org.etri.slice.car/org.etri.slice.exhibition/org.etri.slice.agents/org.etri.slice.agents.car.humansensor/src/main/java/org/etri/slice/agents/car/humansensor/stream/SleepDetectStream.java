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

import java.util.concurrent.TimeUnit;

import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.TWindow;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.etri.slice.api.perception.EventStream;
import org.etri.slice.commons.car.context.SleepDetect;

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate(name=SleepDetectStream.SERVICE_NAME)
public class SleepDetectStream implements EventStream<SleepDetect> {

	public static final String SERVICE_NAME = "SleepDetectStream";
	
	private static int VALID_SIZE = 10; 
	volatile double m_elapsedTime = 0;
	
	@Override
	public TStream<SleepDetect> process(TStream<SleepDetect> stream) {
		
		TWindow<SleepDetect, Integer> window = stream.last(500, TimeUnit.MILLISECONDS, tuple -> 0);
		TStream<SleepDetect> batched = window.batch((tuples, key) -> {			
			if ( tuples.size() >= VALID_SIZE ) {
				m_elapsedTime += 0.5;				
			}
			else {
				m_elapsedTime = 0;
			}			
			return SleepDetect.builder().elapsedTime(m_elapsedTime).build();
		});
		return batched.filter(tuple -> tuple.getElapsedTime() > 0);
	}

}

