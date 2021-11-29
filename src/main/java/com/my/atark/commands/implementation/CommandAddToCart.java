package com.my.atark.commands.implementation;

import com.my.atark.commands.DataValidator;
import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.UserCart;
import com.my.atark.exceptions.InvalidValueException;
import org.apache.log4j.Logger;

public class CommandAddToCart implements ICommand {

    private static final Logger log = Logger.getLogger(CommandAddToCart.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        ExecutionResult result = new ExecutionResult();
        Configuration config = Configuration.getInstance();
        result.setDirection(Direction.FORWARD);
        try {
            UserCart cart = (UserCart) content.getSessionAttribute("cart");
            String productCode = content.getRequestParameter("productCode")[0];
            String productQuantity = content.getRequestParameter("productQuantity")[0];
            Double quantity = DataValidator.filterDouble(productQuantity);
            cart.addProduct(productCode, quantity);
            result.addSessionAttribute("cart", cart);
            result.setPage(config.getPage("redirect_home"));
        } catch (NullPointerException | NumberFormatException e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", config.getErrorMessage("addToCartErr"));
            result.setPage(config.getPage("error"));
        } catch (InvalidValueException ive) {
            log.error(ive);
            result.addRequestAttribute("errorMessage", config.getErrorMessage("dataValidationError"));
            result.setPage(config.getPage("error"));
        }
        return result;
    }
}
