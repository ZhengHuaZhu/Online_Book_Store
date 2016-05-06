package com.group2.bambootemple.persistence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import com.group2.bambootemple.bean.entity.Inventory;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
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
import org.junit.runner.RunWith;

/**
 *
 * @author Julien Comtois
 */
@RunWith(Arquillian.class)
public class InventoryDAOImplTest {

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
                .addPackage(InventoryDAOImpl.class.getPackage())
                .addPackage(Inventory.class.getPackage())
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
    private InventoryDAOImpl inventoryDAO;

    public InventoryDAOImplTest() {
    }

    @Test
    public void testCreateAndFindByBookId() throws Exception {
        Inventory book = new Inventory();
        book.setIsbn("9780262033841");
        book.setTitle("Introduction to Algorithms");
        book.setNumPages(1312);
        book.setAuthor("Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein");
        book.setPublisher("The MIT Press");
        book.setGenre("Computers & Technology");
        book.setEFormat("eReader, PDF");
        book.setDescription("Test Description");
        book.setLPrice(new BigDecimal("110.05"));
        book.setWPrice(new BigDecimal("55.02"));
        book.setSPrice(new BigDecimal(0));
        int bookid = inventoryDAO.create(book);
        book.setBookId(bookid);
        assertThat(inventoryDAO.findByBookId(bookid), is(book));
    }
    
    @Test
    public void testFindByIsbn() throws Exception {
        Inventory book = inventoryDAO.findByBookId(7);
        assertThat(inventoryDAO.findByIsbn("9780316389709"), is(book));
    }

    @Test
    public void testFindByTitle() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(1);
        //books.remove(2);
        List<Inventory> books = new ArrayList<>();
        books.add(inventoryDAO.findByBookId(2)); 
        assertThat(inventoryDAO.findByTitle("shadow"), is(books));
    }

    @Test
    public void testFindByNumPages() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(0);
        List<Inventory> books = new ArrayList<>();
        books.add(inventoryDAO.findByBookId(8)); 
        books.add(inventoryDAO.findByBookId(36)); 
        books.add(inventoryDAO.findByBookId(43)); 
        books.add(inventoryDAO.findByBookId(55)); 
        books.add(inventoryDAO.findByBookId(92)); 
        assertThat(inventoryDAO.findByNumPages(380, 399), is(books));
    }

    @Test
    public void testFindByAuthor() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(1);
        List<Inventory> books = new ArrayList<>();
        books.add(inventoryDAO.findByBookId(7));        
        books.add(inventoryDAO.findByBookId(8));        
        books.add(inventoryDAO.findByBookId(11));        
        assertThat(inventoryDAO.findByAuthor("Sapkowski"), is(books));
    }

    @Test
    public void testFindByPublisher() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(1);
        List<Inventory> books = new ArrayList<>();
        books.add(inventoryDAO.findByBookId(6));  
        assertThat(inventoryDAO.findByPublisher("broadway"), is(books));
    }

    @Test
    public void testFindByGenre() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(1);
        Inventory book = inventoryDAO.findByBookId(20);
        book.setGenre("Computers & Technology");
        inventoryDAO.update(book);
        List<Inventory> books = new ArrayList<>();
        books.add(inventoryDAO.findByBookId(20));
        assertThat(inventoryDAO.findByGenre("Technology"), is(books));
    }

    //@Test
    public void testFindByEFormat() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(0);
        //books.remove(1);
        List<Inventory> books = new ArrayList<>();
        assertThat(inventoryDAO.findByEFormat("ePub"), is(books));
    }

    @Test
    public void testFindByWholesalePrice() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(2);
        List<Inventory> books = new ArrayList<>();
        books.add(inventoryDAO.findByBookId(21));  
        books.add(inventoryDAO.findByBookId(43));  
        assertThat(inventoryDAO.findByWholesalePrice(new BigDecimal("10.99"), new BigDecimal("12.99")), is(books));
    }

    @Test
    public void testFindBySalePrice() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(0);
        Inventory book = inventoryDAO.findByBookId(84);
        book.setSPrice(new BigDecimal("11.49"));
        inventoryDAO.update(book);
        List<Inventory> books = new ArrayList<>();
        books.add(inventoryDAO.findByBookId(84));
        assertThat(inventoryDAO.findBySalePrice(new BigDecimal("10.99"), new BigDecimal("11.99")), is(books));
    }

    @Test
    public void testFindByListPrice() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(1);
        //books.remove(2);
        List<Inventory> books = new ArrayList<>();
        books.add(inventoryDAO.findByBookId(20));  
        books.add(inventoryDAO.findByBookId(53));  
        books.add(inventoryDAO.findByBookId(67));  
        books.add(inventoryDAO.findByBookId(71));  
        assertThat(inventoryDAO.findByListPrice(new BigDecimal("10.99"), new BigDecimal("11.99")), is(books));
    }

    @Test
    public void testFindByRemovalStatusAndDelete() throws Exception {
        //List<Inventory> books = createBooks();
        //books.remove(0);
        //books.remove(1);
        inventoryDAO.delete(100);
        List<Inventory> books = new ArrayList<>();
        books.add(inventoryDAO.findByBookId(100)); 
        assertThat(inventoryDAO.findByRemovalStatus(true), is(books));
    }

    @Test
    public void testUpdate() throws Exception {
        Inventory book = inventoryDAO.findByBookId(1);
        book.setSPrice(new BigDecimal("24.99"));
        inventoryDAO.update(book);
        assertThat(inventoryDAO.findByBookId(1), is(book));
    }

    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("script.sql");
        List<String> list = splitStatements(new StringReader(seedDataScript), ";");
        try (Connection connection = ds.getConnection()) {
            for (String statement : list) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed seeding database", e);
        }
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

    /*private Inventory createBook() throws Exception {
        Inventory book = new Inventory();
        book.setIsbn(9780262033848L);
        book.setTitle("Introduction to Algorithms");
        book.setNumPages(1312);
        book.setAuthor("Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein");
        book.setPublisher("The MIT Press");
        book.setGenre("Computers & Technology");
        book.setEFormat("eReader, PDF");
        book.setDescription("Test Description");
        book.setLPrice(new BigDecimal("110.05"));
        book.setSPrice(new BigDecimal("79.99"));
        book.setWPrice(new BigDecimal("55.02"));
        inventoryDAO.create(book);
        return book;
    }

    private List<Inventory> createBooks() throws Exception {
        List<Inventory> books = new ArrayList<>();
        log.info(books.toString());
        Inventory book = new Inventory();
        int bookid = 0;
        book.setIsbn(9780262033849L);
        book.setTitle("Introduction to Algorithms");
        book.setNumPages(1312);
        book.setAuthor("Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein");
        book.setPublisher("The MIT Press");
        book.setGenre("Computers & Technology");
        book.setEFormat("eReader, PDF");
        book.setDescription("Test Description");
        book.setLPrice(new BigDecimal("110.05"));
        book.setSPrice(new BigDecimal("79.99"));
        book.setWPrice(new BigDecimal("55.02"));
        bookid = inventoryDAO.create(book);
        book.setBookId(bookid);
        books.add(book);
        bookid = 0;
        book = new Inventory();
        book.setIsbn(9780132350884L);
        book.setTitle("Clean Code: A Handbook of Agile Software Craftsmanship");
        book.setNumPages(464);
        book.setAuthor("Robert C. Martin");
        book.setPublisher("Prentice Hall");
        book.setGenre("Computers & Technology");
        book.setEFormat("eReader, MOBI");
        book.setDescription("Test Description with more words");
        book.setLPrice(new BigDecimal("38.07"));
        book.setSPrice(new BigDecimal("29.99"));
        book.setWPrice(new BigDecimal("19.03"));
        bookid = inventoryDAO.create(book);
        book.setBookId(bookid);
        books.add(book);
        bookid = 0;
        book = new Inventory();
        book.setIsbn(9780262121026L);
        book.setTitle("Area-Efficient VLSI Computation");
        book.setNumPages(148);
        book.setAuthor("Charles E. Leiserson");
        book.setPublisher("The MIT Press");
        book.setGenre("Computers & Technology");
        book.setEFormat("ePub, MOBI");
        book.setDescription("Test Description with more words");
        book.setLPrice(new BigDecimal("29.69"));
        book.setSPrice(new BigDecimal("23.99"));
        book.setWPrice(new BigDecimal("14.84"));
        book.setRemovalStatus(true);
        bookid = inventoryDAO.create(book);
        book.setBookId(bookid);
        books.add(book);
        log.info(books.toString());
        return books;
    }*/
}
