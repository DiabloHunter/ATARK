package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.UserCart;
import com.my.atark.domain.UserCartView;
import com.my.atark.exceptions.InvoiceServiceException;
import com.my.atark.service.IInvoiceServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandOpenUsersCart implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenUsersCart.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IInvoiceServ serv = ServiceFactory.getInvoiceService();
            UserCartView view = serv.createUsersCartView((UserCart) content.getSessionAttribute("cart"));
            result.addRequestAttribute("cartView", view);
            result.setPage(conf.getPage("usersCart"));
        }
        catch (NullPointerException | InvoiceServiceException uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showUserCartErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }

}
