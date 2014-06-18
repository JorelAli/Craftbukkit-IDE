package io.github.Skepter.dialogues;

import io.github.Skepter.MainWindow;

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

import java.awt.Color;

public class NewCommand extends JDialog {

	/* Creates a new class */

	private static final long serialVersionUID = 2324713265705322590L;
	private final JPanel contentPanel = new JPanel();
	private JTextField cmdNameBox;
	private JTextField cmdArgsBox;
	private JTextField permBox;

	public NewCommand() {
		setTitle("Insert a new command");
		setType(Type.UTILITY);
		setVisible(true);
		setBounds(100, 100, 450, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(MainWindow.baseColor);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Command name:");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		cmdNameBox = new JTextField();
		cmdNameBox.setColumns(10);
		JLabel lblNewLabel_1 = new JLabel("Command arguments:");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmdArgsBox = new JTextField();
		cmdArgsBox.setEditable(false);
		cmdArgsBox.setEnabled(false);
		cmdArgsBox.setColumns(10);

		final JCheckBox playerOnlyBox = new JCheckBox("Player only (doesn't work with console)");
		playerOnlyBox.setBackground(MainWindow.baseColor);
		playerOnlyBox.setForeground(new Color(255, 255, 255));
		playerOnlyBox.setFont(new Font("Tahoma", Font.PLAIN, 14));

		final JCheckBox requirePermBox = new JCheckBox("Require Permission?");
		requirePermBox.setBackground(MainWindow.baseColor);
		requirePermBox.setForeground(new Color(255, 255, 255));
		requirePermBox.setFont(new Font("Tahoma", Font.PLAIN, 14));

		permBox = new JTextField();
		permBox.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addComponent(requirePermBox).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(permBox, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)).addComponent(playerOnlyBox).addGroup(gl_contentPanel.createSequentialGroup().addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel_1).addComponent(lblNewLabel)).addGap(18).addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(cmdNameBox, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE).addComponent(cmdArgsBox, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)))).addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(cmdNameBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1).addComponent(cmdArgsBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addComponent(playerOnlyBox).addGap(18).addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(requirePermBox).addComponent(permBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(65, Short.MAX_VALUE)));
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(MainWindow.baseColor);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						StringBuilder b = new StringBuilder("@Override\n\tpublic boolean onCommand(CommandSender sender, Command command, String label, String[] args) {\n\t\tif(command.getName().equalsIgnoreCase(" + cmdNameBox.getText() + ")");
						if (requirePermBox.isSelected()) {
							b.append(" && sender.hasPermission(" + permBox.getText() + ")");
						}
						if (playerOnlyBox.isSelected()) {
							b.append(" && sender instanceof Player");
						}
						b.append(") {\n\t\t\t\n\t\t}\n\t}");
						MainWindow.copyToClipboard(b.toString());
						MainWindow.pasteIntoEditor();
						dispose();
					}
				});
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
