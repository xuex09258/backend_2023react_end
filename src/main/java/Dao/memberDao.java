package Dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class memberDao implements implDao{

	public static void main(String[] args) {
		EntityManagerFactory em= Persistence.createEntityManagerFactory("Company");
	}

}
	
