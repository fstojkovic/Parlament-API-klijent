package gui;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import domain.Poslanik;
import gui.model.ParlamentTableModel;
import util.ParlamentAPIKomunikacija;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class ParlamentGUI extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnGETMembers;
	private JButton btnFillTable;
	private JButton btnUpdateMembers;

	private JPanel panelJug;
	private JTextArea jtfStatus;
	private JScrollPane scrollPane_1;

	protected ParlamentAPIKomunikacija API;
	private JPanel panelCentar;
	private JTable table;
	private JScrollPane scrollPane;
	private JPopupMenu popupMenu;
	private JMenuItem mntmAbout;

	/**
	 * Create the frame.
	 */
	public ParlamentGUI() {

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Kontroler.exit();
			}
		});

		setTitle("Clanovi parlamenta");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 722, 470);
		API = new ParlamentAPIKomunikacija();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.EAST);
		contentPane.add(getPanelJug(), BorderLayout.SOUTH);
		contentPane.add(getPanelCentar(), BorderLayout.CENTER);

	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(160, 10));
			panel.add(getBtnSacuvajUFajl());
			panel.add(getBtnIspisi());
			panel.add(getBtnUpdate());
		}
		return panel;
	}

	private JButton getBtnSacuvajUFajl() {
		if (btnGETMembers == null) {
			btnGETMembers = new JButton("GET members");
			btnGETMembers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Kontroler.sacuvajJson();
						ispisiStatus("Poslanici su preuzeti sa servisa.");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(contentPane,
								"Greska prilikom upisivanja u fajl." + '\n' + "Proveri da li sajt radi. ", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btnGETMembers.setPreferredSize(new Dimension(140, 25));
		}
		return btnGETMembers;
	}

	private JButton getBtnIspisi() {
		if (btnFillTable == null) {
			btnFillTable = new JButton("Fill table");
			btnFillTable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ParlamentTableModel model = (ParlamentTableModel) (table.getModel());

					List<Poslanik> p = Kontroler.vratiClanoveParlamenta();
					model.ucitajPoslanike(p);
					if (!(p.isEmpty()))
						ispisiStatus("Tabela popunjena podacima preuzetim sa servisa.");

				}
			});
			btnFillTable.setPreferredSize(new Dimension(140, 25));
		}
		return btnFillTable;

	}

	private JButton getBtnUpdate() {
		if (btnUpdateMembers == null) {
			btnUpdateMembers = new JButton("Update members");
			btnUpdateMembers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ParlamentTableModel model = (ParlamentTableModel) (table.getModel());
					try {
						if (model.getPoslanici().isEmpty()) {
							JOptionPane.showMessageDialog(contentPane, "Tabela je prazna!", "Greska",
									JOptionPane.ERROR_MESSAGE);
						} else {
							Kontroler.updateTable((LinkedList<Poslanik>) model.getPoslanici());
							ispisiStatus("Izmenjeni podaci su sacuvani.");
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(contentPane, "Greska prilikom upisivanja u updateFajl.", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btnUpdateMembers.setPreferredSize(new Dimension(140, 25));
		}
		return btnUpdateMembers;
	}

	private JPanel getPanelJug() {
		if (panelJug == null) {
			panelJug = new JPanel();
			panelJug.setPreferredSize(new Dimension(10, 100));
			panelJug.setLayout(new BorderLayout(0, 0));
			panelJug.add(getScrollPane_1());
		}
		return panelJug;
	}

	public JTextArea getJtfStatus() {
		if (jtfStatus == null) {
			jtfStatus = new JTextArea();
			jtfStatus.setBorder(new TitledBorder(null, "STATUS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		}
		return jtfStatus;
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getJtfStatus());
		}
		return scrollPane_1;
	}

	private JPanel getPanelCentar() {
		if (panelCentar == null) {
			panelCentar = new JPanel();
			panelCentar.setLayout(new BorderLayout(0, 0));
			panelCentar.add(getScrollPane_2());
		}
		return panelCentar;
	}

	public JTable getTable_1() {
		if (table == null) {
			table = new JTable();
			table.setModel(new ParlamentTableModel());
			table.setFillsViewportHeight(true);
			addPopup(table, getPopupMenu());
		}
		return table;
	}

	private JScrollPane getScrollPane_2() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable_1());
		}
		return scrollPane;
	}

	public void ispisiStatus(String txt) {
		jtfStatus.setText(txt);
	}

	private JPopupMenu getPopupMenu() {
		if (popupMenu == null) {
			popupMenu = new JPopupMenu();
			popupMenu.add(getMntmAbout());
		}
		return popupMenu;
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private JMenuItem getMntmAbout() {
		if (mntmAbout == null) {
			mntmAbout = new JMenuItem("About");
			mntmAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(contentPane, "Autor: Filip Stojkovic", "Version 1.0",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return mntmAbout;
	}
}
