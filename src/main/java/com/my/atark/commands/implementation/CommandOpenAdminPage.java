package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.commands.Security;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.UserRole;
import org.apache.log4j.Logger;

public class CommandOpenAdminPage implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenAdminPage.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.CASHIER, UserRole.SENIOR_CASHIER, UserRole.MERCHANT, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            result.setPage(conf.getPage("administration"));
        }
        catch (Exception e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("administrationErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
