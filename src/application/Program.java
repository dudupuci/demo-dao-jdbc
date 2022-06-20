package application;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);

		Department obj = new Department(1, "Books");
		Seller seller = new Seller(2, "Bob Grey", "bob@gmail.com", new Date(), 2500.50, obj);

		SellerDao sellerDao = DaoFactory.createSellerDao();
		
	}

}
