package com.my.atark.service;

import com.my.atark.domain.Payment;

public interface IPaymentServ {

    /**
     * Adds a new payment to DB
     * @param payment Payment to add
     * @return true if operation success and false if fails
     */
    boolean addPayment(Payment payment);

    /**
     * Updates existent payment in DB
     * @param payment Payment to update
     * @return true if operation success and false if fails
     */
    boolean updatePayment(Payment payment);

}
