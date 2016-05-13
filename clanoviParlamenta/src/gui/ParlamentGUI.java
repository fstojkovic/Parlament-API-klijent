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

public class ParlamentGUI extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnSacuvajUFajl;
	private JButton btnIspisi;
	private JButton btnUpdate;

	private JPanel panelJug;
	private JTextArea jtfStatus;
	private JScrollPane scrollPane_1;

	protected ParlamentAPIKomunikacija API;
	private JPanel panelCentar;
	private JTable table;
	private JScrollPane scrollPane;

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
		if (btnSacuvajUFajl == null) {
			btnSacuvajUFajl = new JButton("Sacuvaj u fajl");
			btnSacuvajUFajl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Kontroler.sacuvajJson();
						ispisiStatus("Poslanici su preuzeti sa servisa.");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(contentPane, "Greska prilikom upisivanja u fajl." + '\n' + "Proveri da li sajt radi. ", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btnSacuvajUFajl.setPreferredSize(new Dimension(140, 25));
		}
		return btnSacuvajUFajl;
	}

	private JButton getBtnIspisi() {
		if (btnIspisi == null) {
			btnIspisi = new JButton("Ispisi");
			btnIspisi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ParlamentTableModel model = (ParlamentTableModel) (table.getModel());

					List<Poslanik> p = Kontroler.vratiClanoveParlamenta();
					model.ucitajPoslanike(p);
					if (!(p.isEmpty()))
						ispisiStatus("Tabela popunjena podacima preuzetim sa servisa.");

				}
			});
			btnIspisi.setPreferredSize(new Dimension(140, 25));
		}
		return btnIspisi;

	}

	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton("Update");
			btnUpdate.addActionListener(new ActionListener() {
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
			btnUpdate.setPreferredSize(new Dimension(140, 25));
		}
		return btnUpdate;
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
}
