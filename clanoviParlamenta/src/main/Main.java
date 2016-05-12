package main;

import java.util.List;

import domain.Poslanik;
import util.ParlamentAPIKomunikacija;

public class Main {

	public static void main(String[] args) throws Exception {

		ParlamentAPIKomunikacija comm = new ParlamentAPIKomunikacija();

		List<Poslanik> members = comm.vratiPoslanike();

		for (Poslanik m : members) {
			System.out.println(m);
		}
		
		comm.ucitajUFajl();

	}

}
