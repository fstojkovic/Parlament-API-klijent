package gui;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

public class Kontroler {

	private static ParlamentGUI glavniProzor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParlamentGUI frame = new ParlamentGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void exit() {
		int izbor = JOptionPane.showConfirmDialog(glavniProzor, "Da li zelite da izadjete iz programa?", "Exit",
				JOptionPane.YES_NO_OPTION);
		if (izbor == JOptionPane.YES_OPTION) {
			System.exit(0);
		}

	}

}
