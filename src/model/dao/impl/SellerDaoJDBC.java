package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private final Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement ps = null;
        try {
            if (verifyEmail(obj.getEmail()) == null) {
                ps = conn.prepareStatement(
                        "INSERT INTO seller "
                                + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                                + "VALUES "
                                + "(?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, obj.getName());
                ps.setString(2, obj.getEmail());
                ps.setDate(3, Date.valueOf(obj.getBirthDate()));
                ps.setDouble(4, obj.getBaseSalary());
                ps.setInt(5, obj.getDepartment().getId());

                int rows = ps.executeUpdate();
                if (rows > 0){
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                    DB.closeResult(rs);
                } else { throw new DbException("Erro inesperado");}
            } else {throw new DbException("Esse email já existe");}

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ? ");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()){
                return instantiateSeller(rs, instantiateDepartment(rs));
            } return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResult(rs);
            DB.closeStatment(ps);
        }
    }

    @Override
    public List<Seller> finAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id ");
            rs = ps.executeQuery();
            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                sellers.add(instantiateSeller(rs, instantiateDepartment(rs)));
            } return sellers;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResult(rs);
            DB.closeStatment(ps);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE Department.Id = ? "
                            + "ORDER BY Name");
            ps.setInt(1, department.getId());
            rs = ps.executeQuery();
            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                sellers.add(instantiateSeller(rs, instantiateDepartment(rs)));
            } return sellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResult(rs);
            DB.closeStatment(ps);
        }
    }

    private Seller verifyEmail(String email) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Email = ? ");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()){
                return instantiateSeller(rs, instantiateDepartment(rs));
            } return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResult(rs);
            DB.closeStatment(ps);
        }
    }
    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        return new Department(
                rs.getInt("DepartmentId"),
                rs.getString("DepName"));
    }

    private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
        return new Seller(
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getString("Email"),
                rs.getDate("BirthDate").toLocalDate(),
                rs.getDouble("BaseSalary"),
                dp);
    }
}
