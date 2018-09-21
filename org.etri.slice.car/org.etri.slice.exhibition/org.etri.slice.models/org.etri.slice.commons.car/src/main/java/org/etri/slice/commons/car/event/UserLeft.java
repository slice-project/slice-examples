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
public class UserLeft extends SliceEvent {

	private static final long serialVersionUID = 1359510266109962927L;
	public static final String TOPIC = "user_left";
	
	private BodyPartLength bodyLength;
}
