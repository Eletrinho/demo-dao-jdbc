package aplication;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.List;

public class Program {
    public static void main(String[] args) {
//        Department obj = new Department(1, "Books");
//        Seller seller = new Seller(21, "Bob", "bob@gmail.com", LocalDate.now(), 3000d, obj);
//        System.out.println(seller);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("Search All:");
        List<Seller> sellers = sellerDao.finAll();
        sellers.forEach(System.out::println);

        System.out.println("\nSearch by Id:");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\nSearch by Department:");
        Department obj = new Department(2, null);
        List<Seller> sellers1 = sellerDao.findByDepartment(obj);
        sellers1.forEach(System.out::println);
        
        DB.closeConnection();
    }
}
