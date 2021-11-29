package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.commands.Security;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.Invoice;
import com.my.atark.domain.UserRole;
import com.my.atark.service.IInvoiceServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandConfirmPayment implements ICommand {

    private static final Logger log = Logger.getLogger(CommandConfirmPayment.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.CASHIER, UserRole.SENIOR_CASHIER, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            Long invCode = Long.parseLong(content.getRequestParameter("invCode")[0]);
            if (serv.confirmPayment(invCode)) {
                Invoice invoice = serv.findInvoiceByOrderNumber(invCode);
                result.addRequestAttribute("invoice", invoice);
                result.setPage(conf.getPage("invoiceDetails"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("confirmInvoiceErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        }
        catch (NullPointerException npe) {
            log.error(npe);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("confirmInvoiceErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
