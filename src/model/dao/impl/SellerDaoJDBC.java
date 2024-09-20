package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    private final Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

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
            while (rs.next()){
                sellers.add(instantiateSeller(rs, instantiateDepartment(rs)));
            } return sellers;
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
