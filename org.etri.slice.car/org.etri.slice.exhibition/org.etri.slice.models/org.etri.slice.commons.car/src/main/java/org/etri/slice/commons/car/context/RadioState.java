package org.etri.slice.commons.car.context;

import org.etri.slice.commons.SliceContext;
import org.etri.slice.commons.internal.ContextBase;
import org.kie.api.definition.type.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Role(Role.Type.EVENT)
@SliceContext
public class RadioState  implements ContextBase {
	public static class Field {
		public static final String power = "RadioState.power";
		public static final String volume = "RadioState.volume";
		public static final String channel = "RadioState.channel";
	}
	
	public static final String dataType = "org.etri.slice.commons.car.context.RadioState";
	public static final String topic = "radiostate";
	public static final String dataKey = "dataKey:" + dataType;
	
	private boolean power;
	private int volume;
	private int channel;
}
