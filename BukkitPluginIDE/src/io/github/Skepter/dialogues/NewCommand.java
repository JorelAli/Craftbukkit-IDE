package io.github.Skepter.dialogues;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class NewCommand extends JDialog {

	/* Creates a new class */

	private static final long serialVersionUID = 2324713265705322590L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	public NewCommand() {
		setTitle("Insert a new command");
		setType(Type.UTILITY);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Command name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		textField = new JTextField();
		textField.setColumns(10);
		JLabel lblNewLabel_1 = new JLabel("Command arguments:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1 = new JTextField();
		textField_1.setColumns(10);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Player only (doesn't work with console)");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Require Permission?");
		chckbxNewCheckBox_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addComponent(chckbxNewCheckBox_1).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)).addComponent(chckbxNewCheckBox).addGroup(gl_contentPanel.createSequentialGroup().addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel_1).addComponent(lblNewLabel)).addGap(18).addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(textField, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE).addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)))).addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1).addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addComponent(chckbxNewCheckBox).addGap(18).addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(chckbxNewCheckBox_1).addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(65, Short.MAX_VALUE)));
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
