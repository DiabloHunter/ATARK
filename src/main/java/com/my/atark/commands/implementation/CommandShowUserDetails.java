package com.my.atark.commands.implementation;


import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.User;
import com.my.atark.domain.UserRole;
import org.apache.log4j.Logger;

public class CommandShowUserDetails implements ICommand {

    private static final Logger log = Logger.getLogger(CommandShowUserDetails.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            User user = new User();
            user.setId(Integer.parseInt(content.getRequestParameter("id")[0]));
            user.setUserRole(UserRole.valueOf(content.getRequestParameter("userRole")[0]));
            user.setName(content.getRequestParameter("name")[0]);
            user.setEmail(content.getRequestParameter("phoneNumber")[0]);
            user.setPhoneNumber(content.getRequestParameter("address")[0]);
            user.setAddress(content.getRequestParameter("notes")[0]);

        } catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showProductDetails"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}