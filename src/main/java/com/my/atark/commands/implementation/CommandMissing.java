package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.User;
import com.my.atark.domain.UserCart;
import com.my.atark.exceptions.UnknownUserException;
import com.my.atark.service.IUserServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Locale;

public class CommandMissing implements ICommand {

    private static final Logger log = Logger.getLogger(CommandMissing.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IUserServ userServ = ServiceFactory.getUserService();
            User guest = userServ.findUser("Guest", "");
            UserCart cart = new UserCart(guest.getName());
            if (!content.checkSessionAttribute("user"))
                result.addSessionAttribute("user", guest);
            if (!content.checkSessionAttribute("cart"))
                result.addSessionAttribute("cart", cart);
            if (!content.checkSessionAttribute("local"))
                result.addSessionAttribute("locale", new Locale("ru", "RU"));
            result.addRequestAttribute("pageNum", 1);
            result.setPage(conf.getPage("redirect_home"));
        } catch (UnknownUserException uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("generalErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
