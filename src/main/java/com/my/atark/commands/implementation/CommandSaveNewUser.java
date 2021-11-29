package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.User;
import com.my.atark.domain.UserCart;
import com.my.atark.domain.UserRole;
import com.my.atark.service.IUserServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandSaveNewUser implements ICommand {

    private static final Logger log = Logger.getLogger(CommandSaveNewUser.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            String login = content.getRequestParameter("name")[0];
            String password = content.getRequestParameter("password")[0];
            String phone = content.getRequestParameter("phone")[0];
            String email = content.getRequestParameter("email")[0];
            String address = content.getRequestParameter("address")[0];
            String notes = content.getRequestParameter("notes")[0];
            User user = new User(login, password);
            user.setPhoneNumber(phone);
            user.setEmail(email);
            user.setAddress(address);
            user.setNotes(notes);
            user.setUserRole(UserRole.USER);
            IUserServ userServ = ServiceFactory.getUserService();
            if (userServ.addUser(user)) {
                result.addSessionAttribute("cart", new UserCart(user.getName()));
                result.addSessionAttribute("user", userServ.findUser(login, password));
                result.setPage(conf.getPage("redirect_home"));
            }
            else {
                result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveNewUserErr"));
                result.setPage(Configuration.getInstance().getPage("error"));
            }
        }
        catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("saveNewUserErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
