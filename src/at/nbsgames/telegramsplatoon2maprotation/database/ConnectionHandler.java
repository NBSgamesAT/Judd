package at.nbsgames.telegramsplatoon2maprotation.database;

import at.nbsgames.telegramsplatoon2maprotation.ListenerDiscord;

import java.sql.*;

public class ConnectionHandler {

    private Connection con = null;
    //5 LAPTOP DRIP 2 hulu PARK fruit ZIP hulu 2
    //splatoon2bot
    //jdbc:mysql://nbsgames.at:3306/Splatoon2Rotation

    private String connection;
    private String username;
    private String password;

    public static boolean gotData = false;
    public static long serverId = -1;
    public static long channelId = -1;

    public ConnectionHandler(String connection, String username, String password) {

        this.connection = connection;
        this.username = username;
        this.password = password;

        SqlResult result = null;
        try {
            System.out.println("DATABASE: Trying to load Database conneciton Driver...");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("DATABASE: Loaded successfully.");
            this.con = DriverManager.getConnection(connection, username, password);
            System.out.println("DATABASE: Build up connection. Ask for important information!");
            result = this.getData("SELECT `UsedServer`, `UsedChannel` FROM `Important` WHERE `Description` = 'errlog' ;");
            if (result.getResultSet().next()) {
                long usedServer = result.getResultSet().getLong("UsedServer");
                long usedChannel = result.getResultSet().getLong("UsedChannel");
                ListenerDiscord.getErrorChannel(usedServer, usedChannel);
            } else {
                System.out.println("DATABASE: Info for errlog Channel not found. Please use ;errlog in a channel where the bot has access to.");
            }
            result.closeResources();
        } catch (Exception e) {
            ListenerDiscord.printError(e);
            if (result != null) {
                result.closeResources();
            }
        }
    }

    public void sendSql(String sqlCommand, Object... params) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (!(!this.con.isClosed() && this.con.isValid(100))) {
                this.con = DriverManager.getConnection(connection, username, password);
                System.out.println("DATABASE: Reconnection issued!");
            }
            statement = this.con.prepareStatement(sqlCommand);
            for (int count = 0; count < params.length; count++) {
                if (params[count] instanceof String) {
                    statement.setString(count + 1, (String) params[count]);
                } else if (params[count] instanceof Byte) {
                    statement.setByte(count + 1, (byte) params[count]);
                } else if (params[count] instanceof Short) {
                    statement.setShort(count + 1, (short) params[count]);
                } else if (params[count] instanceof Integer) {
                    statement.setInt(count + 1, (int) params[count]);
                } else if (params[count] instanceof Long) {
                    statement.setLong(count + 1, (long) params[count]);
                } else if (params[count] instanceof Float) {
                    statement.setFloat(count + 1, (float) params[count]);
                } else if (params[count] instanceof Double) {
                    statement.setDouble(count + 1, (double) params[count]);
                } else if (params[count] instanceof Boolean) {
                    statement.setDouble(count + 1, (double) params[count]);
                } else {
                    statement.setObject(count + 1, params[count]);
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.closeResources(statement, null);
        }
    }

    public void closeConnection() {
        try {
            System.out.println("SQL: Closing connection!");
            this.con.close();
            System.out.println("SQL: Connection closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SqlResult getData(String sqlCommand, Object... params) throws SQLException {
        PreparedStatement statement = null;
        try {
            if (!(!this.con.isClosed() && this.con.isValid(100))) {
                this.con = DriverManager.getConnection(connection, username, password);
                System.out.println("DATABASE: Reconnection issued!");
            }
            statement = this.con.prepareStatement(sqlCommand);
            for (int count = 0; count < params.length; count++) {
                if (params[count] instanceof String) {
                    statement.setString(count + 1, (String) params[count]);
                } else if (params[count] instanceof Byte) {
                    statement.setByte(count + 1, (byte) params[count]);
                } else if (params[count] instanceof Short) {
                    statement.setShort(count + 1, (short) params[count]);
                } else if (params[count] instanceof Integer) {
                    statement.setInt(count + 1, (int) params[count]);
                } else if (params[count] instanceof Long) {
                    statement.setLong(count + 1, (long) params[count]);
                } else if (params[count] instanceof Float) {
                    statement.setFloat(count + 1, (float) params[count]);
                } else if (params[count] instanceof Double) {
                    statement.setDouble(count + 1, (double) params[count]);
                } else if (params[count] instanceof Boolean) {
                    statement.setDouble(count + 1, (double) params[count]);
                } else {
                    statement.setObject(count + 1, params[count]);
                }
            }
            ResultSet set = statement.executeQuery();
            return new SqlResult(statement, set);
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean canFind(String sqlCommand, Object... params) throws SQLException {
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            if (!(!this.con.isClosed() && this.con.isValid(100))) {
                this.con = DriverManager.getConnection(connection, username, password);
                System.out.println("DATABASE: Reconnection issued!");
            }
            statement = this.con.prepareStatement(sqlCommand);
            for (int count = 0; count < params.length; count++) {
                if (params[count] instanceof String) {
                    statement.setString(count + 1, (String) params[count]);
                } else if (params[count] instanceof Byte) {
                    statement.setByte(count + 1, (byte) params[count]);
                } else if (params[count] instanceof Short) {
                    statement.setShort(count + 1, (short) params[count]);
                } else if (params[count] instanceof Integer) {
                    statement.setInt(count + 1, (int) params[count]);
                } else if (params[count] instanceof Long) {
                    statement.setLong(count + 1, (long) params[count]);
                } else if (params[count] instanceof Float) {
                    statement.setFloat(count + 1, (float) params[count]);
                } else if (params[count] instanceof Double) {
                    statement.setDouble(count + 1, (double) params[count]);
                } else if (params[count] instanceof Boolean) {
                    statement.setDouble(count + 1, (double) params[count]);
                } else {
                    statement.setObject(count + 1, params[count]);
                }
            }
            set = statement.executeQuery();
            return set.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.closeResources(statement, set);
        }
    }

    private void closeResources(PreparedStatement statement, ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}