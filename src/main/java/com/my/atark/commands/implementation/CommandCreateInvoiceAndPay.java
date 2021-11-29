package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.Invoice;
import com.my.atark.domain.UserCart;
import com.my.atark.exceptions.InvoiceServiceException;
import com.my.atark.service.IInvoiceServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandCreateInvoiceAndPay implements ICommand {

    private static final Logger log = Logger.getLogger(CommandCreateInvoiceAndPay.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            Long invoiceCode = System.currentTimeMillis();
            UserCart cart = (UserCart) content.getSessionAttribute("cart");
            IInvoiceServ invoiceServ = ServiceFactory.getInvoiceService();
            Invoice invoice = invoiceServ.createInvoiceFromUserCart(cart, invoiceCode);
            if (invoiceServ.addInvoice(invoice) && invoiceServ.payByInvoice(invoiceCode)) {
                cart.removeAll();
                result.setPage(conf.getPage("redirect_home"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("invoiceCreationErr"));
                result.setPage(conf.getPage("error"));
            }
        }
        catch (NullPointerException | InvoiceServiceException npe) {
            log.error(npe);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("invoiceCreationErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}