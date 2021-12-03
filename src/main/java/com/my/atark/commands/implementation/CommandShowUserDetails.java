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

  /*  @Override
   public ExecutionResult execute(SessionRequestContent content) {
        Configuration config = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IUserServ serv = ServiceFactory.getUserService();
            String id = content.getRequestParameter("userId")[0];
            User user = serv.findUserById(Integer.parseInt(id));
            result.addRequestAttribute("user", user);
            result.setPage(config.getPage("viewUserProfile"));
        } catch (Exception e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", config.getErrorMessage("editProductPageErr"));
            result.setPage(config.getPage("error"));
        }
        return result;
    }
*/
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
           /* user.setAvailable(content.checkRequestParameter("isAvailable"));
            user.setQuantity(DataValidator.filterDouble(content.getRequestParameter("quantity")[0]));
            user.setReservedQuantity(DataValidator.filterDouble(content.getRequestParameter("reserved")[0]));
            user.setUomRu(content.getRequestParameter("uomRu")[0]);
            user.setUomEn(content.getRequestParameter("uomEn")[0]);
            user.setNotesRu(content.getRequestParameter("notesRu")[0]);
            user.setNotesEn(content.getRequestParameter("notesEn")[0]);*/
        } catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showProductDetails"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}