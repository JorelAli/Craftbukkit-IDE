package io.github.Skepter;

import io.github.Skepter.dialogues.ClassUI;
import io.github.Skepter.dialogues.CommandUI;
import io.github.Skepter.dialogues.ProjectUI;
import io.github.Skepter.libs.JTabbedPaneWithCloseIcons;

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
import java.util.ArrayList;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.rsta.ac.java.JavaParser;
import org.fife.rsta.ac.java.tree.JavaOutlineTree;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

public class MainUI extends JFrame {

	// hashmap with tabs - ensure each is saved or not
	// use this to check with JTabbedPaneWithCloseIcons
	public static int tabCount;
	private static final long serialVersionUID = 1L;

	public static JToolBar toolBar;
	public static JComboBox<String> filesComboBox;
	public static JTabbedPaneWithCloseIcons tabbedPane;
	public static Color baseColor;
	private Theme theme;

	public MainUI() {

		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);

		try {
			theme = Theme.load(MainUI.class.getResourceAsStream("/io/github/Skepter/themes/default.xml"));
		} catch (IOException e2) {
		}
		getContentPane().setBackground(baseColor);
		tabCount = 0;

		tabbedPane = new JTabbedPaneWithCloseIcons();
		tabbedPane.setBackground(baseColor);
		tabbedPane.setBorder(null);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// theme.apply(getRSyntaxTextArea());
			}
		});

		/* Nav: RSyntaxTextArea */

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
		textArea.setText("package io.github.Skepter;\nimport org.bukkit.plugin.java.JavaPlugin;\n\npublic class Main extends JavaPlugin {\n\n\t@Override\n\tpublic void onEnable() {\n\t\tgetLogger().info(\"Plugin has started!\");" + "\n\t}\n}");
		textArea.addParser(new JavaParser(textArea));
		ErrorStrip es = new ErrorStrip(textArea);
		cp.add(es, BorderLayout.LINE_END);
		tabbedPane.addTab("Main class", cp);
		
		/* Nav: Setup Console */
		final JPanel panelConsole = new JPanel();
		panelConsole.setBackground(baseColor);
		panelConsole.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Console", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panelConsole = new GroupLayout(panelConsole);
		gl_panelConsole.setHorizontalGroup(gl_panelConsole.createParallelGroup(Alignment.LEADING).addGroup(gl_panelConsole.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE).addContainerGap(10, Short.MAX_VALUE)));
		gl_panelConsole.setVerticalGroup(gl_panelConsole.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_panelConsole.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE).addContainerGap()));
		JTextPane console = new JTextPane();
		scrollPane.setViewportView(console);
		console.setEditable(false);
		new ConsoleManager(console);
		console.setText("[Title] IDE Initialized");
		panelConsole.setLayout(gl_panelConsole);

		/* Nav: Setup files and utilities panels */
		final JPanel panelFiles = new JPanel();
		panelFiles.setBackground(baseColor);
		panelFiles.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Files", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));

		final JPanel panelUtilities = new JPanel();
		panelUtilities.setBackground(baseColor);
		panelUtilities.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Utilities", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));

		toolBar = new JToolBar();
		toolBar.setBackground(baseColor);
		toolBar.setFloatable(false);

		/* Whatever you do, DO NOT REMOVE THIS LINE OF CODE!! */
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addComponent(panelConsole, 0, 0, Short.MAX_VALUE).addComponent(panelFiles, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addComponent(panelUtilities, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED))).addGap(10).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(panelFiles, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panelUtilities, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panelConsole, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)).addContainerGap()));
		getContentPane().setLayout(groupLayout);

		/* Nav: Utilities panel on the left hand side */

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
		JPanel utilitiesTreePanel = new JPanel();
		utilitiesTreePanel.setBackground(new Color(255, 255, 255));
		tabbedPaneUtilities.addTab("Commands", null, utilitiesCommandPanel, null);
		tabbedPaneUtilities.insertTab("Listeners", null, utilitiesListenerPanel, null, 1);
		tabbedPaneUtilities.insertTab("Actions", null, utilitiesActionPanel, null, 2);
		tabbedPaneUtilities.insertTab("Tree", null, utilitiesTreePanel, null, 3);

		//JTree tree = new JTree();
		JavaOutlineTree tree = new JavaOutlineTree();
		tree.listenTo(getRSyntaxTextArea(1));
		GroupLayout gl_utilitiesTreePanel = new GroupLayout(utilitiesTreePanel);
		gl_utilitiesTreePanel.setHorizontalGroup(gl_utilitiesTreePanel.createParallelGroup(Alignment.LEADING).addGroup(gl_utilitiesTreePanel.createSequentialGroup().addGap(9).addComponent(tree, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE).addContainerGap(87, Short.MAX_VALUE)));
		gl_utilitiesTreePanel.setVerticalGroup(gl_utilitiesTreePanel.createParallelGroup(Alignment.LEADING).addGroup(gl_utilitiesTreePanel.createSequentialGroup().addGap(8).addComponent(tree, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		utilitiesTreePanel.setLayout(gl_utilitiesTreePanel);

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

		/* Nav: Begin of JMenuBar */

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(192, 192, 192));
		setJMenuBar(menuBar);

		JMenu menuMain = new JMenu("BukkitPluginIDE");
		menuBar.add(menuMain);
		JMenu mnNewMenu = new JMenu("New");
		mnNewMenu.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/New document.png")));
		menuMain.add(mnNewMenu);

		JMenuItem mntmProject = new JMenuItem("Plugin");
		mntmProject.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Notes.png")));
		mntmProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				new ProjectUI();
			}
		});
		mnNewMenu.add(mntmProject);

		JMenuItem mntmClass = new JMenuItem("Class");
		mntmClass.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Text.png")));
		mntmClass.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new ClassUI();
			}
		});
		mnNewMenu.add(mntmClass);

		JMenuItem mntmOpenProject = new JMenuItem("Open Plugin");
		mntmOpenProject.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Folder.png")));
		menuMain.add(mntmOpenProject);

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
		mntmSave.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Save.png")));
		menuMain.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save as");
		mntmSaveAs.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Save.png")));
		menuMain.add(mntmSaveAs);

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
		mntmExportCraftbukkitPlugin.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Next.png")));
		menuMain.add(mntmExportCraftbukkitPlugin);

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
				new HomeUI();
			}
		});
		mntmGoToHome.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Home.png")));
		menuMain.add(mntmGoToHome);
		mntmClose.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Exit.png")));
		menuMain.add(mntmClose);

		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);

		JMenu mnNewMenu_2 = new JMenu("Search");
		menuEdit.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem = new JMenuItem("Find");
		mntmNewMenuItem.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Find.png")));
		mnNewMenu_2.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Replace");
		mntmNewMenuItem_1.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Bookmark.png")));
		mnNewMenu_2.add(mntmNewMenuItem_1);

		JMenu menuInsert = new JMenu("Insert");
		menuBar.add(menuInsert);

		JMenu mnNewMenu_1 = new JMenu("New");
		mnNewMenu_1.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/New document.png")));
		menuInsert.add(mnNewMenu_1);

		JMenuItem mntmCommand = new JMenuItem("Command");
		mntmCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CommandUI();
			}
		});
		mntmCommand.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/application_xp_terminal.png")));
		mnNewMenu_1.add(mntmCommand);

		JMenuItem mntmListener = new JMenuItem("Listener");
		mntmListener.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Earth.png")));
		mnNewMenu_1.add(mntmListener);

		JMenu menuPreferences = new JMenu("Preferences");
		menuBar.add(menuPreferences);

		JMenuItem mntmChangeColor = new JMenuItem("Change color scheme");
		mntmChangeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				AbstractColorChooserPanel[] panels = chooser.getChooserPanels();
				for (AbstractColorChooserPanel accp : panels) {
					if (accp.getDisplayName().equals("HSL")) {
						JOptionPane.showMessageDialog(null, accp, "Change color scheme", JOptionPane.QUESTION_MESSAGE);
					}
				}
				baseColor = chooser.getColor();
				getContentPane().setBackground(baseColor);
				panelConsole.setBackground(baseColor);
				panelFiles.setBackground(baseColor);
				panelUtilities.setBackground(baseColor);
				toolBar.setBackground(baseColor);
			}
		});
		mntmChangeColor.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Brush.png")));
		menuPreferences.add(mntmChangeColor);

		JMenu mnSyntaxTheme = new JMenu("Syntax theme");
		mnSyntaxTheme.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Magic wand.png")));
		menuPreferences.add(mnSyntaxTheme);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Dark");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					theme = Theme.load(MainUI.class.getResourceAsStream("/io/github/Skepter/themes/dark.xml"));
					for (RSyntaxTextArea r : MainUI.getAllRSyntaxTextArea()) {
						theme.apply(r);
					}
				} catch (IOException e1) {
					ConsoleManager.getManager().error(e1.getCause().toString());
				}
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Magic wand.png")));
		mnSyntaxTheme.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Default");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					theme = Theme.load(MainUI.class.getResourceAsStream("/io/github/Skepter/themes/default.xml"));
					for (RSyntaxTextArea r : MainUI.getAllRSyntaxTextArea()) {
						theme.apply(r);
					}
				} catch (IOException e1) {
					ConsoleManager.getManager().error(e1.getCause().toString());
				}
			}
		});
		mntmNewMenuItem_3.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Magic wand.png")));
		mnSyntaxTheme.add(mntmNewMenuItem_3);

		JMenuItem mntmEclipse = new JMenuItem("Eclipse");
		mntmEclipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					theme = Theme.load(MainUI.class.getResourceAsStream("/io/github/Skepter/themes/eclipse.xml"));
					for (RSyntaxTextArea r : MainUI.getAllRSyntaxTextArea()) {
						theme.apply(r);
					}
				} catch (IOException e1) {
					ConsoleManager.getManager().error(e1.getCause().toString());
				}
			}
		});
		mntmEclipse.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Magic wand.png")));
		mnSyntaxTheme.add(mntmEclipse);

		JMenuItem mntmIdea = new JMenuItem("Idea");
		mntmIdea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					theme = Theme.load(MainUI.class.getResourceAsStream("/io/github/Skepter/themes/idea.xml"));
					for (RSyntaxTextArea r : MainUI.getAllRSyntaxTextArea()) {
						theme.apply(r);
					}
				} catch (IOException e1) {
					ConsoleManager.getManager().error(e1.getCause().toString());
				}
			}
		});
		mntmIdea.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Magic wand.png")));
		mnSyntaxTheme.add(mntmIdea);

		JMenuItem mntmVs = new JMenuItem("Vs");
		mntmVs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					theme = Theme.load(MainUI.class.getResourceAsStream("/io/github/Skepter/themes/vs.xml"));
					for (RSyntaxTextArea r : MainUI.getAllRSyntaxTextArea()) {
						theme.apply(r);
					}
				} catch (IOException e1) {
					ConsoleManager.getManager().error(e1.getCause().toString());
				}
			}
		});
		mntmVs.setIcon(new ImageIcon(MainUI.class.getResource("/io/github/Skepter/imageResources/icons/Magic wand.png")));
		mnSyntaxTheme.add(mntmVs);
		setVisible(true);
	}

	public static void copyToClipboard(String s) {
		StringSelection stringSelection = new StringSelection(s);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	public static void pasteIntoEditor() {
		if (tabbedPane.getSelectedComponent() instanceof JPanel) {
			for (Component component : ((JPanel) tabbedPane.getSelectedComponent()).getComponents()) {
				if (component instanceof JScrollPane) {
					((JScrollPane) component).getViewport().getView();
					Component syntaxArea = ((RTextScrollPane) component).getViewport().getView();
					((RSyntaxTextArea) syntaxArea).insert(getClipboardContents(), ((RSyntaxTextArea) syntaxArea).getCaretPosition());
					break;
				}
			}
		} else if (tabbedPane.getSelectedComponent() instanceof JScrollPane) {
			Component component = ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport().getView();
			((RSyntaxTextArea) component).insert(getClipboardContents(), ((RSyntaxTextArea) component).getCaretPosition());
			RSyntaxTextArea x = (RSyntaxTextArea) component;
			System.out.println(x.getCaretPosition());
		} else {
			return;
		}
	}

	public static RSyntaxTextArea getRSyntaxTextArea(int tab) {
		tab--;
		if (tab < 0 || tab >= tabbedPane.getTabCount())
			return null;
		JPanel temp = (JPanel) tabbedPane.getComponentAt(tab);
		RTextScrollPane pane = (RTextScrollPane) temp.getComponent(0);
		return (RSyntaxTextArea) pane.getViewport().getView();
	}

	public static RSyntaxTextArea[] getAllRSyntaxTextArea() {
		ArrayList<RSyntaxTextArea> a = new ArrayList<RSyntaxTextArea>();
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			a.add(getRSyntaxTextArea(i));
		}
		return (RSyntaxTextArea[]) a.toArray();
	}

	public static String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			} catch (IOException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static CompletionProvider createCompletionProvider() {

		DefaultCompletionProvider provider = new DefaultCompletionProvider();

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

		provider.addCompletion(new ShorthandCompletion(provider, "sysout", "System.out.println(", "System.out.println("));
		provider.addCompletion(new ShorthandCompletion(provider, "syserr", "System.err.println(", "System.err.println("));
		provider.addCompletion(new ShorthandCompletion(provider, "cmd", "@Override\n\tpublic boolean onCommand(CommandSender sender, Command command, String label, String[] args) {\n\t\t\n\t}"));
		// template providers are for filling in gaps: sysout(<FILLME>) & it
		// highlights <FILLME>
		return provider;
	}
}
