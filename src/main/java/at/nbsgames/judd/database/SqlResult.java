package at.nbsgames.judd.database;

import at.nbsgames.judd.ListenerDiscord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlResult {

    SqlResult(PreparedStatement statement, ResultSet resultSet){
        this.statement = statement;
        this.resultSet = resultSet;
    }

    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public void closeResources(){
        if (this.statement != null) {
            try {
                this.statement.close();
            } catch (SQLException e) {
                ListenerDiscord.printError(e);
            }
        }
        if (this.resultSet != null) {
            try {
                this.resultSet.close();
            } catch (SQLException e) {
                ListenerDiscord.printError(e);
            }
        }
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    @Override
    public void finalize(){
        if (this.statement != null) {
            try {
                this.statement.close();
            } catch (SQLException e) {
                ListenerDiscord.printError(e);
            }
        }
        if (this.resultSet != null) {
            try {
                this.resultSet.close();
            } catch (SQLException e) {
                ListenerDiscord.printError(e);
            }
        }
    }

}
