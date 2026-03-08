package com.bookshop.locadora;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseTest {
    static Connection connection;
    //BeforeAll DEVE ser colocado em método static

    @BeforeAll
    static void setUpDataBase()throws Exception{
        connection = DriverManager
                .getConnection("jdbc:h2:mem:testdb","sa","");
        connection.createStatement().execute("CREATE TABLE users (id INT,name VARCHAR)");
    }

    @BeforeEach
    void insertUserTest() throws Exception{
        connection.createStatement().execute("INSERT INTO users(id,name) values(1,'alberto')");
    }

    @Test
    void testUserExists() throws Exception{
        var result = connection.createStatement().execute("SELECT * FROM users WHERE id = 1");

        Assertions.assertTrue(result);
    }

    @Test
    @Disabled
    void testUserExistsDisabled() throws Exception{
        var result = connection.createStatement().execute("SELECT * FROM users WHERE id = 1");

        Assertions.assertTrue(result);
    }

    @AfterAll
    static void closeConnection() throws Exception{
        connection.close();
    }
}
