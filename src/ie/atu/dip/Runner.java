package ie.atu.dip;

import javax.naming.LimitExceededException;

/**
 * ie.atu.dip.Runner instantiates ie.atu.dip.InsuranceProgram
 */
public class Runner {
    public static void main(String[] args) throws LimitExceededException, IllegalArgumentException {
        /**
         * ie.atu.dip.Runner class to create new instance ofInsurance Program
         * and call the start() method
         *
         */
        InsuranceProgram ip = new InsuranceProgram();
        ip.start();
    }
}
