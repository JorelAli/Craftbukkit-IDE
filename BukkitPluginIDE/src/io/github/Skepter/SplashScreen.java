package io.github.Skepter;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class SplashScreen extends JDialog {

	private static final long serialVersionUID = 5866751799338096044L;
	private static JProgressBar progressBar;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SplashScreen dialog = new SplashScreen();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			initUI(dialog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SplashScreen() {
		getContentPane().setBackground(MainWindow.baseColor);

		setUndecorated(true);
		setBounds(100, 100, 450, 200);
		setLocationRelativeTo(null);
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);

		JPanel panel = new JPanel();
		panel.setBackground(MainWindow.baseColor);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(progressBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE).addComponent(panel, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(panel, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)));

		JLabel icon = new JLabel("");
		JLabel text = new JLabel("<html><b>Bukkit Plugin Maker v0.0.1</b><br>\r\nCreated by nobiths & Skepter<br><br>\r\n[Website link]<br>\r\n[Forums link?]</html>");
		text.setForeground(new Color(255, 255, 255));
		text.setFont(new Font("Tahoma", Font.PLAIN, 18));
		text.setVerticalAlignment(SwingConstants.TOP);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(36).addComponent(icon, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(text, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createSequentialGroup().addContainerGap(35, Short.MAX_VALUE).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addComponent(text, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(icon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)).addGap(29)));
		panel.setLayout(gl_panel);

		BufferedImage img = null;
		try {
			img = ImageIO.read(SplashScreen.class.getResource("/io/github/Skepter/imageResources/icons/Bukkit Logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		icon.setIcon(imageIcon);
		getContentPane().setLayout(groupLayout);
	}

	private static void initUI(final SplashScreen dialog) throws MalformedURLException {
		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() throws Exception {
				for (int i = 0; i < 100; i++) {
					Thread.sleep(50);
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
