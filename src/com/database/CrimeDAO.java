package com.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import com.Main.Crime;

public class CrimeDAO {

    // ================= ADD CRIME =================
    public int addCrime(Crime c) {

        String sql = "INSERT INTO crimes "
                + "(criminal_name, crime_type, location, status, assigned_police, notes, crime_date, day_name) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getCrimeType());
            ps.setString(3, c.getLocation());
            ps.setString(4, c.getStatus());
            ps.setString(5, c.getAssignedPolice());
            ps.setString(6, c.getInvestigationNotes());
            ps.setDate(7, Date.valueOf(c.getDate()));
            ps.setString(8, c.getDayName());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // ================= UPDATE CRIME =================
    public void updateCrime(Crime c) {

    	String sql = "UPDATE crimes SET "
    			+ "criminal_name=?, crime_type=?, location=?, status=?, "
    			+ "assigned_police=?, notes=?, punishment=? WHERE crime_id=?";
    	
    	
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getCrimeType());
            ps.setString(3, c.getLocation());
            ps.setString(4, c.getStatus());
            ps.setString(5, c.getAssignedPolice());
            ps.setString(6, c.getInvestigationNotes());
            ps.setInt(7, c.getCrimeId());
            ps.setString(7, c.getPunishment());
            ps.setInt(8, c.getCrimeId());
            ps.executeUpdate();

//            System.out.println("  Database Updated Successfully");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // ================= LOAD ALL CRIMES =================

    public HashMap<Integer, Crime> loadAllCrimes() {

        HashMap<Integer, Crime> map = new HashMap<>();

        String sql = "SELECT * FROM crimes";

        try (Connection con = DBConnection.getConnection();
    
        		PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

            	Crime c = new Crime(
            		    rs.getInt("crime_id"),
            		    rs.getString("criminal_name"),
            		    rs.getString("crime_type"),
            		    rs.getString("location"),
            		    rs.getString("status"),
            		    rs.getString("assigned_police"),
            		    rs.getString("notes"),
            		    rs.getDate("crime_date").toLocalDate(),
            		    rs.getString("day_name"),
            		    rs.getString("punishment")
            		);
                map.put(c.getCrimeId(), c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


    public void deleteCrime(int id)
    {

        String sql = "DELETE FROM crimes WHERE crime_id=?";

        try (Connection con = DBConnection.getConnection();
    
        		PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

//            System.out.println("Crime Deleted From Database");

        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
