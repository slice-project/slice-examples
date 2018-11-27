package org.etri.slice.agents.car.mirrorcontroller.stream;

import org.apache.edgent.topology.TStream;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.etri.slice.api.perception.EventStream;	
import org.etri.slice.commons.car.event.SeatPostureChanged;

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate(name=SeatPostureChangedStream.SERVICE_NAME)
public class SeatPostureChangedStream implements EventStream<SeatPostureChanged> {

	public static final String SERVICE_NAME = "SeatPostureChangedStream";
	
	@Override
	public TStream<SeatPostureChanged> process(TStream<SeatPostureChanged> stream) {
		return stream;
	}

}

