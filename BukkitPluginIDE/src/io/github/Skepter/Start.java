package io.github.Skepter;

import io.github.Skepter.dialogues.NewProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class Start extends JFrame {
	
	private static final long serialVersionUID = -3185009150138353763L;

	public Start() {
		initialize();
	}
	
	private void initialize() {
		setVisible(true);
		setTitle("Welcome!");
		getContentPane().setBackground(new Color(30, 144, 255));
		setBounds(100, 100, 460, 170);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JButton createNewPlugin = new JButton("Create a new plugin");
		createNewPlugin.setIcon(new ImageIcon(Start.class.getResource("/io/github/Skepter/imageResources/icons/Create.png")));
		createNewPlugin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new MainWindow();
				new NewProject();
			}
		});

		JLabel WelcomeText = new JLabel("<html>Welcome to Bukkit Plugin Maker!<br>To begin, create a new project or open an existing one!</html>");
		WelcomeText.setForeground(new Color(255, 255, 255));
		WelcomeText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		WelcomeText.setVerticalAlignment(SwingConstants.TOP);

		JButton openRecentPlugin = new JButton("Open a recent plugin");
		openRecentPlugin.setIcon(new ImageIcon(Start.class.getResource("/io/github/Skepter/imageResources/icons/Folder.png")));

		JComboBox<String> comboBox = new JComboBox<String>();
		
		JButton learnPlugin = new JButton("Learn how to create plugins");
		learnPlugin.setIcon(new ImageIcon(Start.class.getResource("/io/github/Skepter/imageResources/icons/Help book 3d.png")));
		
		JButton helpButton = new JButton("Help");
		helpButton.setIcon(new ImageIcon(Start.class.getResource("/io/github/Skepter/imageResources/icons/Help symbol.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(WelcomeText, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(learnPlugin)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(createNewPlugin)
							.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
							.addComponent(helpButton))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(openRecentPlugin)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, 0, 265, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(WelcomeText, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(learnPlugin)
						.addComponent(createNewPlugin)
						.addComponent(helpButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(openRecentPlugin)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(38))
		);
		getContentPane().setLayout(groupLayout);
	}
}
