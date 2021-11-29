package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.Product;
import com.my.atark.service.IProductServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

public class CommandOpenEditProductPage implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenEditProductPage.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration config = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            IProductServ serv = ServiceFactory.getProductService();
            String code = content.getRequestParameter("productCode")[0];
            Product product = serv.findProductByCode(code);
            result.addRequestAttribute("product", product);
            result.setPage(config.getPage("editProduct"));
        } catch (Exception e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", config.getErrorMessage("editProductPageErr"));
            result.setPage(config.getPage("error"));
        }
        return result;
    }
}
