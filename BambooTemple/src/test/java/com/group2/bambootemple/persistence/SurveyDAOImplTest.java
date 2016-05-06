/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.persistence;

import com.group2.bambootemple.bean.entity.Survey;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class tests the Survey DAO. All methods in the DAO are tested and function as designed. 
 *
 * @author Derek Herbert
 */
@RunWith(Arquillian.class)
@Named
@RequestScoped
public class SurveyDAOImplTest {
    
    private static final Logger log = Logger.getLogger(ReviewDAOImplTest.class.getName());
    
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
                .addPackage(SurveyDAOImpl.class.getPackage())
                .addPackage(Survey.class.getPackage())
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
    private SurveyDAOImpl surveyDAO;

    
    @Before
    public void seedDatabase() {
	final String seedDataScript = loadAsString("script.sql");
            try (Connection connection = ds.getConnection();) {
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
		return line.startsWith("--") || line.startsWith("//") || line.startsWith("/*");
	}        
        
        /**
         * This test the create method, which creates a row on survey table.
         * @throws SQLException 
         */
	@Test
	public void testCreate() throws SQLException {
            log.info("Testing create() -- Test 1");
            Survey survey = new Survey();
            
            survey.setSurveyId(5);
            survey.setQuestion("What is your favourite genre?");
            survey.setChoice1("Horror");
            survey.setChoice2("Comedy");
            survey.setChoice3("Cooking");
            survey.setChoice4("Fantasy");
            survey.setChoice5("Sci-Fi");
            survey.setVotes1(30);
            survey.setVotes2(40);
            survey.setVotes3(50);
            survey.setVotes4(20);
            survey.setVotes5(10);
         
            int result = surveyDAO.create(survey);
            assertEquals("created 1 record: ", 5, result);
	}   
        
        private void createMockData() throws SQLException {
            Survey survey = new Survey();
            
            survey.setSurveyId(5);
            survey.setQuestion("What is your favourite genre?");
            survey.setChoice1("Horror");
            survey.setChoice2("Comedy");
            survey.setChoice3("Cooking");
            survey.setChoice4("Fantasy");
            survey.setChoice5("Sci-Fi");
            survey.setVotes1(30);
            survey.setVotes2(40);
            survey.setVotes3(50);
            survey.setVotes4(20);
            survey.setVotes5(10);
         
            surveyDAO.create(survey);            
        }
            
        /**
         * This  test findById method, which looks for a specific survey. 
         * @throws SQLException 
         */
        @Test
	public void testFindSurveyById() throws SQLException {
        log.info("Testing findSurveyById() -- Test 1:");
            createMockData();
        
            Survey survey = surveyDAO.findSurveyById(5);
            assertTrue(!survey.getQuestion().equals(""));
	}
        /**
         * This test findSurveyById2 method, which looks for a survey 
         * that does not exist on the database.  
         * @throws SQLException 
         */
        @Test
	public void testFindSurveyById2() throws SQLException {
        log.info("Testing findById() -- Test 2:");
            createMockData();
            
            Survey survey = surveyDAO.findSurveyById(500);
            assertTrue(survey.getSurveyId() == 0);
	}          
        
            
        
        /**
         * This test the updateComment method, which updates a survey's comment. 
         * @throws SQLException 
         */
        @Test
	public void testUpdate() throws SQLException {
            log.info("Testing  updateComment() -- Test 1");
            
            createMockData();
            
            Survey survey = new Survey();
            
            survey.setSurveyId(5);
            survey.setQuestion("What is your favourite genre?");
            survey.setChoice1("Sports");
            survey.setChoice2("Comedy");
            survey.setChoice3("Cooking");
            survey.setChoice4("Fantasy");
            survey.setChoice5("Sci-Fi");
            survey.setVotes1(30);
            survey.setVotes2(40);
            survey.setVotes3(50);
            survey.setVotes4(20);
            survey.setVotes5(10);
            int result = surveyDAO.update(survey);
            assertEquals("updated 1 record: ", 1, result);
	}
        
        /**
         * This test the delete method, which deletes a survey. 
         * @throws SQLException 
         */
        @Test
	public void testDelete() throws SQLException {
            log.info("Testing  updateStatus() -- Test 1");
            
            createMockData();
            
            int result = surveyDAO.delete(5);
            assertEquals("found 1 record: ", 1, result);
	}
}
