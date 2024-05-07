/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import static com.example.dao.BillingDAO.bills;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Appointment;
import com.example.model.Billing;
import com.example.model.Doctor;
import com.example.model.Invoice;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class BillingDAO {

    public static List<Billing> bills = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingDAO.class);
    final double HOSPITALCHARGES = 2500;

    AppointmentDAO appointmentDAO = new AppointmentDAO();
    PatientDAO patietDAO = new PatientDAO();
    DoctorDAO doctorDAO = new DoctorDAO();

//    static {
//        bills.add(new Billing(1, 2, new Invoice("Ashan Dias (0772572800) - Channeled Doctor - Dr. Neranjan Perera (Diabetologist)", 5500, "2024-05-03"), 5500, 0));
//        bills.add(new Billing(2, 1, new Invoice("Janaka Fernando (0712359807)- Channeled Doctor - Dr. Tiran Gamlath (Cardiologist)", 6000, "2024-05-02"), 6000, 0));
//
//    }

    public List<Billing> getAllBillingData() {
        return bills;
    }

    public List<Billing> getNonModifiedBillingList() {
        return bills;
    }

    public void addBillingData(Billing bill) {
        if (bill.getAppointmentId() > 0 && (bill.getInvoiceInfo() instanceof Invoice && bill.getInvoiceInfo() != null) && bill.getAmountPaid() > 0 && (bill.getInvoiceInfo().getDate() instanceof String && !bill.getInvoiceInfo().getDate().isEmpty() && bill.getInvoiceInfo().getDate() != null)) {
            boolean isValidAppointmentId = false;
            for (Appointment appointment : AppointmentDAO.appointments) {
                if (appointment.getAppointmentId() == bill.getAppointmentId()) {
                    isValidAppointmentId = true;
                    break;
                }
            }

            if (isValidAppointmentId) {
                int newBillingId = getNextBillingId();
                Appointment appointment = appointmentDAO.findAppointmentById(bill.getAppointmentId());

                Patient patient = patietDAO.findPatientById(appointment.getPatient().getPatientId());
                Doctor doctor = doctorDAO.findDoctorById(appointment.getDoctor().getDoctorId());
                if ((patient != null) && (patient instanceof Patient) && (doctor != null) && (doctor instanceof Doctor)) {
                    String patientDetails = String.format("%s (%s) - Channeled Doctor - Dr. %s (%s)", patient.getName(), patient.getContactInfo(), doctor.getName(), doctor.getSpecialization());
                    double amountPayable = doctor.getDoctorFee() + HOSPITALCHARGES;
                    double balance = bill.getAmountPaid() - amountPayable;

                    if (balance >= 0) {
                        bills.add(new Billing(newBillingId, bill.getAppointmentId(), new Invoice(patientDetails, amountPayable, bill.getInvoiceInfo().getDate()), bill.getAmountPaid(), balance));
                    }

                } else {
                    throw new ResourceNotFoundException("Error occurred while finding the doctor or patient of the respectiive appointment ID", Response.Status.NOT_FOUND);

                }
            } else {
                throw new ResourceNotFoundException("No appointment found with ID: " + bill.getAppointmentId(), Response.Status.NOT_FOUND);
            }
        } else {
            LOGGER.error("Missing mandatory field(s) in billing data. Failed to add a new Bill!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in billing data. Failed to add a new Bill!", Response.Status.BAD_REQUEST);
        }

    }

    public void updateBillingData(Billing updateBill) {
        if (updateBill.getAppointmentId() > 0 && (updateBill.getInvoiceInfo() instanceof Invoice && updateBill.getInvoiceInfo() != null) && updateBill.getAmountPaid() > 0 && (updateBill.getInvoiceInfo().getDate() instanceof String && !updateBill.getInvoiceInfo().getDate().isEmpty() && updateBill.getInvoiceInfo().getDate() != null)) {
            boolean isValidAppointmentId = false;
            Billing formattedUpdateBill = null;
            for (Appointment appointment : AppointmentDAO.appointments) {
                if (appointment.getAppointmentId() == updateBill.getAppointmentId()) {
                    isValidAppointmentId = true;
                    break;
                }
            }

            if (isValidAppointmentId) {
                Appointment appointment = appointmentDAO.findAppointmentById(updateBill.getAppointmentId());
                Patient patient = patietDAO.findPatientById(appointment.getPatient().getPatientId());
                Doctor doctor = doctorDAO.findDoctorById(appointment.getDoctor().getDoctorId());
                if ((patient != null) || (patient instanceof Patient) || (doctor != null) && (doctor instanceof Doctor)) {
                    String patientDetails = String.format("%s (%s) - Channeled Doctor - Dr. %s (%s)", patient.getName(), patient.getContactInfo(), doctor.getName(), doctor.getSpecialization());
                    double amountPayable = doctor.getDoctorFee() + HOSPITALCHARGES;
                    if (updateBill.getAmountPaid() > amountPayable) {
                        double balance = updateBill.getAmountPaid() - amountPayable;
                        if (balance >= 0) {
                            formattedUpdateBill = new Billing(updateBill.getBillingId(), updateBill.getAppointmentId(), new Invoice(patientDetails, amountPayable, updateBill.getInvoiceInfo().getDate()), updateBill.getAmountPaid(), balance);

                            for (int i = 0; i < bills.size(); i++) {
                                Billing billing = bills.get(i);
                                if (billing.getBillingId() == updateBill.getBillingId()) {
                                    bills.set(i, formattedUpdateBill);
                                    return;
                                }
                            }
                        }
                    } else {
                        throw new ResourceNotFoundException("Amount paid is not sufficient to the complete the bill payment process", Response.Status.BAD_REQUEST);
                    }
                } else {
                    throw new ResourceNotFoundException("Error occurred while finding the doctor or patient of the respectiive appointment ID", Response.Status.NOT_FOUND);
                }
            } else {
                throw new ResourceNotFoundException("No appointment found with ID: " + updateBill.getAppointmentId(), Response.Status.NOT_FOUND);
            }
        } else {
            LOGGER.error("Missing mandatory field(s) in billing data. Failed to update Bill!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in billing data. Failed to update Bill!", Response.Status.BAD_REQUEST);
        }
    }

    public void deleteBillingData(int id) {
        bills.removeIf(bill -> bill.getBillingId() == id);
    }

    public Billing findBillingDataByAppointmentId(int id) {
        Billing billFound = null;
        for (Billing bill : bills) {
            if (bill.getAppointmentId() == id) {
                billFound = bill;
                break;
            }
        }
        return billFound;
    }

    //HELPER METHODS
    public Billing findBillingDataById(int id) {
        Billing billFound = null;
        for (Billing bill : bills) {
            if (bill.getBillingId() == id) {
                billFound = bill;
                break;
            }
        }
        return billFound;
    }

    public int getNextBillingId() {
        int maxBillId = 0;
        if (bills.size() > 0) {
            maxBillId = Integer.MIN_VALUE;

            for (Billing bill : bills) {
                int billId = bill.getBillingId();
                if (billId > maxBillId) {
                    maxBillId = billId;
                }
            }
            return maxBillId + 1;
        } else {
            maxBillId = 1;
        }
        return maxBillId;
    }
}
