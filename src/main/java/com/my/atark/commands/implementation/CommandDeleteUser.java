package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.commands.Security;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.UserRole;
import com.my.atark.service.IUserServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandDeleteUser implements ICommand {
    private static final Logger log = Logger.getLogger(CommandDeleteProduct.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        IUserServ serv = ServiceFactory.getUserService();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            String userId = content.getRequestParameter("userId")[0];
            if (serv.deleteUserById(Integer.parseInt(userId))) {
                result.setPage(conf.getPage("redirect_manageUsers"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("deleteProductErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        }
        catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("deleteProductErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
