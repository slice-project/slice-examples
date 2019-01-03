package org.etri.slice.devices.room.temperaturedetector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.etri.slice.commons.device.DataListener;
import org.etri.slice.commons.room.context.Temperature;

public class TemperatureSensorEmulator {

	private DataListener<Temperature> m_listener;
	private JFrame m_frame;	
	private JTextField m_temp;
	
	public TemperatureSensorEmulator(DataListener<Temperature> listener) {
		m_listener = listener;
		initialize();
	}
	
	public void setVisible(boolean flag) {
		m_frame.setVisible(flag);
	}
	
	private void initialize() {
		m_frame = new JFrame();
		m_frame.setTitle("TemperatureSensor");
		m_frame.setBounds(100, 100, 225, 148);
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.getContentPane().setLayout(null);
		
		JLabel lblTemperature = new JLabel("temperature");
		lblTemperature.setBounds(48, 23, 57, 15);
		m_frame.getContentPane().add(lblTemperature);
		
		m_temp = new JTextField();
		m_temp.setBounds(112, 20, 44, 21);
		m_frame.getContentPane().add(m_temp);
		m_temp.setColumns(10);
		
		JButton btnTemp = new JButton("Temperature");
		btnTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp = Integer.parseInt((String)m_temp.getText().trim());
				Temperature temperature = Temperature.builder().value(temp).build();
				m_listener.dataReceived(temperature);
			}
		});
		btnTemp.setBounds(46, 65, 110, 23);
		m_frame.getContentPane().add(btnTemp);
	}	
}
