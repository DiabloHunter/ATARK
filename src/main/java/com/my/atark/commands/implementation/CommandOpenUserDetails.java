package com.my.atark.commands.implementation;


import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.User;
import com.my.atark.service.IUserServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandOpenUserDetails implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenUserDetails.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration config = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IUserServ serv = ServiceFactory.getUserService();
            String code = content.getRequestParameter("userId")[0];
            User user = serv.findUserById(Integer.parseInt(code));
            result.addRequestAttribute("user", user);
            result.setPage(config.getPage("userDetails"));
        } catch (Exception e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", config.getErrorMessage("editUserPageErr"));
            result.setPage(config.getPage("error"));
        }
        return result;
    }
}
