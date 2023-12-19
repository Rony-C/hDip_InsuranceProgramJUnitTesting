package ie.atu.dip;

import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit;

import javax.naming.LimitExceededException;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the InsuranceProject class
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({InsuranceProgram.class})
public class InsuranceProgramTest {

    /**
     * Define streams to read and process the testing input and output
     *
     */
    private static final InputStream SYSTEM_IN = System.in;
    private static final PrintStream SYSTEM_OUT = System.out;
    private final ByteArrayOutputStream outputByteStream = new ByteArrayOutputStream();

    /**
     * Before all tests, create a byte stream for output This is used to stream the
     * byte output from the console and is converted to String in assert statements
     */
    @BeforeAll
    public static void createStream() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    /**
     * Before each test, set up the input and output byte streams This is to ensure
     * a fresh connection for i/o is established for each test Makes sure the output
     * stream is direct correctly for testing
     */
    @BeforeEach
    public void setUp() {
        System.setIn(new ByteArrayInputStream("".getBytes()));
        System.setOut(new PrintStream(outputByteStream));
    }

    /**
     * After all test are complete the streams are reset
     */
    @AfterAll
    public static void restoreStreams() {
        System.setIn(SYSTEM_IN);
        System.setOut(SYSTEM_OUT);
    }

    /**
     * After each test, reset the i/o streams Allows them to be started fresh to get
     * new input and output
     */
    @AfterEach
    public void tearDown() {
        System.setIn(SYSTEM_IN);
        System.setOut(SYSTEM_OUT);
    }

    /*
      A list of manual tests to check that age and accident combinations return the
      expected results
     */

    /**
     * Test age 58 with 0 accidents
     */
    @Test
    public void testAge58accidents0() throws LimitExceededException {
        String input = "58\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram insuranceProgram = new InsuranceProgram();
        insuranceProgram.start();

        assertEquals(
                "Enter your age: No additional surcharge\n"
                        + "\nHow many accidents did you have? No surcharge\nTotal amount to pay: 500\n",
                outputByteStream.toString());
    }

    /**
     * Test age 72 with 1 accident
     */
    @Test
    public void testAge72accidents1() throws LimitExceededException {
        String input = "72\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram insuranceProgram = new InsuranceProgram();
        insuranceProgram.start();

        assertEquals("Enter your age: No additional surcharge\n"
                        + "\nHow many accidents did you have? Additional surcharge for accident: 50 \nTotal amount to pay: 550\n",
                outputByteStream.toString());
    }

    /**
     * Test for a person under 25 with 2 accidents The full output string is used as
     * that is compared against the output stream converted to a string Input is a
     * string with the age and number of accidents \n is for Enter key presses to
     * simulate user input to move to next input sect
     */
    @Test
    public void testAge22accidents2() throws LimitExceededException {
        String input = "22\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram insuranceProgram = new InsuranceProgram();
        insuranceProgram.start();

        assertEquals("Enter your age: Additional surcharge 100\n"
                        + "\nHow many accidents did you have? Additional surcharge for accident: 125 \nTotal amount to pay: 725\n",
                outputByteStream.toString());
    }

    /**
     * Test age 37 with 3 accidents
     */
    @Test
    public void testAge37accidents3() throws LimitExceededException {
        String input = "37\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram insuranceProgram = new InsuranceProgram();
        insuranceProgram.start();

        assertEquals("Enter your age: No additional surcharge\n"
                        + "\nHow many accidents did you have? Additional surcharge for accident: 225 \nTotal amount to pay: 725\n",
                outputByteStream.toString());
    }

    /**
     * Testing that under 25 age does not return the same value as someone over 25
     */
    @Test
    public void testAge19accidents4() throws LimitExceededException {
        String input = "19\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram ip = new InsuranceProgram();
        ip.start();

        assertNotEquals("Enter your age: No additional surcharge\n"
                        + "\nHow many accidents did you have? Additional surcharge for accident: 375 \nTotal amount to pay: 975\n",
                outputByteStream.toString());
    }

    /**
     * Test age 99 with 5 accidents
     */
    @Test
    public void testAge99accidents5() throws LimitExceededException {
        String input = "99\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram ip = new InsuranceProgram();
        ip.start();

        assertEquals("Enter your age: No additional surcharge\n"
                        + "\nHow many accidents did you have? Additional surcharge for accident: 575 \nTotal amount to pay: 1075\n",
                outputByteStream.toString());
    }


    /**
     * Test if exception is thrown if number under 16 is entered
     * This test will fail if it does not complete within 15 milliseconds
     */

    @Test
    @Timeout(value = 15, unit = TimeUnit.MILLISECONDS)
    public void testIllegalArgumentUnder16WithTimeout() {
        String input = "12\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram ip = new InsuranceProgram();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ip::start);
        assertEquals("Not eligible for insurance", exception.getMessage());
    }

    /**
     * Test if exception is thrown if number over 99 entered
     */
    @Test
    public void testIllegalArgumentOver99() {
        String input = "102\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram ip = new InsuranceProgram();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ip::start);
        assertEquals("Not eligible for insurance", exception.getMessage());
    }

    /**
     * Test exception is thrown if max accidents is entered
     */
    @Test
    public void testMaxAccidents7Accidents() {
        String input = "27\n7";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram ip = new InsuranceProgram();

        LimitExceededException limitException = assertThrows(LimitExceededException.class, ip::start);
        assertEquals("You exceed the maximum number of accidents", limitException.getMessage());
    }

    /**
     * Test exception does not throw false positives
     */
    @Test
    public void testMaxAccidents3Accidents() {
        String input = "67\n7";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram ip = new InsuranceProgram();

        assertThrows(LimitExceededException.class, ip::start);
    }

    /**
     * Test exception when negative number of accidents is entered
     */
    @Test
    public void testNegativeAccidentsEntered() {
        String input = "30\n-1";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram ip = new InsuranceProgram();

        InputMismatchException ime = assertThrows(InputMismatchException.class, ip::start);
        assertEquals("You have entered a negative number", ime.getMessage());
    }

    /**
     * Tests cases submitted through a CSV file
     * @param age Age of user
     * @param accidents Number of car accidents
     * @param expectedOutput Expected string result
     * @throws LimitExceededException Exception thrown dependent on user input
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/insuranceTestCases.csv", numLinesToSkip = 1)
    public void paramaterizedTestCsvFile(int age, int accidents, String expectedOutput) throws LimitExceededException {
        String input = age + "\n" + accidents + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InsuranceProgram ip = new InsuranceProgram();

        ByteArrayOutputStream outputByteStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteStream));

        ip.start();

        assertEquals(expectedOutput, outputByteStream.toString());
    }

}
