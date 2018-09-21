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
public class BodyPartLength  implements ContextBase {
	public static class Field {
		public static final String torso = "BodyPartLength.torso";
		public static final String height = "BodyPartLength.height";
	}
	
	public static final String dataType = "org.etri.slice.commons.car.context.BodyPartLength";
	public static final String topic = "bodypartlength";
	public static final String dataKey = "dataKey:" + dataType;
	
	private double torso;
	private double height;
}
