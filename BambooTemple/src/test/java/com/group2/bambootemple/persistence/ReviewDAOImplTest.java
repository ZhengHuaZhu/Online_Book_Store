/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Review;
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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 *
 * @author Marjorie Morales
 */
@RunWith(Arquillian.class)
public class ReviewDAOImplTest {

    //private final Logger log = LoggerFactory.getLogger(this.getClass()
    //        .getName());    
    
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
                .addPackage(ReviewDAOImpl.class.getPackage())
                .addPackage(Review.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/setup/glassfish-resources.xml"), 
                        "glassfish-resources.xml")
                .addAsResource("script.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Resource(name = "java:app/jdbc/myGroup2")
    private DataSource reviewDataSource;

    @Inject
    private ReviewDAOImpl reviewDAO;
    
    @Before
    public void  seedDatabase() {
	final String seedDataScript = loadAsString("script.sql");
            try (Connection connection = reviewDataSource.getConnection();) {
		for (String statement : splitStatements(new StringReader(seedDataScript), ";")) {
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
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            Scanner scanner = new Scanner(inputStream)) {
		return scanner.useDelimiter("\\A").next();
            } catch (IOException e) {
		throw new RuntimeException("Unable to close input stream.", e);
            }
	}
    private List<String> splitStatements(Reader reader, String statementDelimiter) {
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
		return line.startsWith("--") || line.startsWith("//") || line.startsWith("/*");
	}         
        /**
         * This test the create method, which creates a row on review table.
         * @throws SQLException 
         */
	@Test
	public void testCreate() throws SQLException {
            //log.info("Testing create() -- Test 1");
            Review review = new Review();
            review.setBookId(10);
            review.setUserId(1);
            review.setRate(5);
            review.setComment("Great characters and so well written. A joy to read! I recommend it highly.");	
            int result = reviewDAO.create(review);
            assertEquals("created 1 record: ", 1, result);
	}          

        /**
         * This  test findById method, which looks for a specific review. 
         * @throws SQLException 
         */
        @Test
	public void testFindById() throws SQLException {
        //log.info("Testing findById() -- Test 1:");
            Review review = new Review();	
            review = reviewDAO.findById(1);
            assertNotNull(review);
	}

        /**
         * This test the findByBookId method, which find all the reviews 
         * written for a specific book.
         * @throws SQLException 
         */
        @Test
	public void testFindByBookId() throws SQLException {
        //log.info("Testing  findByBookId() -- Test 1");
            List<Review> reviews = new ArrayList<Review>();	
            reviews = reviewDAO.findByBookId(15);
            int result = reviews.size();
            assertEquals("found 1 record: ", 1, result);
	}
        
        /**
         * This test the findByBookId method, which find all the reviews 
         * written for a not existing book on database.
         * @throws SQLException 
         */
        @Test
	public void testFindByBookId2() throws SQLException {
        //log.info("Testing  findByBookId() -- Test 2");
            List<Review> reviews = new ArrayList<Review>();	
            reviews = reviewDAO.findByBookId(1110);
            int result = reviews.size();
            assertEquals("found 0 records: ", 0, result);
	}
        
        /**
         * This test the findReviewToBeApprove method, which find all the 
         * reviews that needs an approval.
         * @throws SQLException 
         */
        @Test
	public void testFindReviewsToBeApprove() throws SQLException {
        //log.info("Testing  findReviewsToBeApprove() -- Test 1");
            List<Review> reviews = new ArrayList<Review>();	
            reviews = reviewDAO.findReviewsToBeApprove();
            int result = reviews.size();
            assertEquals("found 5 records: ", 5, result);
	}
        
        /**
         * This test the updateComment method, which updates a review's comment. 
         * @throws SQLException 
         */
        @Test
	public void testUpdateComment() throws SQLException {
        //log.info("Testing  updateComment() -- Test 1");
        Review review = reviewDAO.findById(1);
        review.setComment("I highly recommend this book");
            int result = reviewDAO.update(review);
            assertEquals("found 1 record: ", 1, result);
	}
        /**
         * This test the updateStatus method, which updates a review's 
         * approval_status into 1(true). 
         * @throws SQLException 
         */
        @Test
	public void testUpdateStatus() throws SQLException {
        //log.info("Testing  updateStatus() -- Test 1");
            int result = reviewDAO.updateStatus(1,1);
            assertEquals("found 1 record: ", 1, result);
	}
        /**
         * This test the delete method, which deletes a review. 
         * @throws SQLException 
         */
        @Test
	public void testDelete() throws SQLException {
        //log.info("Testing  updateStatus() -- Test 1");
            int result = reviewDAO.delete(3);
            assertEquals("found 1 record: ", 1, result);
	}
}
