/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Item;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
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
public class ItemDAOImplTest {

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
                .addPackage(ItemDAOImpl.class.getPackage())
                .addPackage(Item.class.getPackage())
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
    private ItemDAOImpl instance;

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
     * Test of create method, of class ItemDAOImpl.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Item ib = new Item();
        ib.setOrderID(5);
        ib.setBookID(10);
        ib.setGst(5.0);
        int expResult = 1;
        int result = instance.create(ib);
        assertEquals(expResult, result);
    }

    /**
     * Test of findByOrderID method, of class ItemDAOImpl.
     */
    @Test
    public void testFindByOrderID() throws Exception {
        System.out.println("findByOrderID");
        int id = 1;
        List<Item> expResult = new ArrayList<>();
        Item ib1 = new Item();
        ib1.setItemID(1);
        ib1.setOrderID(1);
        ib1.setBookID(100);
        ib1.setPrice(17.61);
        ib1.setGst(5.0);
        expResult.add(ib1);
        Item ib2 = new Item();
        ib2.setItemID(2);
        ib2.setOrderID(1);
        ib2.setBookID(99);
        ib2.setPrice(18.95);
        ib2.setGst(5.0);
        expResult.add(ib2);
        Item ib3 = new Item();
        ib3.setItemID(3);
        ib3.setOrderID(1);
        ib3.setBookID(98);
        ib3.setPrice(17.99);
        ib3.setGst(5.0);
        expResult.add(ib3);
        
        List<Item> result = instance.findByOrderID(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findByID method, of class ItemDAOImpl.
     */
    @Test
    public void testFindByID() throws Exception {
        System.out.println("findByID");
        int id = 10;
        Item expResult = new Item(10,4,30,30.0,0.0,5.0,0.0);
        Item result = instance.findByID(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class ItemDAOImpl.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Item ib = new Item(15,4,80,12.71,0,5,0);
        int expResult = 1;
        int result = instance.update(ib);
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class ItemDAOImpl.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        int id = 15;
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
        int expResult = 15;
        List<Item> result = instance.findAll();
        assertEquals(expResult, result.size());
    }
    
}
