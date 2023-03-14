package io.Odyssey.sql; // dont forget to change packaging ^-^


import io.Odyssey.model.entity.player.Player;
import io.Odyssey.net.discord.DiscordMessager;
import io.Odyssey.util.Misc;
import io.Odyssey.util.discord.Discord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;


public class NewVote implements Runnable {
    public static final String HOST = "208.109.41.228";
    public static final String USER = "yastavote";
    public static final String PASS = "ZEA@*?Fs~^5p";
    public static final String DATABASE = "xovote";


    private Player player;
    private Connection conn;
    private Statement stmt;

    public NewVote(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            if (!connect(HOST, DATABASE, USER, PASS)) {
                System.out.println("Can't connect to mysql");
                return;
            }

            //    String name = player.getUsername().toLowerCase(""," ");
            ResultSet rs = executeQuery("SELECT * FROM votes WHERE username='"+player.getDisplayName().toLowerCase()+"' AND claimed=0 AND voted_on != -1");

            while (rs.next()) {
                String ipAddress = rs.getString("ip_address");
                int siteId = rs.getInt("site_id");
                int random = Misc.random(10);
                if(random > 7 && random < 9){
                    player.getItems().addItemUnderAnyCircumstance(2841 ,1);
                }
                player.getItems().addItemUnderAnyCircumstance(1464,1);
                player.getItems().addItemUnderAnyCircumstance(995,2000000);
                player.sendMessage("Thank you for voting");
                System.out.println("[Vote] Vote claimed by "+player.getDisplayName().toLowerCase()+". (sid: "+siteId+", ip: "+ipAddress+")");
                DiscordMessager.sendVote(":ok: [Vote] "+player.getDisplayName()+" has just voted.");
                rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
                rs.updateRow();
            }

            destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean connect(String host, String database, String user, String pass) {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, pass);
            return true;
        } catch (SQLException e) {
            System.out.println("Failing connecting to database!");
            return false;
        }
    }

    public void destroy() {
        try {
            conn.close();
            conn = null;
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int executeUpdate(String query) {
        try {
            this.stmt = this.conn.createStatement(1005, 1008);
            int results = stmt.executeUpdate(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public ResultSet executeQuery(String query) {
        try {
            this.stmt = this.conn.createStatement(1005, 1008);
            ResultSet results = stmt.executeQuery(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
