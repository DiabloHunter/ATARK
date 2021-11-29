package com.my.atark.commands.implementation;

import com.my.atark.commands.DataValidator;
import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.Invoice;
import com.my.atark.domain.InvoiceStatus;
import com.my.atark.domain.Payment;
import com.my.atark.exceptions.InvalidValueException;
import com.my.atark.service.IInvoiceServ;
import com.my.atark.service.IPaymentServ;
import com.my.atark.service.IProductServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Set;

public class CommandAddNewPayment implements ICommand {

    private static final Logger log = Logger.getLogger(CommandAddNewPayment.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        IInvoiceServ invserv = ServiceFactory.getInvoiceService();
        IProductServ prodServ = ServiceFactory.getProductService();
        result.setDirection(Direction.FORWARD);
        try {
            IPaymentServ serv = ServiceFactory.getPaymentService();
            Payment payment = new Payment();
            payment.setProductCode(content.getRequestParameter("productCode")[0]);
            payment.setQuantity(DataValidator.filterDouble(content.getRequestParameter("quantity")[0]));
            payment.setStatusId(InvoiceStatus.CREATED);
            payment.setOrderCode(Long.parseLong(content.getRequestParameter("orderCode")[0]));
            payment.setPaymentNotes(content.getRequestParameter("paymentNotes")[0]);
            if (serv.addPayment(payment)) {
                Invoice invoice = invserv.findInvoiceByOrderNumber(payment.getOrderCode());
                Set<String> products = prodServ.createProductSet();
                result.addRequestAttribute("invoice", invoice);
                result.addRequestAttribute("products", products);
                result.setPage(conf.getPage("invoiceDetails"));
            }
            else {
                result.setDirection(Direction.FORWARD);
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("addNewPaymentErr"));
                result.setPage(conf.getPage("error"));
            }
        } catch (InvalidValueException ive) {
            log.error(ive);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("dataValidationError"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }  catch (Exception uue) {
            log.error(uue);
            result.setDirection(Direction.FORWARD);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("addNewPaymentErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
