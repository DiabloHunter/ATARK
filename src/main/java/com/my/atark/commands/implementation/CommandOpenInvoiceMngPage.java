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

import java.util.LinkedList;
import java.util.List;

public class CommandOpenInvoiceMngPage implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenInvoiceMngPage.class);

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
            String type = (String) content.getRequestParameter("type")[0];
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            List<Invoice> invoices = new LinkedList<>();
            if (type.equals("all"))
                invoices = serv.findAllInvoices();
            if (type.equals("new"))
                invoices = serv.findNewInvoices();
            if (type.equals("cancelled"))
                invoices = serv.findCancelledInvoices();
            if (type.equals("closed"))
                invoices = serv.findFinishedInvoices();
            result.addRequestAttribute("invoices", invoices);
            result.setPage(conf.getPage("manageInvoices"));
        }
        catch (Exception e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("manageInvoicesErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}