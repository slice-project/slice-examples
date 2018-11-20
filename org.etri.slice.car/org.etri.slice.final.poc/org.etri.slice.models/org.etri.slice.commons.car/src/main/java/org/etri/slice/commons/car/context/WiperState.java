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
public class WiperState  implements ContextBase {
	public static class Field {
		public static final String power = "WiperState.power";
		public static final String speed = "WiperState.speed";
	}
	
	public static final String dataType = "org.etri.slice.commons.car.context.WiperState";
	public static final String topic = "wiperstate";
	public static final String dataKey = "dataKey:" + dataType;
	
	private boolean power;
	private int speed;
}
