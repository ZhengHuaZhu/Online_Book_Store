/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.User;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 *
 * @author 1231636
 */
@RunWith(Arquillian.class)
public class UserDAOImpTest {
    
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
                .addPackage(UserDAOImpl.class.getPackage())
                .addPackage(User.class.getPackage())
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
    private UserDAOImpl userDAO;
    
    public UserDAOImpTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("script.sql");

        try (Connection connection = ds.getConnection()) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed seeding database", e);
        }
        //System.out.println("Seeding works");

    }

    /**
     * The following methods support the seedDatabse method
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path)) {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader,
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
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
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class UserDAOImp.
     */
    @Ignore
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        User u = new User();
        u.setEmail("mehdi.moodi@gmail.com");
        u.setPassword("09153171567");
        u.setFname("Mehdi");
        u.setLname("Moodi");
        u.setAddress1("645 Elm Blv.");
        u.setCity("Montreal");
        u.setProvince("QC");
        u.setCountry("Canada");
        u.setPostalcode("G4F Y5S");
        int id = userDAO.create(u);
        assertNotEquals(0,id);        
    }

    /**
     * Test of findAll method, of class UserDAOImp.
     */
    @Ignore
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        ArrayList<User> users = userDAO.findAll();
        assertEquals(users.size(), 10);        
    }

    /**
     * Test of findByEmail method, of class UserDAOImp.
     */
    @Ignore
    @Test
    public void testFindByEmail() throws Exception {
        System.out.println("findByEmail");
        String email = "send1231636@gmail.com";
        //User result = userDAO.findByEmail(email);
        //assertEquals("Mehdi", result.getFname());        
    }

    /**
     * Test of findByID method, of class UserDAOImp.
     */
    @Ignore
    @Test
    public void testFindByID() throws Exception {
        System.out.println("findByID");
        User result = userDAO.findByID(1);
        assertEquals("Mehdi", result.getFname());        
    }

    /**
     * Test of update method, of class UserDAOImp.
     */
    //@Ignore
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        User user = userDAO.findByID(1);
        user.setGenre("Cooking");
        int result = userDAO.update(user);
        assertEquals(1, result);        
    }

    /**
     * Test of delete method, of class UserDAOImp.
     */
    @Ignore
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        User u = new User();
        u.setEmail("mehdi.moodi@gmail.com");
        u.setPassword("09153171567");
        u.setFname("Mehdi");
        u.setLname("Moodi");
        u.setAddress1("645 Elm Blv.");
        u.setCity("Montreal");
        u.setProvince("QC");
        u.setCountry("Canada");
        u.setPostalcode("G4F Y5S");
        int id = userDAO.create(u);
        int result = userDAO.delete(id);
        assertEquals(1, result);        
    }
    
}
