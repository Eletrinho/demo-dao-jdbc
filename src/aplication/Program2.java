package aplication;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class Program2 {
    public static void main(String[] args) {
        DepartmentDao dao = DaoFactory.createDepartmentDao();

        System.out.println("Insert Department:");
        Department department = new Department(null, "D2");
        dao.insert(department);
        System.out.println("Funciona");

        System.out.println("\nFindAll Department:");
        List<Department> departments = dao.finAll();
        departments.forEach(System.out::println);

        System.out.println("\nFindById Department:");
        System.out.println(dao.findById(2));

        System.out.println("\nUpdate Department:");
        department.setName("MODA");
        department.setId(2);
        dao.update(department);
        System.out.println("Funciona");

        System.out.println("\nDelete Department:");
        dao.deleteById(departments.getLast().getId());
        System.out.println("Funciona!");
    }
}
