package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private final Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)");
            ps.setString(1, obj.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");

            ps.setString(1, obj.getName());
            ps.setInt(2, obj.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE department WHERE Id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");

            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()){
                return instantiateDepartment(rs);
            } return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatment(ps);
            DB.closeResult(rs);
        }
    }

    @Override
    public List<Department> finAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Department> departments = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM department");

            rs = ps.executeQuery();
            if (rs.next()){
                departments.add(instantiateDepartment(rs));
            } return departments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatment(ps);
            DB.closeResult(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        return new Department(
                rs.getInt("DepartmentId"),
                rs.getString("DepName"));
    }
}
