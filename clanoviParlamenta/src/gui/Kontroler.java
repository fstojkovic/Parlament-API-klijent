package gui;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import domain.Poslanik;
import util.ParlamentAPIKomunikacija;

public class Kontroler {

	private static ParlamentGUI glavniProzor;
	private static ParlamentAPIKomunikacija API;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					API = new ParlamentAPIKomunikacija();
					glavniProzor = new ParlamentGUI();
					glavniProzor.setLocationRelativeTo(null);
					glavniProzor.setVisible(true);
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

	public static void sacuvajJson() throws Exception {
		API.sacuvajUFajl();

	}

	public static List<Poslanik> vratiClanoveParlamenta() {
		return API.vratiPoslanike();
	}

	@SuppressWarnings("static-access")
	public static void updateTable(LinkedList<Poslanik> p) throws Exception {
		API.sacuvajUpdate(p);

	}

}
