package io.github.Skepter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class Start {
	
	public Start() {
		initialize();
	}
	
	private void initialize() {
		final JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("Welcome!");
		frame.getContentPane().setBackground(new Color(30, 144, 255));
		frame.setBounds(100, 100, 450, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton createNewPlugin = new JButton("Create a new plugin");
		createNewPlugin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new MainWindow();
			}
		});

		JLabel WelcomeText = new JLabel("<html>Welcome to PluginMaker!<br>To begin, create a new project or open an existing one!</html>");
		WelcomeText.setForeground(new Color(255, 255, 255));
		WelcomeText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		WelcomeText.setVerticalAlignment(SwingConstants.TOP);

		JButton openRecentPlugin = new JButton("Open a recent plugin");

		JComboBox<String> comboBox = new JComboBox<String>();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(WelcomeText, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addComponent(openRecentPlugin).addGap(18).addComponent(comboBox, 0, 287, Short.MAX_VALUE)).addComponent(createNewPlugin)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addContainerGap().addComponent(WelcomeText, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(openRecentPlugin)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(createNewPlugin).addContainerGap()));
		frame.getContentPane().setLayout(groupLayout);
	}
}
