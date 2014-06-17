package io.github.Skepter.dialogues;

import io.github.Skepter.ConsoleManager;
import io.github.Skepter.MainWindow;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.EditorKit;

import jsyntaxpane.DefaultSyntaxKit;
import jsyntaxpane.syntaxkits.JavaSyntaxKit;

public class NewClass extends JDialog {
	private static final long serialVersionUID = -5094872991274960527L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	public NewClass() {
		setType(Type.UTILITY);
		setTitle("Create a new class");
		setVisible(true);
		setBounds(100, 100, 450, 142);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblNewLabel = new JLabel("Please enter the name of the class you would like to create");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel className = new JLabel("Class name:");
		className.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField = new JTextField();
		textField.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE).addGroup(gl_contentPanel.createSequentialGroup().addComponent(className).addGap(18).addComponent(textField, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))).addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblNewLabel).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(className)).addGap(223)));
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MainWindow.filesComboBox.addItem(textField.getText());
						JEditorPane p = new JEditorPane();
						new JScrollPane(p);
						jsyntaxpane.DefaultSyntaxKit.initKit();
						p.setEditorKit(new JavaSyntaxKit());
						EditorKit kit = p.getEditorKit();
								MainWindow.toolBar.removeAll();
						if (kit instanceof DefaultSyntaxKit) {
							DefaultSyntaxKit defaultSyntaxKit = (DefaultSyntaxKit) kit;
							defaultSyntaxKit.addToolBarActions(p, MainWindow.toolBar);
						}
						MainWindow.toolBar.validate();
						p.setContentType("text/java");
						MainWindow.tabCount++;
						MainWindow.tabbedPane.addTab(textField.getText(), null);
//						MainWindow.tabbedPane.addTab("New class - " + MainWindow.tabCount, null);
						MainWindow.tabbedPane.setSelectedIndex(MainWindow.tabCount);
						dispose();
						ConsoleManager.getManager().log("Created a new class: " + textField.getText());
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
