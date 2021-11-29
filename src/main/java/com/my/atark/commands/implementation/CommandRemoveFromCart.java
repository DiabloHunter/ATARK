package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.UserCart;
import org.apache.log4j.Logger;

public class CommandRemoveFromCart implements ICommand {

    private static final Logger log = Logger.getLogger(CommandRemoveFromCart.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            UserCart cart = (UserCart) content.getSessionAttribute("cart");
            String productCode = content.getRequestParameter("productCode")[0];
            cart.removeProduct(productCode);
            result.addSessionAttribute("cart", cart);
            result.setPage(conf.getPage("redirect_usersCart"));
        }
        catch (NullPointerException npe) {
            log.error(npe);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("removefromCartErr"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
