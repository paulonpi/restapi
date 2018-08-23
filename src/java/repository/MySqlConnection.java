package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author paulo
 */
public class MySqlConnection {

    private static Connection connection = null;
    public static String status = "Não conectou...";

    public MySqlConnection() {
    }

    public static java.sql.Connection getConexaoMySQL() {

        if (connection == null) {
            try {
                String driverName = "com.mysql.jdbc.Driver";
                Class.forName(driverName);
                String serverName = "opmy0009.servidorwebfacil.com:3306";
                String mydatabase = "nas1sistem_paulo";

                String url = "jdbc:mysql://" + serverName + "/" + mydatabase + 
                        "?useTimezone=true&serverTimezone=UTC";
                String username = "nas1s_paulo";
                String password = "9Tfl9h*8";

                connection = DriverManager.getConnection(url, username, password);

                if (connection != null) {
                    status = ("STATUS--->Conectado com sucesso!");
                } else {
                    status = ("STATUS--->Não foi possivel realizar conexão");
                }

                return connection;

            } catch (ClassNotFoundException e) {  //Driver não encontrado

                System.out.println("O driver expecificado nao foi encontrado.");
                return null;

            } catch (SQLException e) {

                System.out.println("Nao foi possivel conectar ao Banco de Dados.");
                return null;
            }
        } 
        else 
            return connection;

    }

    public static String statusConection() {
        return status;
    }

    public static boolean FecharConexao() {
        try {

            MySqlConnection.getConexaoMySQL().close();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public static java.sql.Connection ReiniciarConexao() {
        FecharConexao();
        return MySqlConnection.getConexaoMySQL();
    }
    
    public static PreparedStatement getPreparedStatement(String sql, String[] key){
        if (connection == null){
            // cria a conexão
            connection = getConexaoMySQL();
        }
        try {
            // retorna um objeto java.sql.PreparedStatement
            return connection.prepareStatement(sql, key);
        } catch (SQLException e){
            System.out.println("Erro de sql: " + e.getMessage());
        }
        
        return null;
    }
}
