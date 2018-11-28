/**
 *
 * Copyright (c) 2017-2018 SLICE project team (yhsuh@etri.re.kr, woosungpil@etri.re.kr)
 * http://slice.etri.re.kr
 *
 * This file is part of The SLICE components
 *
 * This Program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This Program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with The SLICE components; see the file COPYING.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.etri.slice.devices.room.temperaturedetector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.felix.ipojo.handlers.event.publisher.Publisher;
import org.etri.slice.commons.room.context.Temperature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TemperatureSensorGUI {
	
	private static Logger s_logger = LoggerFactory.getLogger(TemperatureSensorGUI.class);	

	private Publisher m_publisher;
	public JFrame m_frame;	
	private JTextField m_temp;
	
	public TemperatureSensorGUI(Publisher publisher) {
		m_publisher = publisher;
		initialize();
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
				m_publisher.sendData(temperature);
				
				s_logger.info("PUB: " + temperature);				
			}
		});
		btnTemp.setBounds(46, 65, 110, 23);
		m_frame.getContentPane().add(btnTemp);
	}
}