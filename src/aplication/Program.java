package aplication;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("Search All:");
        List<Seller> sellers = sellerDao.finAll();
        sellers.forEach(System.out::println);

        System.out.println("\nSearch by Id:");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\nSearch by Department:");
        Department dep = new Department(2, null);
        List<Seller> sellers1 = sellerDao.findByDepartment(dep);
        sellers1.forEach(System.out::println);

        System.out.println("\nInsert Test: ");
//        Seller seller1 = new Seller(null, "Bob", "bob1@gmail.com", LocalDate.now().minusYears(35), 3000d, dep);
//        sellerDao.insert(seller1);
        System.out.println("Sucesso!");

        System.out.println("\nUpdate Test:");
        seller.setName("Mônica");
        seller.setBaseSalary(20000d);
        seller.setEmail("monica@gmail.com");
        seller.setDepartment(dep);
        sellerDao.update(seller);
        System.out.println("User " + seller.getId() + " Atualizado");

        System.out.println("\nDelete Test:");
        sellerDao.deleteById(22);
        System.out.println("Last user deleted.");
        DB.closeConnection();
    }
}
