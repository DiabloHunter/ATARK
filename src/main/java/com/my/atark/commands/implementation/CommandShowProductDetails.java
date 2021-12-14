package com.my.atark.commands.implementation;

import com.my.atark.commands.DataValidator;
import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.Product;
import org.apache.log4j.Logger;

public class CommandShowProductDetails implements ICommand {

    private static final Logger log = Logger.getLogger(CommandUpdateProduct.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            Product product = new Product();
            product.setId(Integer.parseInt(content.getRequestParameter("id")[0]));
            product.setCode(content.getRequestParameter("code")[0]);
            product.setNameRu(content.getRequestParameter("nameRu")[0]);
            product.setNameEn(content.getRequestParameter("nameEn")[0]);
            product.setDescriptionRu(content.getRequestParameter("descriptionRu")[0]);
            product.setDescriptionEn(content.getRequestParameter("descriptionEn")[0]);
            product.setCost(DataValidator.filterDouble(content.getRequestParameter("cost")[0]));
            product.setAvailable(content.checkRequestParameter("isAvailable"));
            product.setQuantity(DataValidator.filterDouble(content.getRequestParameter("quantity")[0]));
            product.setReservedQuantity(DataValidator.filterDouble(content.getRequestParameter("reserved")[0]));
            product.setUomRu(content.getRequestParameter("uomRu")[0]);
            product.setUomEn(content.getRequestParameter("uomEn")[0]);
            product.setNotesRu(content.getRequestParameter("notesRu")[0]);
            product.setNotesEn(content.getRequestParameter("notesEn")[0]);
        } catch (Exception uue) {
            log.error(uue);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("showProductDetails"));
            result.setPage(Configuration.getInstance().getPage("error"));
        }
        return result;
    }
}
