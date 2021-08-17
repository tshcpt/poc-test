package com.sample.rest.webservices.ledger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDBConnection {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    //"jdbc:mysql://pocdb-instance-1.cipwprgh221p.us-east-1.rds.amazonaws.com:3306/pocdb", "admin", "Feud640yarn413");
                    "jdbc:mysql://localhost:3306/poc?serverTimezone=UTC", "admin", "Feud640yarn413");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from emp");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

