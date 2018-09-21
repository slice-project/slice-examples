package org.etri.slice.commons.car.event;

import org.etri.slice.commons.SliceContext;
import org.etri.slice.commons.SliceEvent;
import org.kie.api.definition.type.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.etri.slice.commons.car.context.HumanInfo;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(callSuper=false)

@Role(Role.Type.EVENT)
@SliceContext
public class FaceDetected extends SliceEvent {

	public static final String TOPIC = "face_detected";
	private static final long serialVersionUID = 5986389655847848603L;
	
	private HumanInfo human;
}
