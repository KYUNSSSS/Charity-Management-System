/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import boundary.*;
import utility.MessageUI;

/**
 *
 * @author Hp
 */
public class driver {

    private driverUI driverUI = new driverUI();
    private DoneeManagement donee = new DoneeManagement();
    private DonorManagement donor = new DonorManagement();
    private DonationManagement donation = new DonationManagement();
    private DonationDistribution distribution = new DonationDistribution();
    private VolunteerManagement volunteer = new VolunteerManagement();

    public void runDriver() {
        int choice = 0;
        do {
            choice = driverUI.getMenuChoice();
            switch (choice) {
                case 0:
                    driver driver = new driver();
                    driver.runDriver();
                    break;
                case 1:
                    donor.runDonorManagement();
                    break;
                case 2:
                    donee.runDoneeManagement();
                    break;
                case 3:
                    donation.runDonationManagement();
                    break;
                case 4:
                    distribution.runDonationDistribution();
                    break;
                case 5:
                    volunteer.runVolunteerManagement();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        driver driver = new driver();
        driver.runDriver();
    }
}
