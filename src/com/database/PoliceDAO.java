package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.Main.PoliceDetails;

public class PoliceDAO {

    public List<PoliceDetails> getAllPolice() {

        List<PoliceDetails> list = new ArrayList<>();

        String sql = "SELECT * FROM police_details";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new PoliceDetails(
                        rs.getString("username"),
                        rs.getString("police_rank"),
                        rs.getString("station"),
                        rs.getString("contact")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
