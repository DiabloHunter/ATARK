package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import org.apache.log4j.Logger;

public class CommandOpenAddNewUserAsAdmin implements ICommand {
    private static final Logger log = Logger.getLogger(CommandOpenAddNewUserAsAdmin.class);
    @Override
    public ExecutionResult execute(SessionRequestContent request) {
        ExecutionResult result = new ExecutionResult();
        result.setPage(Configuration.getInstance().getPage("openAddNewUserAsAdminPage"));
        result.setDirection(Direction.FORWARD);
        return result;
    }
}
