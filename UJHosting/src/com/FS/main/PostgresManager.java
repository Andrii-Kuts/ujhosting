package com.FS.main;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PostgresManager {
    private Connection connection = null;
    private String port, username, pass;

    public PostgresManager(Window window)
    {
        port = window.port;
        username = window.username;
        pass = window.pass;
    }

    public boolean Connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:"+port+"/postgres?user="+username+"&password="+pass);
            System.out.println("Successfully Connected to the Postgres");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Connection Failed!");
            return false;
        }
    }

    public boolean CheckUserLogin(String nickname) {
        try {
            Statement stm = connection.createStatement();
            String query = "SELECT * FROM public.\"users\" WHERE nickname = \'" + nickname + "\' OR email = \'" + nickname + "\';";
            System.out.println(query);
            ResultSet rs = stm.executeQuery(query);
            if (!rs.next())
                return false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query");
        }
        return false;
    }

    public String GetPassword(String nickname) {
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM public.\"users\" WHERE nickname = \'" + nickname + "\' OR email = \'" + nickname + "\' ;");
            if (!rs.next()) {
                return "";
            } else {
                return rs.getString("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query");
        }
        return "";
    }

    public int ConnectAsUser(String nickname, char[] password) {
        try {
            Statement stm = connection.createStatement();
            String query = "SELECT * FROM public.\"users\" WHERE (nickname = \'" + nickname + "\' OR email = \'" + nickname + "\') " +
                    "AND password = \'" + String.valueOf(password) + "\' ;";
            ResultSet rs = stm.executeQuery(query);
            System.out.println(query);
            if (!rs.next()) {
                return -1;
            } else {
                return rs.getInt("user_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query");
        }
        return -1;
    }

    public boolean RegisterUser(String first_name, String second_name, String nickname, String gender, Integer location, char[] password, String email) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO public.users (first_name, second_name, nickname, gender, " +
                    "location_id, password, email) VALUES (?, ?, ?, ?::gender, ?, ?, ?)");
            ps.setString(1, first_name);
            ps.setString(2, second_name);
            ps.setString(3, nickname);
            ps.setString(4, gender);
            ps.setObject(5, location);
            ps.setString(6, String.valueOf(password));
            ps.setString(7, email);
            int vl = ps.executeUpdate();
            if (vl == 0)
                return false;
            if (vl == 1)
                return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something went wrong while registering a user");
        }
        return false;
    }

    public boolean EditUser(int user_id, String first_name, String second_name, String gender, Integer location, char[] password, String email) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE public.users SET first_name = ?, second_name = ?, gender = ?::gender," +
                    "location_id = ?, password = ?, email = ? WHERE user_id = ?");
            ps.setString(1, first_name);
            ps.setString(2, second_name);
            ps.setString(3, gender);
            ps.setObject(4, location);
            ps.setString(5, String.valueOf(password));
            ps.setString(6, email);
            ps.setInt(7, user_id);
            int vl = ps.executeUpdate();
            if (vl == 0)
                return false;
            if (vl == 1)
                return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something went wrong while editing a user");
        }
        return false;
    }

    public String GetUserNickname(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nickname FROM public.users WHERE user_id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return "Error";
            } else {
                return rs.getString("nickname");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query");
        }
        return "Error";
    }

    public HostOffer[] GetPopularOffers(int num) {
        try {
            String s = "SELECT * FROM host_offers_info";
            if(num != -1)
                s += " LIMIT " + (Integer)num;
            PreparedStatement ps = connection.prepareStatement(s);
            ResultSet rs = ps.executeQuery();
            ArrayList<HostOffer> offers = new ArrayList<>();
            while (rs.next()) {
                HostOffer ho = new HostOffer();
                ho.city = rs.getString("city");
                ho.country = rs.getString("country");
                ho.maximum_cpu = rs.getInt("maximum_cpu_usage");
                ho.maximum_disk = rs.getInt("maximum_disk_usage");
                ho.name = rs.getString("name");
                ho.offer_id = rs.getInt("offer_id");
                ho.price = rs.getDouble("price");
                ho.time_from = rs.getString("time_from");
                ho.time_to = rs.getString("time_to");
                ho.location_id = rs.getInt("location_id");
                ho.discount = rs.getDouble("discount");
                offers.add(ho);
            }
            System.out.println(offers.size());
            return offers.toArray(new HostOffer[9]);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }

    public ServerOffer[] GetServerOffers(int num)
    {
        try {
            String s = "SELECT * FROM server_offers_info";
            if(num != -1)
                s += " LIMIT " + (Integer)num;
            PreparedStatement ps = connection.prepareStatement(s);
            ResultSet rs = ps.executeQuery();
            ArrayList<ServerOffer> so = new ArrayList<>();
            while(rs.next())
            {
                ServerOffer ss = new ServerOffer();
                ss.discount = rs.getDouble("discount");
                ss.price = rs.getDouble("price");
                ss.server_id = rs.getInt("server_id");
                ss.offer_id = rs.getInt("offer_id");
                ss.name = rs.getString("name");
                ss.nickname = rs.getString("nickname");
                ss.owner_id = rs.getInt("owner_id");
                so.add(ss);
            }
            return so.toArray(new ServerOffer[so.size()]);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }

    public Server[] GetUserServers(int num, int user)
    {
        try {
            String quer = "SELECT a.server_id, a.maximum_cpu_usage, a.maximum_disk_usage, c.city, c.country, a.owner_id, d.nickname" +
                    "\nFROM servers a JOIN computers b ON a.computer_id = b.computer_id LEFT JOIN locations c ON b.location_id = c.location_id" +
                    "\nJOIN users d ON a.owner_id = d.user_id WHERE a.owner_id = ? ORDER BY 1";
            if(num != -1)
            {
                quer += " LIMIT " + ((Integer)num).toString();
            }

            PreparedStatement ps = connection.prepareStatement(quer);
            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();
            ArrayList<Server> servers = new ArrayList<>();
            while (rs.next())
            {
                Server s = new Server();
                s.server_id = rs.getInt("server_id");
                s.maximum_cpu = rs.getInt("maximum_cpu_usage");
                s.maximum_disk = rs.getInt("maximum_disk_usage");
                s.city = rs.getString("city");
                s.country = rs.getString("country");
                s.owner_id = rs.getInt("owner_id");
                s.owner_nickname = rs.getString("nickname");

                servers.add(s);
            }
            System.out.println(servers.size());
            return servers.toArray(new Server[servers.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }

    public Card[] GetUserCards(int num, int user)
    {
        try {
            String quer = "SELECT card_id, number, cvc, expiary_date FROM cards WHERE owner_id = ?";
            if(num != -1)
            {
                quer += " LIMIT " + ((Integer)num).toString();
            }
            PreparedStatement ps = connection.prepareStatement(quer);
            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();
            ArrayList<Card> cards = new ArrayList<>();
            while(rs.next())
            {
                Card crd = new Card();
                crd.expirationDate = rs.getString("expiary_date");
                crd.cvc = rs.getString("cvc");
                crd.number = rs.getString("number");
                crd.card_id = rs.getInt("card_id");
                cards.add(crd);
            }
            return cards.toArray(new Card[cards.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }

    public Card GetCardInfo(int card)
    {
        try {
            String quer = "SELECT card_id, number, cvc, expiary_date FROM cards WHERE card_id = ?";
            PreparedStatement ps = connection.prepareStatement(quer);
            ps.setInt(1, card);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Card crd = new Card();
            crd.card_id = rs.getInt("card_id");
            crd.cvc = rs.getString("cvc");
            crd.number = rs.getString("number");
            crd.expirationDate = rs.getString("expiary_date");
            return crd;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }

    public boolean UpdateCard(int card, String number, String cvc, String expiration_date) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE cards SET number = ?, cvc = ?, expiary_date = ? WHERE card_id = ?");
            ps.setString(1, number);
            ps.setString(2, cvc);
            ps.setString(3, expiration_date);
            ps.setInt(4, card);
            int vl = ps.executeUpdate();
            if (vl == 0)
                return false;
            if (vl == 1)
                return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something went wrong while registering a user");
        }
        return false;
    }

    public boolean DeleteCard(int card)
    {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE transactions SET card_id = NULL WHERE card_id = ?");
            ps.setInt(1, card);
            ps.executeUpdate();

            ps = connection.prepareStatement("DELETE FROM cards WHERE card_id = ?");
            ps.setInt(1, card);
            int vl = ps.executeUpdate();
            if (vl == 0)
                return false;
            if (vl == 1)
                return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something went wrong while registering a user");
        }
        return false;
    }

    public boolean AddCard(String number, String cvc, String expiration_date, int owner)
    {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO cards (number, cvc, expiary_date, owner_id) " +
                    "VALUES (?, ?, ?, ?);");
            ps.setString(1, number);
            ps.setString(2, cvc);
            ps.setString(3, expiration_date);
            ps.setInt(4, owner);

            int vl = ps.executeUpdate();
            if (vl == 0)
                return false;
            if (vl == 1)
                return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something went wrong while registering a user");
        }
        return false;
    }


    public Server[] GetComputersServers(int num, int computer)
    {
        try {
            String quer = "SELECT a.server_id, a.maximum_cpu_usage, a.maximum_disk_usage, c.city, c.country, a.owner_id, d.nickname" +
                    "\nFROM servers a JOIN computers b ON a.computer_id = b.computer_id LEFT JOIN locations c ON b.location_id = c.location_id" +
                    "\nJOIN users d ON a.owner_id = d.user_id WHERE a.computer_id = ? ORDER BY 1";
            if(num != -1)
            {
                quer += " LIMIT " + ((Integer)num).toString();
            }

            PreparedStatement ps = connection.prepareStatement(quer);
            ps.setInt(1, computer);
            ResultSet rs = ps.executeQuery();
            ArrayList<Server> servers = new ArrayList<>();
            while (rs.next())
            {
                Server s = new Server();
                s.server_id = rs.getInt("server_id");
                s.maximum_cpu = rs.getInt("maximum_cpu_usage");
                s.maximum_disk = rs.getInt("maximum_disk_usage");
                s.city = rs.getString("city");
                s.country = rs.getString("country");
                s.owner_id = rs.getInt("owner_id");
                s.owner_nickname = rs.getString("nickname");

                servers.add(s);
            }
            System.out.println(servers.size());
            return servers.toArray(new Server[servers.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }

    public Transaction[] GetUserTransactions(int num, int user)
    {
        try
        {
            String s = "SELECT a.transaction_id, a.user_id, a.value, a.transaction_time, b.nickname, c.number, d.offer_id, d.server_id " +
                    "FROM transactions a JOIN users b ON a.user_id = b.user_id LEFT JOIN cards c ON a.card_id = c.card_id LEFT JOIN " +
                    "offer_purchases d ON a.transaction_id = d.transaction_id WHERE a.user_id = ? ORDER BY a.transaction_time DESC";
            if(num != -1)
            {
                s += " LIMIT " + num;
            }
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();
            ArrayList<Transaction> trs = new ArrayList<>();
            while(rs.next())
            {
                Transaction t = new Transaction();
                t.transaction_id = rs.getInt("transaction_id");
                t.offer_id = rs.getInt("offer_id");
                t.value = rs.getDouble("value");
                t.user_id = rs.getInt("user_id");
                t.server_id = rs.getInt("server_id");
                t.card_number = rs.getString("number");
                t.date = rs.getString("transaction_time");
                t.nickname = rs.getString("nickname");
                trs.add(t);
            }
            return trs.toArray(new Transaction[trs.size()]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Computer[] GetUserComputers(int num, int user)
    {
        try {
            String quer = "SELECT a.computer_id, a.owner_id, b.nickname, a.ram, a.disk_space, c.country, c.city\n" +
                    "FROM computers a JOIN users b ON a.owner_id = b.user_id JOIN locations c ON a.location_id = c.location_id\n" +
                    "WHERE a.owner_id = ? ORDER BY 1";
            if(num != -1)
            {
                quer += " LIMIT " + ((Integer)num).toString();
            }

            PreparedStatement ps = connection.prepareStatement(quer);
            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();
            ArrayList<Computer> computers = new ArrayList<>();
            while (rs.next())
            {
                Computer comp = new Computer();
                comp.computer_id = rs.getInt("computer_id");
                comp.owner_id = rs.getInt("owner_id");
                comp.ownerNickname = rs.getString("nickname");
                comp.ram = rs.getInt("ram");
                comp.disk = rs.getInt("disk_space");
                comp.country = rs.getString("country");
                comp.city = rs.getString("city");
                computers.add(comp);
            }
            System.out.println(computers.size());
            return computers.toArray(new Computer[computers.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }

    public Server GetServerInfo(int id)
    {
        try {
            String quer = "SELECT a.server_id, a.maximum_cpu_usage, a.maximum_disk_usage, b.cores, b.cpu_model, b.main_cpu_frequency, " +
                    "b.computer_id, c.city, c.country, c.location_name, e.name AS \"country_full\", a.owner_id, d.nickname" +
                    "\nFROM servers a JOIN computers b ON a.computer_id = b.computer_id LEFT JOIN locations c ON b.location_id = c.location_id" +
                    "\nJOIN users d ON a.owner_id = d.user_id LEFT JOIN country_codes e ON c.country = e.code WHERE a.server_id = ? ORDER BY 1";

            PreparedStatement ps = connection.prepareStatement(quer);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Server s = new Server();
            rs.next();
            s.server_id = rs.getInt("server_id");
            s.maximum_cpu = rs.getInt("maximum_cpu_usage");
            s.maximum_disk = rs.getInt("maximum_disk_usage");
            s.city = rs.getString("city");
            s.country = rs.getString("country");
            s.owner_id = rs.getInt("owner_id");
            s.owner_nickname = rs.getString("nickname");
            s.computer_id = rs.getInt("computer_id");
            s.cpu_model = rs.getString("cpu_model");
            s.main_cpu_frequency = rs.getDouble("main_cpu_frequency");
            s.organisation = rs.getString("location_name");
            s.country_full = rs.getString("country_full");
            s.cores_num = rs.getInt("cores");

            return s;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }


    public Computer GetComputerInfo(int id)
    {
        try {
            String quer = "SELECT a.computer_id, a.ram, a.cpu_model, a.main_cpu_frequency, a.cores, a.disk_space, a.owner_id,\n" +
                    "b.nickname, c.country, c.city, c.location_name, d.name AS \"country_full\" FROM " +
                    "computers a JOIN users b ON a.owner_id = b.user_id JOIN locations c ON a.location_id = c.location_id JOIN " +
                    "country_codes d ON c.country = d.code WHERE computer_id = ? ORDER BY 1";
            PreparedStatement ps = connection.prepareStatement(quer);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Computer s = new Computer();
            rs.next();
            s.computer_id = rs.getInt("computer_id");
            s.ram = rs.getInt("ram");
            s.disk = rs.getInt("disk_space");
            s.city = rs.getString("city");
            s.country = rs.getString("country");
            s.owner_id = rs.getInt("owner_id");
            s.ownerNickname = rs.getString("nickname");
            s.cpu_name = rs.getString("cpu_model");
            s.cpu_freq = rs.getDouble("main_cpu_frequency");
            s.organisation = rs.getString("location_name");
            s.country_full = rs.getString("country_full");
            s.cores = rs.getInt("cores");

            return s;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }

    public User GetUserInfo(int id)
    {
        try {
        String quer = "SELECT * FROM users a LEFT JOIN locations b ON a.location_id = b.location_id\n" +
                "LEFT JOIN country_codes c ON b.country = c.code WHERE a.user_id = ? LIMIT 1";
        PreparedStatement ps = connection.prepareStatement(quer);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        User u = new User();
        rs.next();
        u.city = rs.getString("city");
        u.country = rs.getString("country");
        u.country_full = rs.getString("name");
        u.organisation = rs.getString("location_name");
        u.email = rs.getString("email");
        u.firstName = rs.getString("first_name");
        u.secondName = rs.getString("second_name");
        u.nickname = rs.getString("nickname");
        u.gender = rs.getString("gender");
        u.user_id = rs.getInt("user_id");
        u.password = rs.getString("password");

        ps = connection.prepareStatement("SELECT status FROM statuses WHERE user_id = ? ORDER BY 1");
        ps.setInt(1, id);
        rs = ps.executeQuery();
        u.tags = new ArrayList<>();
        while(rs.next())
        {
            u.tags.add(rs.getString("status"));
        }

        return u;
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Error while executing the query.");
    }
        return null;
    }

    public boolean EditUserRights(int id)
    {
        try {
            String quer = "SELECT * FROM statuses WHERE (status = \'Admin\' OR status = \'Moderator\') AND user_id = ?";
            PreparedStatement ps = connection.prepareStatement(quer);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return false;
    }

    public Object[] GetOfferInfo(int id)
    {
        try {
            String checkQuery = "SELECT * FROM host_offers WHERE offer_id = ?";
            PreparedStatement ps = connection.prepareStatement(checkQuery);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String query = "SELECT a.offer_id, a.name, a.price, a.time_from, a.time_to, " +
                        "b.maximum_cpu_usage, b.maximum_disk_usage, b.location_id, c.country, c.city, c.location_name, d.name AS \"coutry_full\" " +
                        "FROM offers a JOIN host_offers b ON a.offer_id = b.offer_id LEFT JOIN locations c ON b.location_id = c.location_id " +
                        "LEFT JOIN country_codes d ON c.country = d.code WHERE a.offer_id = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                Object[] res = new Object[12];
                rs.next();
                for(int i = 0; i < 12; i++)
                {
                    res[i] = rs.getObject(i+1);
                }
                return res;
            }
            else
            {
                String query = "SELECT a.offer_id, a.name, a.price, a.time_from, a.time_to, " +
                        "b.server_id, b.owner_id, c.nickname " +
                        "FROM offers a JOIN server_offers b ON a.offer_id = b.offer_id " +
                        "LEFT JOIN users c ON b.owner_id = c.user_id WHERE a.offer_id = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                Object[] res = new Object[8];
                rs.next();
                for(int i = 0; i < 8; i++)
                {
                    res[i] = rs.getObject(i+1);
                }
                return res;
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while executing the query.");
        }
        return null;
    }

    public int GetServerOffer(int server)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT a.offer_id FROM server_offers a JOIN offers b " +
                    "ON a.offer_id = b.offer_id WHERE a.server_id = ? AND " +
                    "(b.time_to > current_timestamp+(SELECT * FROM timeshift LIMIT 1)::interval OR b.time_to IS NULL)");
            ps.setInt(1, server);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getInt("offer_id");
            }
            else
            {
                return -1;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while executing the query");
            return -1;
        }
    }

    public double OfferDiscount(int offer)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT offer_discount_percent(a.offer_id) FROM offers a WHERE a.offer_id = ?");
            ps.setInt(1, offer);
            ResultSet rs = ps.executeQuery();
            if(!rs.next())
                return 0;
            return rs.getDouble(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while executing the query");
            return 0;
        }
    }

    public double UserDiscount(int user)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT user_discount_percent(user_id) FROM users WHERE user_id = ?");
            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();
            if(!rs.next())
                return 0;
            return rs.getDouble(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while executing the query");
            return 0;
        }
    }

    public double OfferPrice(int user, int offer)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT offer_price(?, ?)");
            ps.setInt(1, user);
            ps.setInt(2, offer);
            ResultSet rs = ps.executeQuery();
            if(!rs.next())
                return 0;
            return rs.getDouble(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while executing the query");
            return 0;
        }
    }

    public int GetLocation(String country, String city, String org)
    {
        try {
            boolean f = true;
            String s = "SELECT location_id FROM locations WHERE";
            if(country != "")
            {
                if(!f)
                    s += " AND";
                s += " country = ?";
                f = false;
            }
            if(city != "")
            {
                if(!f)
                    s += " AND";
                s += " city = ?";
                f = false;
            }
            if(org != "")
            {
                if(!f)
                    s += " AND";
                s += " location_name = ?";
                f = false;
            }
            s += " LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(s);
            int ind = 1;
            if(country != "")
            {
                ps.setString(ind++, country);
            }
            if(city != "")
            {
                ps.setString(ind++, city);
            }
            if(org != "")
            {
                ps.setString(ind++, org);
            }
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getInt("location_id");
            }
            s = "INSERT INTO locations(";
            f = true;
            if(country != "")
            {
                if(!f)
                    s += ", ";
                s += "country";
                f = false;
            }
            if(city != "")
            {
                if(!f)
                    s += ", ";
                s += "city";
                f = false;
            }
            if(org != "")
            {
                if(!f)
                    s += ", ";
                s += "location_name";
                f = false;
            }
            s += ") VALUES (";
            f = true;
            if(country != "") {
                if (!f)
                    s += ", ";
                s += "?";
                f = false;
            }
            if(city != "")
            {
                if(!f)
                    s += ", ";
                s += "?";
                f = false;
            }
            if(org != "")
            {
                if(!f)
                    s += ", ";
                s += "?";
                f = false;
            }
            s += ")";
            ps = connection.prepareStatement(s);
            ind = 1;
            if(country != "")
                ps.setString(ind++, country);
            if(city != "")
                ps.setString(ind++, city);
            if(org != "")
                ps.setString(ind++, org);
            int r = ps.executeUpdate();
            if(r == 1)
            {
                rs = connection.prepareStatement("SELECT currval(\'locations_location_id_seq\')").executeQuery();
                rs.next();
                return rs.getInt(1);
            }
            return -1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public double UserBalance(int user)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT user_balance(?)");
            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();
            if(!rs.next())
                return 0;
            return rs.getDouble(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while executing the query");
            return 0;
        }
    }

    public boolean BuyOffer(int user, int offer)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT buy_offer(?, ?)");
            ps.setInt(1, user);
            ps.setInt(2, offer);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBoolean(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while executing the query");
            return false;
        }
    }

    public boolean PayUser(int user, int card, double value)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO transactions (user_id, card_id, value, transaction_time) " +
                    "VALUES (?, ?, ?, current_timestamp)");
            ps.setInt(1, user);
            ps.setInt(2, card);
            ps.setDouble(3, value);
            int res = ps.executeUpdate();
            if(res == 1)
                return true;
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while executing the query");
            return false;
        }
    }

    public Discount[] GetOfferDiscounts(int num)
    {
        try
        {
            String s = "SELECT * FROM offer_discounts_info";
            if(num != -1)
            {
                s += " LIMIT " +  (Integer)num;
            }
            PreparedStatement ps = connection.prepareStatement(s);
            ResultSet rs = ps.executeQuery();
            ArrayList<Discount> ds = new ArrayList<>();
            while(rs.next())
            {
                Discount d = new Discount();
                d.discount_id = rs.getInt("discount_id");
                d.offer_id = rs.getInt("offer_id");
                d.percent = rs.getDouble("percent");
                d.time_from = rs.getString("time_from");
                d.time_to = rs.getString("time_to");
                d.offer_name = rs.getString("name");
                ds.add(d);
            }
            return ds.toArray(new Discount[ds.size()]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while executing the query");
            return null;
        }
    }

    public boolean SellServer(int user, int server, double price, String name)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT sell_server(?, ?, ?, ?)");
            ps.setInt(1, user);
            ps.setInt(2, server);
            ps.setObject(3, price, Types.NUMERIC);
            ps.setString(4, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getBoolean(1))
                return true;
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CancelSell(int offer)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT cancel_offer(?)");
            ps.setInt(1, offer);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getBoolean(1))
                return true;
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public Discount[] GetAllDiscounts()
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT a.discount_id, b.percent, b.time_from, b.time_to, a.offer_id, c.name FROM\n" +
                    "offer_discounts a JOIN discounts b ON a.discount_id = b.discount_id\n" +
                    "JOIN offers c ON a.offer_id = c.offer_id ORDER BY a.offer_id;\n");
            ArrayList<Discount> dscs = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Discount ds = new Discount();
                ds.offer_id = rs.getInt("offer_id");
                ds.offer_name = rs.getString("name");
                ds.discount_id = rs.getInt("discount_id");
                ds.percent = rs.getDouble("percent");
                ds.time_from = rs.getString("time_from");
                ds.time_to = rs.getString("time_to");
                ds.nickname = null;
                ds.user_id = null;
                dscs.add(ds);
            }

            ps = connection.prepareStatement("SELECT a.discount_id, b.percent, b.time_from, b.time_to, a.user_id, c.nickname FROM\n" +
                    "user_discounts a JOIN discounts b ON a.discount_id = b.discount_id\n" +
                    "JOIN users c ON a.user_id = c.user_id ORDER BY a.user_id");
            rs = ps.executeQuery();
            while(rs.next())
            {
                Discount ds = new Discount();
                ds.offer_id = null;
                ds.offer_name = null;
                ds.discount_id = rs.getInt("discount_id");
                ds.percent = rs.getDouble("percent");
                ds.time_from = rs.getString("time_from");
                ds.time_to = rs.getString("time_to");
                ds.nickname = rs.getString("nickname");
                ds.user_id = rs.getInt("user_id");
                dscs.add(ds);
            }
            return dscs.toArray(new Discount[dscs.size()]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while retrieving data");
            return null;
        }
    }

    public Discount GetDiscountInfo(int discount)
    {
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM user_discounts WHERE discount_id = ?");
            ps.setInt(1, discount);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                ps = connection.prepareStatement("SELECT a.discount_id, b.percent, b.time_from, b.time_to, a.user_id, c.nickname FROM\n" +
                        "user_discounts a JOIN discounts b ON a.discount_id = b.discount_id\n" +
                        "JOIN users c ON a.user_id = c.user_id WHERE a.discount_id = ?");
                ps.setInt(1, discount);
                rs = ps.executeQuery();
                rs.next();
                Discount ds = new Discount();
                ds.offer_id = null;
                ds.offer_name = null;
                ds.discount_id = discount;
                ds.percent = rs.getDouble("percent");
                ds.time_from = rs.getString("time_from");
                ds.time_to = rs.getString("time_to");
                ds.nickname = rs.getString("nickname");
                ds.user_id = rs.getInt("user_id");
                return ds;
            }
            else
            {
                ps = connection.prepareStatement("SELECT a.discount_id, b.percent, b.time_from, b.time_to, a.offer_id, c.name FROM\n" +
                        "offer_discounts a JOIN discounts b ON a.discount_id = b.discount_id\n" +
                        "JOIN offers c ON a.offer_id = c.offer_id WHERE a.discount_id = ?");
                ps.setInt(1, discount);
                rs = ps.executeQuery();
                rs.next();
                Discount ds = new Discount();
                ds.offer_id = rs.getInt("offer_id");
                ds.offer_name = rs.getString("name");
                ds.discount_id = discount;
                ds.percent = rs.getDouble("percent");
                ds.time_from = rs.getString("time_from");
                ds.time_to = rs.getString("time_to");
                ds.nickname = null;
                ds.user_id = null;
                return ds;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Something went wrong while retrieving data");
            return null;
        }
    }
}
