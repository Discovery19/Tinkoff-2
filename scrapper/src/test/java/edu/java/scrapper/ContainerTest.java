package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContainerTest extends IntegrationTest{
    @Test
    public void testContainerStartupAndMigration() throws SQLException {
        //проверка запуска контейнера
        assertTrue(POSTGRES.isRunning());

        Connection connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet rs = metaData.getTables(connection.getCatalog(), null, "chats", new String[]{"TABLE"});
        //проверка существования таблицы chats
        assertTrue(rs.next());
        rs.close();

        rs = metaData.getTables(connection.getCatalog(), null, "links", new String[]{"TABLE"});
        //проверка существования таблицы links
        assertTrue(rs.next());
        rs.close();

        rs = metaData.getTables(connection.getCatalog(), null, "link_chat", new String[]{"TABLE"});
        //проверка существования таблицы link_chat
        assertTrue(rs.next());
        rs.close();

        //POSTGRES.stop();
    }
}

