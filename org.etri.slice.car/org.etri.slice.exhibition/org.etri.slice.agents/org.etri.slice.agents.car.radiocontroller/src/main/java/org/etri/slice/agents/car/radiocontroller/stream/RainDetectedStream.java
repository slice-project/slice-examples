package org.etri.slice.agents.car.radiocontroller.stream;

import org.apache.edgent.topology.TStream;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.etri.slice.api.perception.EventStream;	
import org.etri.slice.commons.car.event.RainDetected;

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate(name=RainDetectedStream.SERVICE_NAME)
public class RainDetectedStream implements EventStream<RainDetected> {

	public static final String SERVICE_NAME = "RainDetectedStream";
	
	@Override
	public TStream<RainDetected> process(TStream<RainDetected> stream) {
		return stream;
	}

}

