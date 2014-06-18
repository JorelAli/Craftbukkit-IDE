package io.github.Skepter;

import javax.swing.JTextPane;

public class ConsoleManager {

	private static ConsoleManager instance;
	private JTextPane console;
	
	public ConsoleManager(JTextPane console) {
		instance = this;
		this.console = console;
	}
	
	public static ConsoleManager getManager() {
		return instance;
	}
	
	public void log(String s) {
		console.setText(console.getText() + "\n[Bukkit Plugin Maker] " + s);
	}
}
