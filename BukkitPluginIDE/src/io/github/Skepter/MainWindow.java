package io.github.Skepter;

import io.github.Skepter.dialogues.NewClass;
import io.github.Skepter.dialogues.NewCommand;
import io.github.Skepter.dialogues.NewProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.autocomplete.VariableCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class MainWindow extends JFrame {

	// hashmap with tabs - ensure each is saved or not
	// use this to check with JTabbedPaneWithCloseIcons
	public static int tabCount;
	private static final long serialVersionUID = 1L;

	public static JToolBar toolBar;
	public static JComboBox<String> filesComboBox;
	public static JTabbedPaneWithCloseIcons tabbedPane;
	public static Color baseColor = new Color(30, 144, 255);

	public MainWindow() {
		setVisible(true);
		getContentPane().setBackground(baseColor);
		tabCount = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);
		tabbedPane = new JTabbedPaneWithCloseIcons();
		tabbedPane.setBackground(baseColor);
		tabbedPane.setBorder(null);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		final JPanel panelConsole = new JPanel();
		panelConsole.setBackground(baseColor);
		panelConsole.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Console", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));

		final JPanel panelFiles = new JPanel();
		panelFiles.setBackground(baseColor);
		panelFiles.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Files", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));

		final JPanel panelUtilities = new JPanel();
		panelUtilities.setBackground(baseColor);
		panelUtilities.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Utilities", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));

		//coming Soon!
		//Adding functions here
		toolBar = new JToolBar();
		toolBar.setBackground(baseColor);
		toolBar.setFloatable(false);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(toolBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addComponent(panelConsole, 0, 0, Short.MAX_VALUE).addComponent(panelFiles, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addComponent(panelUtilities, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED))).addGap(10).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(tabbedPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addComponent(panelFiles, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panelUtilities, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panelConsole, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))).addContainerGap()));

		JTabbedPane tabbedPaneUtilities = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_panelUtilities = new GroupLayout(panelUtilities);
		gl_panelUtilities.setHorizontalGroup(gl_panelUtilities.createParallelGroup(Alignment.LEADING).addGroup(gl_panelUtilities.createSequentialGroup().addContainerGap().addComponent(tabbedPaneUtilities, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE).addContainerGap()));
		gl_panelUtilities.setVerticalGroup(gl_panelUtilities.createParallelGroup(Alignment.LEADING).addGroup(gl_panelUtilities.createSequentialGroup().addContainerGap().addComponent(tabbedPaneUtilities, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE).addContainerGap()));

		JPanel utilitiesCommandPanel = new JPanel();
		utilitiesCommandPanel.setBackground(new Color(255, 255, 255));
		JPanel utilitiesListenerPanel = new JPanel();
		utilitiesListenerPanel.setBackground(new Color(255, 255, 255));
		JPanel utilitiesActionPanel = new JPanel();
		utilitiesActionPanel.setBackground(new Color(255, 255, 255));
		tabbedPaneUtilities.addTab("Commands", null, utilitiesCommandPanel, null);
		tabbedPaneUtilities.insertTab("Listeners", null, utilitiesListenerPanel, null, 1);
		tabbedPaneUtilities.insertTab("Actions", null, utilitiesActionPanel, null, 2);

		JLabel lblNewLabel = new JLabel("Player actions:");

		JComboBox<String> playerActions = new JComboBox<String>();
		playerActions.addItem("Make the player say something in chat");
		playerActions.addItem("Hurt the player");
		playerActions.addItem("Check if the player has played before");
		playerActions.addItem("Set the player's display username");
		playerActions.addItem("Set the player's health");
		playerActions.addItem("Set the player's food level");
		playerActions.addItem("Allow the player to fly");
		playerActions.addItem("Set the player's status to OP");
		playerActions.addItem("Get the player's location");
		playerActions.addItem("LOTS more coming soon!");

		playerActions.addItem("LOTS more coming soon!");

		// org.bukkit.entity.Player p = null;
		/*
		 * PotionEffect Chat Damage HasPlayedBefore SetFly SetDisplayName
		 * FlySpeed FoodLevel WalkSpeed SetOP SetPTime SetPWeather getItemInHand
		 * getLocation getWorld?
		 */

		JComboBox<String> worldActions = new JComboBox<String>();
		/*
		 * Generate Explosion Spawn Tree Drop Item SpawnEntity setSpawn getSpawn
		 * PlayEffect
		 */

		JLabel lblNewLabel_1 = new JLabel("World actions:");
		GroupLayout gl_utilitiesActionPanel = new GroupLayout(utilitiesActionPanel);
		gl_utilitiesActionPanel.setHorizontalGroup(gl_utilitiesActionPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_utilitiesActionPanel.createSequentialGroup().addContainerGap().addGroup(gl_utilitiesActionPanel.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel_1).addComponent(lblNewLabel)).addGap(10).addGroup(gl_utilitiesActionPanel.createParallelGroup(Alignment.LEADING).addComponent(worldActions, Alignment.TRAILING, 0, 94, Short.MAX_VALUE).addComponent(playerActions, Alignment.TRAILING, 0, 94, Short.MAX_VALUE)).addContainerGap()));
		gl_utilitiesActionPanel.setVerticalGroup(gl_utilitiesActionPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_utilitiesActionPanel.createSequentialGroup().addContainerGap().addGroup(gl_utilitiesActionPanel.createParallelGroup(Alignment.BASELINE).addComponent(playerActions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblNewLabel)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_utilitiesActionPanel.createParallelGroup(Alignment.BASELINE).addComponent(worldActions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblNewLabel_1)).addContainerGap(214, Short.MAX_VALUE)));
		utilitiesActionPanel.setLayout(gl_utilitiesActionPanel);
		GroupLayout gl_utilitiesListenerPanel = new GroupLayout(utilitiesListenerPanel);
		gl_utilitiesListenerPanel.setHorizontalGroup(gl_utilitiesListenerPanel.createParallelGroup(Alignment.LEADING).addGap(0, 195, Short.MAX_VALUE));
		gl_utilitiesListenerPanel.setVerticalGroup(gl_utilitiesListenerPanel.createParallelGroup(Alignment.LEADING).addGap(0, 271, Short.MAX_VALUE));
		utilitiesListenerPanel.setLayout(gl_utilitiesListenerPanel);

		JButton btnNewButton = new JButton("Insert Player");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		GroupLayout gl_utilitiesCommandPanel = new GroupLayout(utilitiesCommandPanel);
		gl_utilitiesCommandPanel.setHorizontalGroup(gl_utilitiesCommandPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_utilitiesCommandPanel.createSequentialGroup().addContainerGap().addComponent(btnNewButton).addContainerGap(95, Short.MAX_VALUE)));
		gl_utilitiesCommandPanel.setVerticalGroup(gl_utilitiesCommandPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_utilitiesCommandPanel.createSequentialGroup().addContainerGap().addComponent(btnNewButton).addContainerGap(237, Short.MAX_VALUE)));
		utilitiesCommandPanel.setLayout(gl_utilitiesCommandPanel);
		panelUtilities.setLayout(gl_panelUtilities);

		filesComboBox = new JComboBox<String>();
		filesComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					e.getItem();

				}
			}
		});
		filesComboBox.addItem("Main.class");
		GroupLayout gl_panelFiles = new GroupLayout(panelFiles);
		gl_panelFiles.setHorizontalGroup(gl_panelFiles.createParallelGroup(Alignment.LEADING).addGroup(gl_panelFiles.createSequentialGroup().addContainerGap().addComponent(filesComboBox, 0, 188, Short.MAX_VALUE).addContainerGap()));
		gl_panelFiles.setVerticalGroup(gl_panelFiles.createParallelGroup(Alignment.LEADING).addGroup(gl_panelFiles.createSequentialGroup().addComponent(filesComboBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addContainerGap(25, Short.MAX_VALUE)));
		panelFiles.setLayout(gl_panelFiles);

		JTextPane console = new JTextPane();
		console.setEditable(false);
		new ConsoleManager(console);
		console.setText("[Title] IDE Initialized");
		GroupLayout gl_panelConsole = new GroupLayout(panelConsole);
		gl_panelConsole.setHorizontalGroup(gl_panelConsole.createParallelGroup(Alignment.LEADING).addGroup(gl_panelConsole.createSequentialGroup().addContainerGap().addComponent(console, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE).addContainerGap()));
		gl_panelConsole.setVerticalGroup(gl_panelConsole.createParallelGroup(Alignment.LEADING).addGroup(gl_panelConsole.createSequentialGroup().addComponent(console, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE).addContainerGap()));
		panelConsole.setLayout(gl_panelConsole);
		getContentPane().setLayout(groupLayout);
		JPanel cp = new JPanel(new BorderLayout());
		RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(textArea);
		sp.setFoldIndicatorEnabled(true);
		cp.add(sp);

		CompletionProvider provider = createCompletionProvider();

		AutoCompletion ac = new AutoCompletion(provider);
		ac.install(textArea);
		textArea.setText("package io.github.Skepter;\n\npublic class Main extends JavaPlugin {\n\n\t@Override\n\tpublic void onEnable() {\n\t\tgetLogger().info(\"Plugin has started!\");" + "\n\t}\n}");

		tabbedPane.addTab("Main class", cp);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(192, 192, 192));
		setJMenuBar(menuBar);

		JMenu mnCbide = new JMenu("BukkitPluginIDE");
		menuBar.add(mnCbide);
		JMenu mnNewMenu = new JMenu("New");
		mnNewMenu.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/New document.png")));
		mnCbide.add(mnNewMenu);

		JMenuItem mntmProject = new JMenuItem("Plugin");
		mntmProject.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Notes.png")));
		mntmProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new NewProject();
			}
		});
		mnNewMenu.add(mntmProject);

		JMenuItem mntmClass = new JMenuItem("Class");
		mntmClass.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Text.png")));
		mntmClass.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new NewClass();
			}
		});
		mnNewMenu.add(mntmClass);

		JMenuItem mntmOpenProject = new JMenuItem("Open Plugin");
		mntmOpenProject.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Folder.png")));
		mnCbide.add(mntmOpenProject);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Java Files", "java"));
				fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
				fileChooser.showSaveDialog(null);
				// If the file selection has been cancelled - return
				Component component = ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport().getView();
				String content = ((JTextArea) component).getText();
				int retrival = fileChooser.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					try {
						FileWriter fw = new FileWriter(fileChooser.getSelectedFile() + ".java");
						fw.write(content);
						fw.flush();
						fw.close();
						ConsoleManager.getManager().log("Successfully saved file " + fileChooser.getSelectedFile());
					} catch (Exception ex) {
						ConsoleManager.getManager().log("An error occured when saving the file!");
						// ex.printStackTrace();
					}
				}
			}
		});
		mntmSave.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Save.png")));
		mnCbide.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save as");
		mntmSaveAs.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Save.png")));
		mnCbide.add(mntmSaveAs);

		JMenuItem mntmExportCraftbukkitPlugin = new JMenuItem("Export CraftBukkit Plugin");
		mntmExportCraftbukkitPlugin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileFilter(new FileNameExtensionFilter("Java Files", "java"));
					fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
					// fileChooser.setMultiSelectionEnabled(true);
					fileChooser.showOpenDialog(null);
					Runtime.getRuntime().exec("javac " + fileChooser.getSelectedFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mntmExportCraftbukkitPlugin.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Next.png")));
		mnCbide.add(mntmExportCraftbukkitPlugin);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
		
		JMenuItem mntmGoToHome = new JMenuItem("Go to start window");
		mntmGoToHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Start();
			}
		});
		mntmGoToHome.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Home.png")));
		mnCbide.add(mntmGoToHome);
		mntmClose.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Exit.png")));
		mnCbide.add(mntmClose);

		JMenu menuInsert = new JMenu("Insert");
		menuBar.add(menuInsert);

		JMenu mnNewMenu_1 = new JMenu("New");
		mnNewMenu_1.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/New document.png")));
		menuInsert.add(mnNewMenu_1);

		JMenuItem mntmCommand = new JMenuItem("Command");
		mntmCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewCommand();
			}
		});
		mntmCommand.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/application_xp_terminal.png")));
		mnNewMenu_1.add(mntmCommand);

		JMenuItem mntmListener = new JMenuItem("Listener");
		mntmListener.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Earth.png")));
		mnNewMenu_1.add(mntmListener);

		JMenu preferencedMenu = new JMenu("Preferences");
		menuBar.add(preferencedMenu);

		JMenuItem mntmChangeColor = new JMenuItem("Change color scheme");
		mntmChangeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				baseColor = JColorChooser.showDialog(null, "Choose a color scheme", baseColor);
				getContentPane().setBackground(baseColor);
				panelConsole.setBackground(baseColor);
				panelFiles.setBackground(baseColor);
				panelUtilities.setBackground(baseColor);
				toolBar.setBackground(baseColor);
			}
		});
		mntmChangeColor.setIcon(new ImageIcon(MainWindow.class.getResource("/io/github/Skepter/imageResources/icons/Brush.png")));
		preferencedMenu.add(mntmChangeColor);
	}

	public static void copyToClipboard(String s) {
		StringSelection stringSelection = new StringSelection(s);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	public static void pasteIntoEditor() {
		System.out.println(tabbedPane.getSelectedComponent().getName());
		if(tabbedPane.getSelectedComponent() instanceof JPanel) {
			for(Component component : ((JPanel) tabbedPane.getSelectedComponent()).getComponents()) {
				if(component instanceof JScrollPane) {
					((JScrollPane) component).getViewport().getView();
					Component syntaxArea = ((RTextScrollPane) component).getViewport().getView();
					((RSyntaxTextArea) syntaxArea).insert(getClipboardContents(), ((RSyntaxTextArea) syntaxArea).getCaretPosition());
					break;
				}
			}
		} else if(tabbedPane.getSelectedComponent() instanceof JScrollPane) {
			Component component = ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport().getView();
			((RSyntaxTextArea) component).insert(getClipboardContents(), ((RSyntaxTextArea) component).getCaretPosition());
			RSyntaxTextArea x = (RSyntaxTextArea) component;
			System.out.println(x.getCaretPosition());
		} else {
			return;
		}
	}
	
	public static String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException ex) {
				// highly unlikely since we are using a standard DataFlavor
				System.out.println(ex);
				ex.printStackTrace();
			} catch (IOException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Create a simple provider that adds some Java-related completions.
	 * 
	 * @return The completion provider.
	 */
	public static CompletionProvider createCompletionProvider() {

		// A DefaultCompletionProvider is the simplest concrete implementation
		// of CompletionProvider. This provider has no understanding of
		// language semantics. It simply checks the text entered up to the
		// caret position for a match against known completions. This is all
		// that is needed in the majority of cases.
		DefaultCompletionProvider provider = new DefaultCompletionProvider();

		// Add completions for all Java keywords. A BasicCompletion is just
		// a straightforward word completion.
		provider.addCompletion(new BasicCompletion(provider, "abstract"));
		provider.addCompletion(new BasicCompletion(provider, "assert"));
		provider.addCompletion(new BasicCompletion(provider, "break"));
		provider.addCompletion(new BasicCompletion(provider, "case"));
		provider.addCompletion(new BasicCompletion(provider, "catch"));
		provider.addCompletion(new BasicCompletion(provider, "class"));
		provider.addCompletion(new BasicCompletion(provider, "const"));
		provider.addCompletion(new BasicCompletion(provider, "continue"));
		provider.addCompletion(new BasicCompletion(provider, "default"));
		provider.addCompletion(new BasicCompletion(provider, "do"));
		provider.addCompletion(new BasicCompletion(provider, "else"));
		provider.addCompletion(new BasicCompletion(provider, "enum"));
		provider.addCompletion(new BasicCompletion(provider, "extends"));
		provider.addCompletion(new BasicCompletion(provider, "final"));
		provider.addCompletion(new BasicCompletion(provider, "finally"));
		provider.addCompletion(new BasicCompletion(provider, "for"));
		provider.addCompletion(new BasicCompletion(provider, "goto"));
		provider.addCompletion(new BasicCompletion(provider, "if"));
		provider.addCompletion(new BasicCompletion(provider, "implements"));
		provider.addCompletion(new BasicCompletion(provider, "import"));
		provider.addCompletion(new BasicCompletion(provider, "instanceof"));
		provider.addCompletion(new BasicCompletion(provider, "interface"));
		provider.addCompletion(new BasicCompletion(provider, "native"));
		provider.addCompletion(new BasicCompletion(provider, "new"));
		provider.addCompletion(new BasicCompletion(provider, "package"));
		provider.addCompletion(new BasicCompletion(provider, "private"));
		provider.addCompletion(new BasicCompletion(provider, "protected"));
		provider.addCompletion(new BasicCompletion(provider, "public"));
		provider.addCompletion(new BasicCompletion(provider, "return"));
		provider.addCompletion(new BasicCompletion(provider, "static"));
		provider.addCompletion(new BasicCompletion(provider, "strictfp"));
		provider.addCompletion(new BasicCompletion(provider, "super"));
		provider.addCompletion(new BasicCompletion(provider, "switch"));
		provider.addCompletion(new BasicCompletion(provider, "synchronized"));
		provider.addCompletion(new BasicCompletion(provider, "this"));
		provider.addCompletion(new BasicCompletion(provider, "throw"));
		provider.addCompletion(new BasicCompletion(provider, "throws"));
		provider.addCompletion(new BasicCompletion(provider, "transient"));
		provider.addCompletion(new BasicCompletion(provider, "try"));
		provider.addCompletion(new BasicCompletion(provider, "void"));
		provider.addCompletion(new BasicCompletion(provider, "volatile"));
		provider.addCompletion(new BasicCompletion(provider, "while"));

		// Add a couple of "shorthand" completions. These completions don't
		// require the input text to be the same thing as the replacement text.
		provider.addCompletion(new ShorthandCompletion(provider, "sysout", "System.out.println(", "System.out.println("));
		provider.addCompletion(new ShorthandCompletion(provider, "syserr", "System.err.println(", "System.err.println("));
		provider.addCompletion(new ShorthandCompletion(provider, "cmd", "@Override\n\tpublic boolean onCommand(CommandSender sender, Command command, String label, String[] args) {\n\t\t\n\t}"));
		provider.addCompletion(new VariableCompletion(provider, "text", "hello!"));
		return provider;

	}
}
