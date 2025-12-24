package org.jsp;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/updateStudent")
public class UpdateStudentActionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String oldEmail = req.getParameter("oldEmail"); // 🔴 original email
        String name = req.getParameter("name");
        String email = req.getParameter("newemail");       // 🔴 new email
        String password = req.getParameter("password");
        long number = Long.parseLong(req.getParameter("number"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_data",
                    "root", "root");

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE students SET name=?, email=?, password=?, number=? WHERE email=?");

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setLong(4, number);
            ps.setString(5, oldEmail); // 🔴 WHERE condition uses OLD email

            ps.executeUpdate();

            con.close();

            // Redirect back to list page
            resp.sendRedirect("getalldetails");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
