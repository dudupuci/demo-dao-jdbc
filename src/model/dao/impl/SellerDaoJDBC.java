package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		PreparedStatement st = null;
		ResultSet resultSet = null;
		try {
			st = conn.prepareStatement("INSERT INTO seller " 
		            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " 
		            + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());

			int rows = st.executeUpdate();

			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				} else {
					throw new DbException("No rows affected: ");
				}
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(resultSet);
		}

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer Id) {
		PreparedStatement st = null;
		ResultSet resultSet = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			st.setInt(1, Id);
			resultSet = st.executeQuery();
			if (resultSet.next()) {
				Department dep = instantieteDepartment(resultSet);
				Seller obj = instantieteSeller(resultSet, dep);
				return obj;
			}

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {

			DB.closeResultSet(resultSet);
		}
		return null;

	}

	private Seller instantieteSeller(ResultSet resultSet, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(resultSet.getInt("Id"));
		obj.setName(resultSet.getString("Name"));
		obj.setEmail(resultSet.getString("Email"));
		obj.setBirthDate(resultSet.getDate("BirthDate"));
		obj.setBaseSalary(resultSet.getDouble("BaseSalary"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantieteDepartment(ResultSet resultSet) throws SQLException {
		Department dep = new Department();
		dep.setId(resultSet.getInt("DepartmentId"));
		dep.setName(resultSet.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet resultSet = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			resultSet = st.executeQuery();

			while (resultSet.next()) {
				Department dep = map.get(resultSet.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantieteDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), dep);
				}

				Seller obj = instantieteSeller(resultSet, dep);
				list.add(obj);

			}
			return list;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {

			DB.closeResultSet(resultSet);
		}
		return null;

	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet resultSet = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			st.setInt(1, department.getId());

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			resultSet = st.executeQuery();

			while (resultSet.next()) {
				Department dep = map.get(resultSet.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantieteDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), dep);
				}

				Seller obj = instantieteSeller(resultSet, dep);
				list.add(obj);

			}
			return list;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {

			DB.closeResultSet(resultSet);
		}
		return null;

	}

}
