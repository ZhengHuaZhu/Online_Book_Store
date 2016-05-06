/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author 1334262
 */
@RunWith(Arquillian.class)
public class OrderDAOImplTest {
    
//    private final String host = "jdbc:mysql://localhost:3306/group2";
//    private final String user = "zheng";
//    private final String password = "zheng";
    
    @Deployment
    public static WebArchive deploy() {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();

        // The webArchive is the special packaging of your project
        // so that only the test cases run on the server or embedded
        // container
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(OrderDAOImpl.class.getPackage())
                .addPackage(Order.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/setup/glassfish-resources.xml"), 
                        "glassfish-resources.xml")
                .addAsResource("script.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource ds;

    @Inject
    private OrderDAOImpl instance;

    /**
     * This routine recreates the database for every test. This makes sure that
     * a destructive test will not interfere with any other test.
     *
     * This routine is courtesy of Bartosz Majsak, an Arquillian developer at
     * JBoss who helped me out last winter with an issue with Arquillian. Look
     * up Arquillian to learn what it is.
     */
    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("script.sql");
        try (Connection connection = ds.getConnection();) {
            for (String statement : splitStatements(
                    new StringReader(seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    }

    /**
     * The following methods support the seedDatabse method
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread().
                getContextClassLoader().getResourceAsStream(path);
                Scanner scanner = new Scanner(inputStream)) {
            return scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader, 
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<String>();
        try {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//") || 
                line.startsWith("/*");
    }

    /**
     * Test of create method, of class OrderDAOImpl.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Order ob = new Order(-1,7,LocalDateTime.of(2016, 1, 10, 10, 10, 10));
        int expResult = 1;
        int result = instance.create(ob);
        assertEquals(expResult, result);
    }

    /**
     * Test of findByUserID method, of class OrderDAOImpl.
     */
    @Test
    public void testFindByUserID() throws Exception {
        System.out.println("findByUserID");
        Order ob = new Order(-1,1,LocalDateTime.of(2016, 1, 10, 10, 10, 10));
        int id = 1;
        instance.create(ob);
        List<Order> expResult = new ArrayList<>();
        expResult.add(new Order(1,1,LocalDateTime.of(2013, 8, 5, 18, 19, 3)));
        expResult.add(new Order(6,1,LocalDateTime.of(2016, 1, 10, 10, 10, 10)));
        List<Order> result = instance.findByUserID(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findByID method, of class OrderDAOImpl.
     */
    @Test
    public void testFindByID() throws Exception {
        System.out.println("findByID");
        int id = 5;
        Order expResult = new Order(5,5,LocalDateTime.of(2011, 7, 25, 8, 9, 0));
        Order result = instance.findByID(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class OrderDAOImpl.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Order ob = new Order(2, 8, LocalDateTime.of(2016, 1, 10, 10, 10, 10));
        int expResult = 1;
        int result = instance.update(ob);
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class OrderDAOImpl.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        int id = 4;
        int expResult = 1;
        int result = instance.delete(id);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of findAll method, of class ItemDAOImpl.
     */
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        int expResult = 5;
        List<Order> result = instance.findAll();
        assertEquals(expResult, result.size());
    }
    
}
