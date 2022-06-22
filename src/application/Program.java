package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;

		System.out.println("==== TEST 1: seller findById ====");

		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("==== TEST 2: seller findByDepartment ====");
		Department department = new Department(3, null);
		List<Seller> list = sellerDao.findByDepartment(department);

		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n==== TEST 3: seller findAll ====");
		list = sellerDao.findAll();

		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n==== TEST 4: seller insert ====");
		Seller newSeller = new Seller(null, "Eduardo Pucinelli", "dudupucinelli@gmail.com", new Date(), 1900.00,
				department);
		sellerDao.insert(newSeller);

		System.out.println("Insert done! " + newSeller.getId());

		System.out.println("\n==== TEST 4: seller update ====");
     
        
        seller = sellerDao.findById(5);
        seller.setName("Pedrinho");
        seller.setBaseSalary(5450.00);
        sellerDao.update(seller);
        System.out.println("Updated");
        
        

	}

}
