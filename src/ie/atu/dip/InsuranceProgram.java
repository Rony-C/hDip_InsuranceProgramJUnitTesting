package ie.atu.dip;

import java.nio.channels.IllegalChannelGroupException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.naming.LimitExceededException;

/**
 * Logic for the ie.atu.dip.InsuranceProgram to run
 */
public class InsuranceProgram {
    Scanner input = new Scanner(System.in);

    /**
     * Defining variables to be used in the program
     */
    static int minimumAge = 16;
    static int maxAge = 100;
    static int surchargeStopAge = 25;
    static int maxAccidents = 5;

    static int basicInsurance = 500;
    static int surchargeU25 = 100;

    static int oneAcc = 50;
    static int twoAcc = 125;
    static int threeAcc = 225;
    static int fourAcc = 375;
    static int fiveAcc = 575;

    /**
     * Exception is thrown if user is under 16 or 100 and older
     *
     * Start method starts the applications logic
     * It has initial values, creates the scanner and holds the logic
     * for determining what methods are called to ensure
     * the correct calculation is made
     *
     */
    public void start() throws IllegalArgumentException, LimitExceededException {

        System.out.print("Enter your age: ");
        int age = input.nextInt();

        if (age < minimumAge || age >= maxAge) {
            throw new IllegalArgumentException("Not eligible for insurance");
        } else if (age < surchargeStopAge) {
            calculateUnder25(input, basicInsurance, surchargeU25);
        } else {
            calculateOver25(input, basicInsurance);
        }
    }

    /**
     * Calculates Insurance for those under 25. Takes parameters to determine amount
     *
     * @param input - Taken from the user. Needs to be passed in to get the number of accidents
     * the user has had
     * @param basicInsurance - Minimum price set as variable
     * @param surcharge - Price set as a variable for being under 25
     */
    private static void calculateUnder25(Scanner input, int basicInsurance, int surcharge) throws LimitExceededException {
        //Surcharge added to basic insurance so getInsurance price can be used in Under and Over 25 methods
        basicInsurance += surcharge;
        System.out.println("Additional surcharge " + surcharge);

        System.out.print("\nHow many accidents did you have? ");
        int accidents = input.nextInt();

            getInsurancePrice(basicInsurance, accidents);
    }

    /**
     * Calculates insurance for those over 25. Takes parameters to determine amount
     *
     * @param input - Taken from the user. Needs to be passed in to get the number of accidents
     * the user has had
     * @param basicInsurance - Minimum price set as variable
     */
    private static void calculateOver25(Scanner input, int basicInsurance) throws LimitExceededException {
        System.out.println("No additional surcharge");

        System.out.print("\nHow many accidents did you have? ");
        int accidents = input.nextInt();

            getInsurancePrice(basicInsurance, accidents);
    }

    /**
     * Called in each calculate method. Used to calculate and return the final price based on the
     * users age and accidents.
     * A switch statement is used to call getFinalPrice and print it out
     *
     * @param basicInsurance - Based on minimum price in set variable and if surcharge is added or not
     * @param accidents - Entered by the user from Scanner input
     */

    private static void getInsurancePrice(int basicInsurance, int accidents) throws LimitExceededException {

        if (accidents < 0) {
            throw new InputMismatchException("You have entered a negative number");
        }
        if (accidents > maxAccidents) {
            throw new LimitExceededException("You exceed the maximum number of accidents");
        }

        switch (accidents) {
            case 0:
                System.out.println("No surcharge");
                System.out.println("Total amount to pay: " + basicInsurance);
                break;
            case 1:
                getFinalPrice(basicInsurance, oneAcc);
                break;
            case 2:
                getFinalPrice(basicInsurance, twoAcc);
                break;
            case 3:
                getFinalPrice(basicInsurance, threeAcc);
                break;
            case 4:
                getFinalPrice(basicInsurance, fourAcc);
                break;
            case 5:
                getFinalPrice(basicInsurance, fiveAcc);
                break;
            default:
                System.out.println("No insurance");
        }
    }

    /**
     * Prints out the price details based on the users input and age
     *
     * @param basicInsurance
     * @param surcharge
     */
    private static void getFinalPrice(int basicInsurance, int surcharge) {
        System.out.println("Additional surcharge for accident: " + surcharge
                + " \nTotal amount to pay: " + (basicInsurance + surcharge));
    }
}
