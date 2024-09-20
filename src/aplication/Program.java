package aplication;

import entities.model.Department;
import entities.model.Seller;

import java.time.LocalDate;

public class Program {
    public static void main(String[] args) {
        Department obj = new Department(1, "Books");
        Seller seller = new Seller(21, "Bob", "bob@gmail.com", LocalDate.now(), 3000d, obj);
        System.out.println(seller);
    }
}
