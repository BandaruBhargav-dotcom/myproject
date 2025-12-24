
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

@WebServlet("/getalldetails")
public class DisplayAllStudentDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        // 🔹 Context path for CSS
        String path = req.getContextPath();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Total Students</title>");
        out.println("<link rel='stylesheet' href='" + path + "/css/style.css'>");
        out.println("</head>");
        out.println("<body>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_data","root","root");
            
            PreparedStatement ps =con.prepareStatement("SELECT * FROM students");
                    

            ResultSet resultSet = ps.executeQuery();

            out.println("<h2 class='title'>TOTAL STUDENTS</h2>");

            out.println("<table class='student-table' border='1' cellpadding='8'>");

            // -------- TABLE HEADER --------
            out.println("<tr>");
            out.println("<th>Name</th>");
            out.println("<th>Email</th>");
            out.println("<th>Password</th>");
            out.println("<th>Number</th>");
            out.println("<th>Update</th>");
            out.println("<th>Delete</th>");
            out.println("</tr>");

            // -------- TABLE ROWS --------
            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                long number = resultSet.getLong("number");

                out.println("<tr>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + password + "</td>");
                out.println("<td>" + number + "</td>");

                out.println("<td><a href='update?email=" + email + "'>UPDATE</a></td>");
                out.println("<td><a href='delete?email=" + email + "'>DELETE</a></td>");

                out.println("</tr>");
            }

            out.println("</table>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("</body>");
        out.println("</html>");
    }
}