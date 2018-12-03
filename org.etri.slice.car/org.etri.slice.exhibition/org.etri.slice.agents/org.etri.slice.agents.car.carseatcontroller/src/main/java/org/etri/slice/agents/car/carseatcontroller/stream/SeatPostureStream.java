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
package org.etri.slice.agents.car.carseatcontroller.stream;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.TWindow;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.etri.slice.api.perception.EventStream;
import org.etri.slice.commons.car.context.SeatPosture;

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate(name=SeatPostureStream.SERVICE_NAME)
public class SeatPostureStream implements EventStream<SeatPosture> {

	public static final String SERVICE_NAME = "SeatPostureStream";
	
	private static SeatPosture s_previous = null;
	private static int s_threshold = 0;
	
	@Override
	public TStream<SeatPosture> process(TStream<SeatPosture> stream) {

		TWindow<SeatPosture, Integer> window = stream.last(2, TimeUnit.SECONDS,  tuple->0);
		TStream<SeatPosture> batched = window.batch((tuples, key) -> {
			if ( tuples.isEmpty() ) {
				if ( s_previous == null ) {
					s_previous = new SeatPosture();
				}
				
				return s_previous;
			}
			return tuples.get(tuples.size() - 1);
		});
		
		return batched.filter(tuple -> {
			if ( s_previous == null ) {
				s_previous = tuple;
			}
System.out.println("######## HEIGHT : " + s_previous.getHeight() + " => " + tuple.getHeight());
System.out.println("######## POSITIION : " + s_previous.getPosition() + " => " + tuple.getPosition());	
System.out.println("######## TILT : " + s_previous.getTilt() + " => " + tuple.getTilt());	

			if ( Math.abs(tuple.getHeight() - s_previous.getHeight()) > s_threshold ) {
System.out.println(".......................HEIGHT : " + s_previous.getHeight() + " => " + tuple.getHeight());				
				return true;
			}		
			if ( Math.abs(tuple.getPosition() - s_previous.getPosition()) > s_threshold ) {
System.out.println(".......................POSITION : " + s_previous.getPosition() + " => " + tuple.getPosition());						
				return true;
			}		
			if ( Math.abs(tuple.getTilt() - s_previous.getTilt()) > s_threshold ) {
System.out.println(".......................TILT : " + s_previous.getTilt() + " => " + tuple.getTilt());						
				return true;
				
			}		
			
			return false;
			
		}).filter(tuple -> {
			s_previous = tuple;
			return true;
		});
	}

}

