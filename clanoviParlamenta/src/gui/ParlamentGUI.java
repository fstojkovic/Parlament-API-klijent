package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import gui.model.ParlamentTableModel;
import util.ParlamentAPIKomunikacija;

public class ParlamentGUI extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnPrebaciUJSON;
	private JButton btnIspuniTabelu;
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

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.EAST);
		contentPane.add(getPanelJug(), BorderLayout.SOUTH);
		contentPane.add(getPanelCentar(), BorderLayout.CENTER);

		// sistem = new Menjacnica();
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			panel.setPreferredSize(new Dimension(160, 10));
			panel.add(getBtnPrebaciUJSON());
			panel.add(getBtnIspuniTabelu());
			panel.add(getBtnUpdate());
		}
		return panel;
	}

	private JButton getBtnPrebaciUJSON() {
		if (btnPrebaciUJSON == null) {
			btnPrebaciUJSON = new JButton("Prebaci u JSON");
			btnPrebaciUJSON.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// prikaziDodajKursGUI();
				}
			});
			btnPrebaciUJSON.setPreferredSize(new Dimension(140, 25));
		}
		return btnPrebaciUJSON;
	}

	private JButton getBtnIspuniTabelu() {
		if (btnIspuniTabelu == null) {
			btnIspuniTabelu = new JButton("Ispuni tabelu");
			btnIspuniTabelu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// obrisiKurs();
				}
			});
			btnIspuniTabelu.setPreferredSize(new Dimension(140, 25));
		}
		return btnIspuniTabelu;

	}

	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton("Update");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// prikaziIzvrsiZamenuGUI();
				}
			});
			btnUpdate.setPreferredSize(new Dimension(140, 25));
		}
		return btnUpdate;
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

	public void osveziTabelu() {
		ParlamentTableModel model = (ParlamentTableModel) (table.getModel());
		try {
			model.ucitajPoslanike(API.vratiPoslanike());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

}
