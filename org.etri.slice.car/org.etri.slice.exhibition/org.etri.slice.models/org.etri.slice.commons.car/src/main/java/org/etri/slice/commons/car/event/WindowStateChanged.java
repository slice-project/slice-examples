package org.etri.slice.commons.car.event;

import org.etri.slice.commons.SliceContext;
import org.etri.slice.commons.SliceEvent;
import org.kie.api.definition.type.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.etri.slice.commons.car.context.WindowState;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(callSuper=false)

@Role(Role.Type.EVENT)
@SliceContext
public class WindowStateChanged extends SliceEvent {

	public static final String TOPIC = "window_state_changed";
	private static final long serialVersionUID = -8413953604224267618L;
	
	private WindowState window;
}
