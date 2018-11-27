package org.etri.slice.devices.car.mirrorcontroller;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.commons.SliceException;
import org.etri.slice.commons.car.context.SeatPosture;
import org.etri.slice.commons.car.service.MirrorControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hileco.drpc.generic.ServiceConnector;
import com.hileco.drpc.mqtt.MqttDrpcClient;
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder;
import com.kmcrobot.MSM_Algorithm;
import com.kmcrobot.MSM_PT_Property;
import com.kmcrobot.MSM_Point3d;		

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate	
public class MirrorControlService implements MirrorControl {
	
	private static Logger s_logger = LoggerFactory.getLogger(MirrorControlService.class);	
	
	@Property(name="borker", value="tcp://192.168.0.37:1883")
	private String m_broker;
	@Property(name="identifier", value="innosim/left")
	private String m_identifier;
	
	@Property(name="Head2Eye", value="90")
	private double m_head2Eye;
	@Property(name="PanOffset", value="1")
	private int m_panOffset;
	@Property(name="TiltOffset", value="0")
	private int m_tiltOffset;		
	
	private MirrorControl m_service;
	private MSM_Algorithm m_msm = new MSM_Algorithm();
	private double m_torso;
	
	@Validate
	public void init() throws SliceException {
		try {
	        MqttDrpcClient mqttDrpcClient = new MqttDrpcClientBuilder().build(m_broker);
	        mqttDrpcClient.connect();
	        ServiceConnector<MirrorControl> connector = mqttDrpcClient.connector(MirrorControl.class);
	        m_service = connector.connect(m_identifier);
		}
		catch ( Throwable e ) {
			throw new SliceException(e);
		}
		
		s_logger.info("STARTED: " + MirrorControlService.class.getSimpleName());		
	}

	@Invalidate
	public void fini() throws SliceException {
		s_logger.info("STOPPED: " + MirrorControlService.class.getSimpleName());			
	}
	
	@Override
	public double getPan() {
		return m_service.getPan();
	}

	@Override
	public void setPan(double pan) {
		m_service.setPan(pan);
	}

	@Override
	public double getTilt() {
		return m_service.getTilt();
	}

	@Override
	public void setTilt(double tilt) {
		m_service.setTilt(tilt);
	}

	@Override
	public void setSittingHeight(double height) {
		m_torso = height * 10;		
	}

	@Override
	public void adjustPosture(SeatPosture posture) {
		int tilt = (int)posture.getTilt();
		int position = (int)posture.getPosition();
		int height = (int)posture.getHeight();
				
		MSM_Point3d head  = m_msm.Get_Head_Position(m_torso, 90, tilt, position, height);
		s_logger.info("head position(x=" + head.x + ", y=" + head.y + ", z=" + head.z);
		
		m_msm.Set_MSM_PT_Correction(m_panOffset, m_tiltOffset, 0, 0);		
		MSM_PT_Property adjust = m_msm.Get_MSM_PT(head);
		s_logger.info("ADJUST(pan=" + adjust.LM_Panning_P + ", tilt=" + adjust.LM_Tilting_P);
		
		m_service.setPan(adjust.LM_Panning_P);
		m_service.setTilt(adjust.LM_Tilting_P);
	}

}
