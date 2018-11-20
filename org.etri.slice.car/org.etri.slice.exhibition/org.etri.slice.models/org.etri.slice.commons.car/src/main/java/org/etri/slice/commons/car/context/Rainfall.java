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
public class Rainfall  implements ContextBase {
	public static class Field {
		public static final String value = "Rainfall.value";
	}
	
	public static final String dataType = "org.etri.slice.commons.car.context.Rainfall";
	public static final String topic = "rainfall";
	public static final String dataKey = "dataKey:" + dataType;
	
	private double value;
}
