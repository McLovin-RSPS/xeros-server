package io.Odyssey.sql;


import io.Odyssey.model.entity.player.Player;
import io.Odyssey.model.entity.player.PlayerHandler;
import io.Odyssey.util.Misc;

import java.sql.*;


/**
 * Opens the store page in the default web browser.
 *
 * @author The Plateau
 */
public class NewStore implements Runnable {

    private static final String HOST = "208.109.41.228"; // website ip address
    private static final String USER = "xostore";
    private static final String PASS = "-(]fb!X)+mT+";
    private static final String DATABASE = "yastastore";

    private Player player;
    private Connection conn;
    private Statement stmt;


    public NewStore(Player player) {
        this.player = player;
    }

    @Override
    public void run(){
        try {
            if (!connect(HOST, DATABASE, USER, PASS)) {
                return;
            }

            String name = player.getDisplayName().replace("_", " ");
            ResultSet rs = executeQuery("SELECT * FROM payments WHERE player_name='" + name + "' AND status='Completed' AND claimed=0");

            while (rs.next()) {
                int item_number = 0;

                item_number = rs.getInt("item_number");

                double paid = rs.getDouble("amount");
                int quantity = rs.getInt("quantity");

                switch (item_number) {// add products according to their ID in the ACP



                    case 1023: // example
                        player.getItems().addItemUnderAnyCircumstance(2403, quantity);
                        player.sendMessage("Thank you for donating!");
                        break;
                    case 1024: // example
                        player.getItems().addItem(2396, quantity);
                        player.sendMessage("Thank you for donating!");
                        break;
                    case 1025: // example
                        player.getItems().addItemUnderAnyCircumstance(786, quantity);
                        PlayerHandler.executeGlobalMessage("[@cya@DONATION@bla@] @dre@Thank you! " + Misc.capitalizeJustFirst(player.getDisplayName()) + " has just donated!");
                        player.sendMessage("Thank you for donating!");
                        break;


                    case 1026: // example
                        player.getItems().addItemUnderAnyCircumstance(761, quantity);
                        PlayerHandler.executeGlobalMessage("[@cya@DONATION@bla@] @dre@Thank you! " + Misc.capitalizeJustFirst(player.getDisplayName()) + " has just donated!");
                        player.sendMessage("Thank you for donating!");
                        break;
                    case 1027: // example
                        player.getItems().addItemUnderAnyCircumstance(607, quantity);
                        PlayerHandler.executeGlobalMessage("[@cya@DONATION@bla@] @dre@Thank you! " + Misc.capitalizeJustFirst(player.getDisplayName()) + " has just donated!");
                        player.sendMessage("Thank you for donating!");
                        break;
                    case 1028: // example
                        player.getItems().addItemUnderAnyCircumstance(608, quantity);
                        PlayerHandler.executeGlobalMessage("[@cya@DONATION@bla@] @dre@Thank you! " + Misc.capitalizeJustFirst(player.getDisplayName()) + " has just donated!");
                        player.sendMessage("Thank you for donating!");
                        break;
                    case 1029:
                        player.getItems().addItemUnderAnyCircumstance(20201, quantity);
                        player.sendMessage("Thank you for donating!");
                        break;


                }
                rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
                rs.updateRow();
            }

            destroy();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param host the host ip address or url
     * @param database the name of the database
     * @param user the user attached to the database
     * @param pass the users password
     * @return true if connected
     */
    public boolean connect(String host, String database, String user, String pass) {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, pass);
            System.out.print("Connected");
            return true;
        } catch (SQLException e) {
            System.out.println("Failing connecting to database!");
            return false;
        }
    }

    /**
     * Disconnects from the MySQL server and destroy the connection
     * and statement instances
     */
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

    /**
     * Executes an update query on the database
     * @param query
     * @see {@link Statement#executeUpdate}
     */
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

    /**
     * Executres a query on the database
     * @param query
     * @see {@link Statement#executeQuery(String)}
     * @return the results, never null
     */
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
