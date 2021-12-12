package mx.edu.utez.controller;
import mx.edu.utez.model.Employee;
import mx.edu.utez.util.ConectionMysql;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/employee")
public class Service {
    Connection con;
    PreparedStatement pstm;
    Statement state;
    ResultSet rs;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            con = ConectionMysql.getConnection();
            String query = " SELECT employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle FROM employees;";
            state = con.createStatement();
            rs = state.executeQuery(query);
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeNumber(rs.getInt("employeeNumber"));
                employee.setLastName(rs.getString("lastName"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setExtension(rs.getString("extension"));
                employee.setEmail(rs.getString("email"));
                employee.setOfficeCode(rs.getInt("officeCode"));
                employee.setReportsTo(rs.getInt("reportsTo"));
                employee.setJobTitle(rs.getString("jobTitle"));
                employees.add(employee);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
        return employees;
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@PathParam("id") int employeeNumber) {
        Employee employee = new Employee();
        try {
            con = ConectionMysql.getConnection();
            String query = "SELECT `lastName`, `firstName`, `extension`, `email`, `officeCode`, `reportsTo`, `jobTitle` FROM `employees` WHERE employeeNumber = ?;";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, employeeNumber);
            rs = pstm.executeQuery();
            if (rs.next()) {
                employee.setLastName(rs.getString("lastName"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setExtension(rs.getString("extension"));
                employee.setEmail(rs.getString("email"));
                employee.setOfficeCode(rs.getInt("officeCode"));
                employee.setReportsTo(rs.getInt("reportsTo"));
                employee.setJobTitle(rs.getString("jobTitle"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return employee;
    }

    @POST
    @Path("/createEmployee/{id}/{lastName}/{firstName}/{extension}/{email}/{officeCode}/{reportsTo}/{jobTitle}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean createEmployee(@PathParam("id") int employeeNumber,
                               @PathParam("lastName") String lastName,
                               @PathParam("firstName") String firstName,
                               @PathParam("extension") String extension,
                               @PathParam("email") String email,
                               @PathParam("officeCode") int officeCode,
                               @PathParam("reportsTo") int reportsTo,
                               @PathParam("jobTitle") String jobTitle) {
        boolean flag = false;
        try {
            con = ConectionMysql.getConnection();
            pstm = con.prepareCall("INSERT INTO employees (employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle) VALUES (?,?,?,?,?,?,?,?);");
            pstm.setInt(1, employeeNumber);
            pstm.setString(2, lastName);
            pstm.setString(3, firstName);
            pstm.setString(4, extension);
            pstm.setString(5, email);
            pstm.setInt(6, officeCode);
            pstm.setInt(7, reportsTo);
            pstm.setString(8, jobTitle);

            flag = pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        } finally {

        }

        return flag;
    }

    @PUT
    @Path("/updateEmployee/{id}/{lastName}/{firstName}/{extension}/{email}/{officeCode}/{reportsTo}/{jobTitle}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updateEmployee(@PathParam("id") int employeeNumber, @PathParam("lastName") String lastName, @PathParam("firstName") String firstName, @PathParam("extension") String extension, @PathParam("email") String email, @PathParam("officeCode") int officeCode, @PathParam("reportsTo") int reportsTo, @PathParam("jobTitle") String jobTitle) {
        boolean flag = false;
        try {
            con = ConectionMysql.getConnection();
            pstm = con.prepareCall("UPDATE employees SET lastName = ?, firstName = ?, extension = ?, email = ?, officeCode = ?, reportsTo = ?, jobTitle = ? WHERE employeeNumber = ?");
            pstm.setString(1, lastName);
            pstm.setString(2, firstName);
            pstm.setString(3, extension);
            pstm.setString(4, email);
            pstm.setInt(5, officeCode);
            pstm.setInt(6, reportsTo);
            pstm.setString(7, jobTitle);
            pstm.setInt(8, employeeNumber);

            flag = pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        } finally {

        }

        return flag;
    }

    @DELETE
    @Path("/deleteEmployee/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteEmployee(@PathParam("id") int employeeNumber) {
        boolean flag = false;

        try {
            con = ConectionMysql.getConnection();
            pstm = con.prepareCall("DELETE FROM employees WHERE employeeNumber = ?");
            pstm.setInt(1, employeeNumber);

            flag = pstm.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        } finally {

        }

        return flag;
    }
}
