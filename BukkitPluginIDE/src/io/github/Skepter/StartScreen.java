package io.github.Skepter;

import java.net.MalformedURLException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class StartScreen extends JDialog {

	private static final long serialVersionUID = 5866751799338096044L;
	private static JProgressBar progressBar;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			StartScreen dialog = new StartScreen();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			initUI(dialog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StartScreen() {
		setUndecorated(true);
		setBounds(100, 100, 450, 200);

		progressBar = new JProgressBar();
		JLabel lblNewLabel = new JLabel("Some cool background icon goes here sometime");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(progressBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE).addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)));
		getContentPane().setLayout(groupLayout);
	}

	private static void initUI(final StartScreen dialog) throws MalformedURLException {
		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() throws Exception {
				for (int i = 0; i < 100; i++) {
					Thread.sleep(10);
					publish(i);
				}
				return null;
			}
			@Override
			protected void process(List<Integer> chunks) {
				progressBar.setValue(chunks.get(chunks.size() - 1));
			}
			@Override
			protected void done() {
				dialog.dispose();
				new Start();
			}
		};
		worker.execute();
	}
}
