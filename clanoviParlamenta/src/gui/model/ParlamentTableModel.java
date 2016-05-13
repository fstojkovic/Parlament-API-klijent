package gui.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Poslanik;

public class ParlamentTableModel extends AbstractTableModel {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	private final String[] kolone = new String[] { "ID", "Ime", "Prezime", "Datum rodjenja" };

	private List<Poslanik> poslanici = new LinkedList<Poslanik>();

	public ParlamentTableModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return false;
		case 1:
			return true;
		case 2:
			return true;
		case 3:
			return true;
		default:
			return false;
		}
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public int getRowCount() {
		return poslanici.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Poslanik p = poslanici.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getId();
		case 1:
			return p.getIme();
		case 2:
			return p.getPrezime();
		case 3:
			return sdf.format(p.getDatumRodjenja());
		default:
			return "N/A";
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Poslanik p = poslanici.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return;
		case 1:
			p.setIme((String) aValue);
		case 2:
			p.setPrezime((String) aValue);
			// case 3:
			// p.setDatumRodjenja(Date.parse(aValue));

		}

	}

	@Override
	public String getColumnName(int column) {
		return kolone[column];
	}

	public void ucitajPoslanike(List<Poslanik> poslanici) {
		this.poslanici = poslanici;
		fireTableDataChanged();
	}

	public Poslanik vratiPoslanika(int index) {
		return poslanici.get(index);
	}

	public List<Poslanik> getPoslanici() {
		return poslanici;
	}

}
