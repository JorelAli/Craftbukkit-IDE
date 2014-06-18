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
		setTitle("Start page");
		getContentPane().setBackground(MainWindow.baseColor);
		setBounds(100, 100, 530, 218);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JButton createNewPlugin = new JButton("Create a new plugin");
		createNewPlugin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		createNewPlugin.setIcon(new ImageIcon(Start.class.getResource("/io/github/Skepter/imageResources/icons/Create.png")));
		createNewPlugin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new MainWindow();
				new NewProject();
			}
		});

		JLabel WelcomeText = new JLabel("<html><center>Welcome to the Bukkit Plugin Maker!<br>To get started, click Create a new plugin</center></html>");
		WelcomeText.setHorizontalAlignment(SwingConstants.CENTER);
		WelcomeText.setForeground(new Color(255, 255, 255));
		WelcomeText.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JButton openRecentPlugin = new JButton("Open a recent plugin");
		openRecentPlugin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		openRecentPlugin.setIcon(new ImageIcon(Start.class.getResource("/io/github/Skepter/imageResources/icons/Folder.png")));

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton learnPlugin = new JButton("Learn how to create plugins");
		learnPlugin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		learnPlugin.setIcon(new ImageIcon(Start.class.getResource("/io/github/Skepter/imageResources/icons/Help book 3d.png")));
		
		JButton helpButton = new JButton("Help");
		helpButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		helpButton.setIcon(new ImageIcon(Start.class.getResource("/io/github/Skepter/imageResources/icons/Help symbol.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(WelcomeText, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(openRecentPlugin, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox, 0, 263, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(learnPlugin)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(createNewPlugin)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(helpButton, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(WelcomeText, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(learnPlugin, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(createNewPlugin, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(helpButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(openRecentPlugin, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
					.addGap(161))
		);
		getContentPane().setLayout(groupLayout);
	}
}
