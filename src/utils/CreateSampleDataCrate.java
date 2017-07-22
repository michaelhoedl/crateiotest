package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;

import model.Creditcard;
import model.Orderline;
import model.Orders;
import model.Person;
import model.Product;
import model.Shopuser;

public class CreateSampleDataCrate {
	
	
	private ArrayList<Creditcard> creditcardlist = new ArrayList<Creditcard>();
	private ArrayList<Person> personlist = new ArrayList<Person>();
	private ArrayList<Product> productlist = new ArrayList<Product>();
	private ArrayList<Shopuser> shopuserlist = new ArrayList<Shopuser>();
	private ArrayList<Orders> orderlist = new ArrayList<Orders>();
	private HashSet<Orderline> orderlinelist = new HashSet<Orderline>();

	
	public static void main(String[] argv) {
		
		
		CreateSampleDataCrate csdc = new CreateSampleDataCrate();
		
		csdc.fillCreditCards();
		csdc.fillPersons();
		csdc.fillProducts();
		csdc.fillShopusers();
		csdc.fillOrders();
		csdc.fillOrderline();
		
		System.out.println("size="+csdc.orderlinelist.size());
		
		
		try {
		//	csdc.writeCreditcardsToCrate();
		//	csdc.writePersonsToCrate();
		//	csdc.writeProductsToCrate();
		//	csdc.writeShopusersToCrate();
		//	csdc.writeOrdersToCrate();
			csdc.writeOrderlinesToCrate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}

	// ---------------------------------------------------------------------------


	/**
	 * Fill the ArrayList with 10000 sample Creditcard Objects
	 */
	private void fillCreditCards() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Creditcard c1;
		for (int i = 1; i <= 10000; i++) {
			String cardname = "Visa";
			if(i%3==0) {
				cardname = "Mastercard";
			}
			else if (i%8 == 0) {
				cardname = "Amex";
			}
			
			c1 = new Creditcard(i, cardname, "1234"+i, DateHelper.getCurrentTimeStampAsDate());
			creditcardlist.add(c1);
		}
		endtime = System.nanoTime();
		System.out.println("Duration of fillCreditCards (ms): "+(endtime-starttime)/1000000);
		
	}
	
	
	/**
	 * Fill the ArrayList with 10000 sample Person Objects
	 */
	private void fillPersons() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Person p1;
		for (int i = 1; i <= 10000; i++) {
			String country = "Austria";
			if(i%4==0) {
				country = "Germany";
			}
			else if (i%13 == 0) {
				country = "Italy";
			} 
			else if (i%7 == 0) {
				country = "France";
			}
			p1 = new Person(i, "Firstname"+i, "Lastname"+i, new java.util.Date(), "0664"+i, country, "Testcity", (i+1000)+"", "Teststreet "+i, creditcardlist.get(i-1));
			personlist.add(p1);
		}
		endtime = System.nanoTime();
		System.out.println("Duration of fillPersons (ms): "+(endtime-starttime)/1000000);
		
	}

	/**
	 * Fill the ArrayList with 100000 sample Product Objects
	 */
	private void fillProducts() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Random randy = new Random();
		
		Product p1;
		for (int i = 1; i <= 1000000; i++) {
			p1 = new Product(i, "Product"+i, "The best Product!", randy.nextFloat()*randy.nextInt(1000), randy.nextFloat()*randy.nextInt(500));
			productlist.add(p1);
		}
		endtime = System.nanoTime();
		System.out.println("Duration of fillProducts (ms): "+(endtime-starttime)/1000000);
		
	}
	
	/**
	 * Fill the ArrayList with 10000 sample Shopuser Objects
	 */
	private void fillShopusers() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		

		Shopuser s1;
		for (int i = 1; i <= 10000; i++) {
			
			java.util.Date d = null;
			if(i%27 == 0) {
				d = new java.util.Date();
			}
			
			//create MD5 Hash of Password
			String pwd = "pwd"+i;
			byte[] bytesOfMessage = null;
			try {
				bytesOfMessage = pwd.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] thedigest = md.digest(bytesOfMessage);
			StringBuffer sb = new StringBuffer();
			for (byte b : thedigest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			pwd = sb.toString();

			s1 = new Shopuser("user"+i, "user"+i+"@testmail.at", pwd, new java.util.Date(), d, personlist.get(i-1));
			shopuserlist.add(s1);
		}
		endtime = System.nanoTime();
		System.out.println("Duration of fillShopusers (ms): "+(endtime-starttime)/1000000);
		
	}
	
	
	/**
	 * Fill the ArrayList with 100000 sample Order Objects
	 */
	private void fillOrders() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Random randy = new Random();

		Shopuser user;
		Orders o1;
		for(int i = 1; i <= 100000; i++) {
			
			// generate a random date
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, randy.nextInt(17)+2000);
			calendar.set(Calendar.DAY_OF_YEAR, randy.nextInt(365));
			java.util.Date d = calendar.getTime();
			
			// get a random user from the user list
			user = shopuserlist.get(randy.nextInt(10000));
			
			o1 = new Orders(i, d, user);
			orderlist.add(o1);
		}
		
		
		endtime = System.nanoTime();
		System.out.println("Duration of fillOrders (ms): "+(endtime-starttime)/1000000);
		
	}
	

	/**
	 * Fill the ArrayList with 5000000 sample Orderline Objects
	 */
	private void fillOrderline() {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Random randy = new Random();

		Product p;
		Orders o;
		
		Orderline o1;
		
		for(int i = 1; i <= 5000000; i++) {
			o = orderlist.get(randy.nextInt(100000));
			p = productlist.get(randy.nextInt(1000000));
			
			
			o1 = new Orderline(i, o, p);
			o1.setAmount(randy.nextFloat()+randy.nextInt(1000));
			orderlinelist.add(o1);
		}
		
		//for (Orderline ol : orderlinelist) {
		//	ol.setAmount(randy.nextFloat()+randy.nextInt(1000));
		//}
		
		endtime = System.nanoTime();
		System.out.println("Duration of fillOrderline (ms): "+(endtime-starttime)/1000000);
		
	}
	
	
	// ---------------------------------------------------------------------------
	


	/**
	 * Insert the Creditcards from the ArrayList into the Crate DB
	 */
	private void writeCreditcardsToCrate() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO creditcard (creditcard_id, cardname, cardnumber, expiry_date) "
								+ "VALUES (?,?,?,?)";
		try {
			dbConnection = ConnectionHelperCrate.getDBConnection();
			
			for(Creditcard c : creditcardlist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setInt(1, c.getCreditcard_id()); // the 1. ? from the PreparedStatement
				statement.setString(2, c.getCardname()); // the 2. ? from the PreparedStatement
				statement.setString(3, c.getCardnumber()); // the 3. ? from the PreparedStatement
				statement.setTimestamp(4, (new java.sql.Timestamp(c.getExpiry_date().getTime())) ); // the 4. ? from the PreparedStatement
				
				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into CREDITCARD table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		endtime = System.nanoTime();
		System.out.println("Duration of writeCreditcardsToCrate (ms): "+(endtime-starttime)/1000000);
	}
	


	/**
	 * Insert the Orderlines from the ArrayList into the Crate DB
	 */
	private void writeOrderlinesToCrate() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO orderline (orderline_id, order_id, product_id, amount) "
								+ "VALUES (?,?,?,?) ";
		try {
			dbConnection = ConnectionHelperCrate.getDBConnection();
			
			for(Orderline o : orderlinelist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setInt(1, o.getOrderline_id()); // the 1. ? from the PreparedStatement
				statement.setInt(2, o.getOrder().getOrder_id()); // the 2. ? from the PreparedStatement
				statement.setInt(3, o.getProduct().getProduct_id()); // the 3. ? from the PreparedStatement
				statement.setFloat(4, o.getAmount()); // the 4. ? from the PreparedStatement

				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into ORDERLINE table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		endtime = System.nanoTime();
		System.out.println("Duration of writeOrderlinesToCrate (ms): "+(endtime-starttime)/1000000);
		
	}


	/**
	 * Insert the Orders from the ArrayList into the Crate DB
	 */
	private void writeOrdersToCrate() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO orders (order_id, order_date, username) "
								+ "VALUES (?,?,?)";
		try {
			dbConnection = ConnectionHelperCrate.getDBConnection();
			
			for(Orders o : orderlist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setInt(1, o.getOrder_id()); // the 1. ? from the PreparedStatement
				statement.setTimestamp(2, (new java.sql.Timestamp(o.getOrder_date().getTime())) ); // the 2. ? from the PreparedStatement
				statement.setString(3, o.getUser().getUsername()); // the 3. ? from the PreparedStatement

				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into ORDERS table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		endtime = System.nanoTime();
		System.out.println("Duration of writeOrdersToCrate (ms): "+(endtime-starttime)/1000000);
		
	}
	

	/**
	 * Insert the Shopusers from the ArrayList into the Crate DB
	 */
	private void writeShopusersToCrate() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO shopuser (username, email, pwd, created_date, locked_date, person_id) "
								+ "VALUES (?,?,?,?,?,?)";
		try {
			dbConnection = ConnectionHelperCrate.getDBConnection();
			
			for(Shopuser s : shopuserlist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setString(1, s.getUsername()); // the 1. ? from the PreparedStatement
				statement.setString(2, s.getEmail()); // the 2. ? from the PreparedStatement
				statement.setString(3, s.getPwd()); // the 3. ? from the PreparedStatement
				statement.setTimestamp(4, (new java.sql.Timestamp(s.getCreated_date().getTime())) ); // the 4. ? from the PreparedStatement
				
				if (s.getLocked_date() != null) {
					statement.setTimestamp(5, (new java.sql.Timestamp(s.getLocked_date().getTime())) ); // the 5. ? from the PreparedStatement
				}else {
					statement.setTimestamp(5, null); // the 5. ? from the PreparedStatement
				}
				statement.setInt(6, s.getPerson().getPerson_id()); // the 6. ? from the PreparedStatement

				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into SHOPUSER table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		endtime = System.nanoTime();
		System.out.println("Duration of writeShopusersToCrate (ms): "+(endtime-starttime)/1000000);
		
	}
	
	

	/**
	 * Insert the Person from the ArrayList into the Crate DB
	 */
	private void writePersonsToCrate() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO person (person_id, firstname, lastname, birthdate, phone, country, city, zip, street, creditcard_id) "
								+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {
			dbConnection = ConnectionHelperCrate.getDBConnection();
			
			for(Person p : personlist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setInt(1, p.getPerson_id()); // the 1. ? from the PreparedStatement
				statement.setString(2, p.getFirstname()); // the 2. ? from the PreparedStatement
				statement.setString(3, p.getLastname()); // the 3. ? from the PreparedStatement
				statement.setTimestamp(4, (new java.sql.Timestamp(p.getBirthdate().getTime())) ); // the 4. ? from the PreparedStatement
				statement.setString(5, p.getPhone()); // the 5. ? from the PreparedStatement
				statement.setString(6, p.getCountry()); // the 6. ? from the PreparedStatement
				statement.setString(7, p.getCity()); // the 7. ? from the PreparedStatement
				statement.setString(8, p.getZip()); // the 8. ? from the PreparedStatement
				statement.setString(9, p.getStreet()); // the 9. ? from the PreparedStatement
				statement.setInt(10, p.getCreditcard().getCreditcard_id()); // the 10. ? from the PreparedStatement
				
				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into PERSON table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		endtime = System.nanoTime();
		System.out.println("Duration of writePersonsToCrate (ms): "+(endtime-starttime)/1000000);
		
	}
	
	

	/**
	 * Insert the Products from the ArrayList into the Crate DB
	 */
	private void writeProductsToCrate() throws SQLException {
		long starttime;
		long endtime;
		starttime = System.nanoTime();
		
		Connection dbConnection = null;
		PreparedStatement statement = null;
		
		String insertTableSQL = "INSERT INTO product (product_id, product_name, product_description, price, in_stock) "
								+ "VALUES (?,?,?,?,?)";
		try {
			dbConnection = ConnectionHelperCrate.getDBConnection();
			
			for(Product p : productlist) {
				statement = dbConnection.prepareStatement(insertTableSQL);
				
				statement.setInt(1, p.getProduct_id()); // the 1. ? from the PreparedStatement
				statement.setString(2, p.getProduct_Name()); // the 2. ? from the PreparedStatement
				statement.setString(3, p.getProduct_Description()); // the 3. ? from the PreparedStatement
				statement.setFloat(4, p.getPrice()); // the 4. ? from the PreparedStatement
				statement.setFloat(5, p.getIn_stock()); // the 5. ? from the PreparedStatement

				// execute insert SQL statement
				statement.executeUpdate();
				
				statement.close();
			} 
		
			System.out.println("Records are inserted into PRODUCT table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		endtime = System.nanoTime();
		System.out.println("Duration of writeProductsToCrate (ms): "+(endtime-starttime)/1000000);
		
	}



}
