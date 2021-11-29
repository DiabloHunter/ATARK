package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.commands.Security;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.User;
import com.my.atark.domain.UserRole;
import com.my.atark.service.IUserServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class CommandOpenUserMngPage implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenUserMngPage.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            //
            String type =  content.getRequestParameter("type")[0];
            IUserServ serv = ServiceFactory.getUserService();
            List<User> users = new LinkedList<>();
            if (type.equals("all"))
                users = serv.findAllUsers();
            if (type.equals("user"))
                users = serv.findUsersByRole(UserRole.USER);
            if (type.equals("cashier"))
                users = serv.findUsersByRole(UserRole.CASHIER);
            if (type.equals("seniorCashier"))
                users = serv.findUsersByRole(UserRole.SENIOR_CASHIER);
            if (type.equals("merchant"))
                users = serv.findUsersByRole(UserRole.MERCHANT);
            if (type.equals("admin"))
                users = serv.findUsersByRole(UserRole.ADMIN);
            result.addRequestAttribute("users", users);
            result.setPage(conf.getPage("manageUsers"));
        }
        catch (Exception e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("manageUsersErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}