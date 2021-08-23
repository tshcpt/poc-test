package com.sample.rest.webservices.ledger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class TestDBConnection {

    public static void main(String[] args) {
     /*   try {
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
        }*/
        String date = "Wed Nov 17 04:52:35 GMT 2021";
        System.out.println("date:"+date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd'T':HH:mm:ss.SSSz");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        ZonedDateTime dt = null;
        dt = ZonedDateTime.parse(date, formatter1);
        System.out.println("date:"+ dt);
        String dt1 = dt.format(formatter);
        System.out.println("date1 :"+ dt1);

        Date date1 = new Date();
        SimpleDateFormat formatter2 = new SimpleDateFormat("MM-dd'T':HH:mm:ss.SSSz");
        formatter2.format(date1);


    }
}

