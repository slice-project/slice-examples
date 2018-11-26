package org.etri.slice.commons.car.event;

import org.etri.slice.commons.SliceContext;
import org.etri.slice.commons.SliceEvent;
import org.kie.api.definition.type.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.etri.slice.commons.car.context.BodyPartLength;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(callSuper=false)

@Role(Role.Type.EVENT)
@SliceContext
public class FullBodyDetected extends SliceEvent {

	public static final String TOPIC = "full_body_detected";
	private static final long serialVersionUID = -746638784530920553L;
	
	private BodyPartLength bodyLength;
}
