package org.etri.slice.commons.car.event;

import org.etri.slice.commons.SliceContext;
import org.etri.slice.commons.SliceEvent;
import org.etri.slice.commons.car.context.BodyPartLength;
import org.etri.slice.commons.car.context.Pressure;
import org.kie.api.definition.type.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(callSuper=false)

@Role(Role.Type.EVENT)
@SliceContext
public class UserSeated extends SliceEvent {

	public static final String TOPIC = "user_seated";
	private static final long serialVersionUID = 8570551909927962262L;
	
	private BodyPartLength bodyLength;
	private Pressure pressure;
}
