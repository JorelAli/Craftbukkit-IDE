package io.github.Skepter.dialogues;

import io.github.Skepter.MainUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

public class ProjectUI extends JDialog {

	private static final long serialVersionUID = -3464158322822086379L;
	private final JPanel contentPanel = new JPanel();
	private JTextField pluginName;
	private JTextField packageName;
	private JTextField authors;
	private JTextField pluginDescription;
	
	public ProjectUI() {
		setType(Type.UTILITY);
		setTitle("Create a new plugin");
		setBounds(100, 100, 450, 305);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(MainUI.baseColor);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Plugin name:");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		pluginName = new JTextField();
		pluginName.setColumns(10);

		JLabel lblPackageName = new JLabel("Package name:");
		lblPackageName.setForeground(new Color(255, 255, 255));
		lblPackageName.setFont(new Font("Tahoma", Font.PLAIN, 14));

		packageName = new JTextField();
		packageName.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Craftbukkit version:");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("CraftBukkit 1.7.2");
		comboBox.addItem("CraftBukkit 1.7.5");
		comboBox.addItem("CraftBukkit 1.7.9");
		comboBox.addItem("CraftBukkit 1.8");
		
		JLabel lblNewLabel_2 = new JLabel("Author(s):");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		authors = new JTextField();
		authors.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Plugin description:");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));

		pluginDescription = new JTextField();
		pluginDescription.setColumns(10);

		JCheckBox boolConfig = new JCheckBox("Requires Config");
		boolConfig.setBackground(MainUI.baseColor);

		JCheckBox boolAdvancedPlugin = new JCheckBox("Advanced plugin");
		boolAdvancedPlugin.setBackground(MainUI.baseColor);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_2)
								.addComponent(lblNewLabel)
								.addComponent(lblPackageName)
								.addComponent(lblNewLabel_3))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(pluginDescription, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
								.addComponent(pluginName, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
								.addComponent(packageName, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
								.addComponent(authors, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
								.addComponent(comboBox, 0, 266, Short.MAX_VALUE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(boolAdvancedPlugin)
							.addGap(18)
							.addComponent(boolConfig)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(pluginName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPackageName)
						.addComponent(packageName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2)
						.addComponent(authors, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3)
						.addComponent(pluginDescription, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(boolConfig)
						.addComponent(boolAdvancedPlugin))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(MainUI.baseColor);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton helpButton = new JButton("Help");
			helpButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.", "Help", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			buttonPane.add(helpButton);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
