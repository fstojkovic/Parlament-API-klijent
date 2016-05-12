package domain;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Poslanik {

	// @SerializedName("id")
	private int id;
	// @SerializedName("name")
	private String ime;
	// @SerializedName("lastName")
	private String prezime;
	// @SerializedName("birthDate")
	private Date datumRodjenja;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", datumRodjenja=" + datumRodjenja + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poslanik other = (Poslanik) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
