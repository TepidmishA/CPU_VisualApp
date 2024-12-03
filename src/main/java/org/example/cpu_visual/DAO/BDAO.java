package org.example.cpu_visual.DAO;

public class BDAO {
	static DAO dao = new DAO(); //new DAO_memory();
	public static DAO getDAO(){
		return dao;
	}
}
