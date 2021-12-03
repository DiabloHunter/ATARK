package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.commands.Security;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.Invoice;
import com.my.atark.domain.UserRole;
import com.my.atark.exceptions.ProductServiceException;
import com.my.atark.service.IInvoiceServ;
import com.my.atark.service.IProductServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Set;

public class CommandRemoveFromInvoice implements ICommand {

    private static final Logger log = Logger.getLogger(CommandRemoveFromInvoice.class);

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
        
            Long invCode = Long.parseLong( content.getRequestParameter("invCode")[0]);
            String productCode =  content.getRequestParameter("productCode")[0];
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            if (serv.removeProductFromInvoice(invCode, productCode)) {
                Invoice inv = serv.findInvoiceByOrderNumber(invCode);
                IProductServ prodServ = ServiceFactory.getProductService();
                Set<String> products = prodServ.createProductSet();
                result.addRequestAttribute("invoice", inv);
                result.addRequestAttribute("products", products);
                result.addRequestAttribute("command", "showInvoiceDetail");
                result.addRequestAttribute("code", invCode);
                result.setPage(conf.getPage("invoiceDetails"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("removeProductFromInvoiceErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        } catch (ProductServiceException | NullPointerException npe) {
            log.error(npe);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("removeProductFromInvoiceErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
