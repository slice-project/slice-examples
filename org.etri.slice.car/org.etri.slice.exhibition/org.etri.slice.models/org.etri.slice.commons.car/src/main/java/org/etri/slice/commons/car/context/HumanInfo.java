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
public class HumanInfo  implements ContextBase {
	public static class Field {
		public static final String gender = "HumanInfo.gender";
		public static final String age_low = "HumanInfo.age_low";
		public static final String age_high = "HumanInfo.age_high";
		public static final String happiness = "HumanInfo.happiness";
	}
	
	public static final String dataType = "org.etri.slice.commons.car.context.HumanInfo";
	public static final String topic = "humaninfo";
	public static final String dataKey = "dataKey:" + dataType;
	
	private int gender;
	private int age_low;
	private int age_high;
	private double happiness;
}
