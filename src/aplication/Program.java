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

        List<Seller> sellers = sellerDao.finAll();
        System.out.println(sellers);
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        DB.closeConnection();
    }
}
