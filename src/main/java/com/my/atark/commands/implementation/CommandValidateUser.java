package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.User;
import com.my.atark.domain.UserCart;
import com.my.atark.exceptions.UnknownUserException;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandValidateUser implements ICommand {

    private static final Logger log = Logger.getLogger(CommandValidateUser.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        String login = content.getRequestParameter("name")[0];
        String password = content.getRequestParameter("password")[0];
        try {
            User user = ServiceFactory.getUserService().findUser(login, password);
            UserCart cart = new UserCart(user.getName());
            result.addSessionAttribute("user", user);
            result.addSessionAttribute("cart", cart);
            result.setPage(conf.getPage("redirect_home"));
        }
        catch (UnknownUserException uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("validateUserErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
