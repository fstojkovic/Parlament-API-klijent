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

	public static void sacuvajJson() throws Exception {
		API.ucitajUFajl();

	}

//	public static List<Poslanik> vratiClanoveParlamenta() throws Exception {
//		return API.vratiPoslanike();
//	}

	
	
}
