package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Poslanik;
import gui.ParlamentGUI;

public class ParlamentAPIKomunikacija {

	private static final String membersURL = "http://147.91.128.71:9090/parlament/api/members?limit=20";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	private static ParlamentGUI glavniProzor;

	// citanje podataka u Json formatu iz fajla

	public List<Poslanik> vratiPoslanike() {
		try {
			FileReader reader = new FileReader("data/provera.json");

			Gson gson = new GsonBuilder().create();
			JsonArray membersJson = gson.fromJson(reader, JsonArray.class);

			LinkedList<Poslanik> members = new LinkedList<Poslanik>();

			for (int i = 0; i < membersJson.size(); i++) {
				JsonObject memberJson = (JsonObject) membersJson.get(i);

				Poslanik p = new Poslanik();
				p.setId(memberJson.get("id").getAsInt());
				p.setIme(memberJson.get("name").getAsString());
				p.setPrezime(memberJson.get("lastName").getAsString());
				if (memberJson.get("birthDate") != null)
					try {
						p.setDatumRodjenja(sdf.parse(memberJson.get("birthDate").getAsString()));
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(glavniProzor, "Greska prilikom parsiranja datuma", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}

				members.add(p);
			}

			return members;

		} catch (IOException e) {
			JOptionPane.showMessageDialog(glavniProzor, "Ne postoji fajl na lokaciji data/members.json", "Greska",
					JOptionPane.ERROR_MESSAGE);

		}
		return new LinkedList<Poslanik>();
	}

	// Citanje podataka sa linka i upisavanje u string

	private String sendGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		boolean endReading = false;
		String response = "";

		while (!endReading) {
			String s = in.readLine();

			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}
		}
		in.close();

		return response.toString();
	}

	// Upisivanje stringa u fajl
	public void sacuvajUFajl() throws Exception {

		String result = sendGet(membersURL);
		if (!result.isEmpty()) {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/serviceMembers.json")));

			out.println(result);

			out.close();
		}
	}

	// Konverzija u Json format i cuvanje u fajl
	public static void sacuvajUpdate(LinkedList<Poslanik> poslanik) throws Exception {
		JsonArray membersArray = new JsonArray();

		for (int i = 0; i < poslanik.size(); i++) {
			Poslanik p = poslanik.get(i);

			JsonObject memberJson = new JsonObject();
			memberJson.addProperty("id", p.getId());
			memberJson.addProperty("name", p.getIme());
			memberJson.addProperty("lastName", p.getPrezime());
			memberJson.addProperty("birthDate", sdf.format(p.getDatumRodjenja()));
			membersArray.add(memberJson);
		}

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/updatedMembers.json")));

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String membersString = gson.toJson(membersArray);

		out.println(membersString);
		out.close();

	}
}
