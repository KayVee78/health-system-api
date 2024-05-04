/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import static com.example.dao.BillingDAO.bills;
import com.example.model.Billing;
import com.example.model.Invoice;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class BillingDAO {

    public static List<Billing> bills = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingDAO.class);
    private BillingDAO billDAO = new BillingDAO();
    final double HOSPITALCHARGES = 2500;

    static {
        bills.add(new Billing(1, 1, new Invoice("Ashan Dias (0772572800) - Channeled Doctor - Dr. Neranjan Perera (Diabetologist)", 5500, "2024-05-03"), 5500, 0));
        bills.add(new Billing(2, 2, new Invoice("Janaka Fernando (0712359807)- Channeled Doctor - Dr. Tiran Gamlath (Cardiologist)", 6000, "2024-05-02"), 6000, 0));

    }

    public List<Billing> getAllBills() {
        return bills;
    }
    
    //HELPER METHODS
    public Billing findBillingById(int id) {
        Billing billFound = null;
        for (Billing bill : bills) {
            if (bill.getBillingId()== id) {
                billFound = bill;
                break;
            }
        }
        return billFound;
    }

    public int getNextBillId() {
        //Initialize the maxUserId with a value lower than any possible userId
        int maxUserId = Integer.MIN_VALUE;

        //Iterage through the list and finding the maximum userId
        for (Billing bill : bills) {
            int userId = bill.getBillingId();
            if (userId > maxUserId) {
                maxUserId = userId;
            }
        }

        //Increment the mxUserId to get the next available userId
        return maxUserId + 1;
    }
}
