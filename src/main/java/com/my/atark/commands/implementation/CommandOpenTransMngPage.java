package com.my.atark.commands.implementation;

import com.my.atark.commands.ICommand;
import com.my.atark.commands.Security;
import com.my.atark.config.Configuration;
import com.my.atark.controller.Direction;
import com.my.atark.controller.ExecutionResult;
import com.my.atark.controller.SessionRequestContent;
import com.my.atark.domain.Transaction;
import com.my.atark.domain.TransactionType;
import com.my.atark.domain.UserRole;
import com.my.atark.service.ITransactionServ;
import com.my.atark.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class CommandOpenTransMngPage implements ICommand {

    private static final Logger log = Logger.getLogger(CommandOpenTransMngPage.class);

    @Override
    public ExecutionResult execute(SessionRequestContent content) {
        Configuration conf = Configuration.getInstance();
        ExecutionResult result = new ExecutionResult();
        result.setDirection(Direction.FORWARD);
        try {
            if (!Security.checkSecurity(content, UserRole.CASHIER, UserRole.SENIOR_CASHIER, UserRole.ADMIN)) {
                result.setPage(conf.getPage("securityError"));
                return result;
            }
            String type = (String) content.getRequestParameter("type")[0];
            ITransactionServ serv = ServiceFactory.getTransactionService();
            List<Transaction> transactions = new LinkedList<>();
            if (type.equals("all"))
                transactions = serv.findAllTransactions();
            if (type.equals("payment"))
                transactions = serv.findAllTransactionsByType(TransactionType.PAYMENT);
            if (type.equals("refund"))
                transactions = serv.findAllTransactionsByType(TransactionType.REFUND);
            result.addRequestAttribute("transactions", transactions);
            result.setPage(conf.getPage("manageTransactions"));
        }
        catch (Exception e) {
            log.error(e);
            result.addRequestAttribute("errorMessage", conf.getErrorMessage("manageTransactionsErr"));
            result.setPage(conf.getPage("error"));
        }
        return result;
    }
}
