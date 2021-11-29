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
import com.my.atark.service.IProductServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Set;

public class CommandOpenInvDetailsPage implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenInvDetailsPage.class);

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
            IProductServ prodServ = ServiceFactory.getProductService();
            Long code = Long.parseLong(content.getRequestParameter("code")[0]);
            Invoice invoice = serv.findInvoiceByOrderNumber(code);
            Set<String> products = prodServ.createProductSet();
            result.addRequestAttribute("invoice", invoice);
            result.addRequestAttribute("products", products);
            result.setPage(conf.getPage("invoiceDetails"));
        }
        catch (Exception e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showInvoiceDetailsErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
