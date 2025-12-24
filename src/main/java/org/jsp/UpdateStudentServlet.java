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
@WebServlet("/update")
public class UpdateStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        String email = req.getParameter("email");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_data",
                    "root", "root");

            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT * FROM students WHERE email=?");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                out.println("<html><body>");
                out.println("<h2>Update Student</h2>");

                out.println("<form action='updateStudent' method='post'>");

                // 🔴 VERY IMPORTANT: old email
                out.println("<input type='hidden' name='oldEmail' value='" + email + "'>");

                out.println("Name: <input type='text' name='name' value='" + resultSet.getString("name") + "'><br><br>");
                out.println("Email: <input type='text' name='newemail' value='" + resultSet.getString("email") + "'><br><br>");
                out.println("Password: <input type='text' name='password' value='" + resultSet.getString("password") + "'><br><br>");
                out.println("Number: <input type='text' name='number' value='" + resultSet.getLong("number") + "'><br><br>");

                out.println("<input type='submit' value='UPDATE'>");
                out.println("</form>");

                out.println("</body></html>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
