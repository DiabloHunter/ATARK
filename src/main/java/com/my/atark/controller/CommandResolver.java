package com.my.atark.controller;

import com.my.atark.commands.ICommand;
import com.my.atark.commands.implementation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandResolver {

    private static CommandResolver instance = null;
    Map<String, ICommand> commands = new HashMap<>();

    public CommandResolver() {
        /** Commands available for User */
        commands.put("enter", new CommandOpenLoginPage());
        commands.put("login", new CommandValidateUser());
        commands.put("logout", new CommandLogout());
        commands.put("main", new CommandOpenMainPage());
        commands.put("usersCart", new CommandOpenUsersCart());
        commands.put("addProductToCart", new CommandAddToCart());
        commands.put("getProductDetails", new CommandOpenProductDetails());
        commands.put("removeProductFromCart", new CommandRemoveFromCart());
        commands.put("createInvoice", new CommandCreateInvoice());
        commands.put("createInvoiceAndPay", new CommandCreateInvoiceAndPay());
        commands.put("addNewUser", new CommandOpenRegistrationPage());
        commands.put("registerNewUser", new CommandSaveNewUser());
        commands.put("showUserProfile", new CommandOpenUserProfilePage());
        commands.put("saveUserProfile", new CommandSaveUserProfile());
        commands.put("showProductDetails", new CommandShowProductDetails());
        commands.put("changeLang", new CommandChangeLang());
        /** Commands available for Administration */
        commands.put("administration", new CommandOpenAdminPage());
        commands.put("manageInvoices", new CommandOpenInvoiceMngPage());
        commands.put("manageUsers", new CommandOpenUserMngPage());
        commands.put("manageProducts", new CommandOpenProductMngPage());
        commands.put("manageTransactions", new CommandOpenTransMngPage());
        commands.put("showInvoiceDetails", new CommandOpenInvDetailsPage());
        commands.put("removeProductFromInvoice", new CommandRemoveFromInvoice());
        commands.put("closeInvoice", new CommandCloseInvoice());
        commands.put("cancelInvoice", new CommandCancelInvoice());
        commands.put("addNewProductPage", new CommandOpenNewProductPage());
        commands.put("saveNewProduct", new CommandSaveNewProduct());
        commands.put("editProductPage", new CommandOpenEditProductPage());
        commands.put("updateProduct", new CommandUpdateProduct());
        commands.put("deleteProduct", new CommandDeleteProduct());
        commands.put("addNewPayment", new CommandAddNewPayment());
        commands.put("confirmPayment", new CommandConfirmPayment());
        commands.put("getUserDetails", new CommandOpenUserDetails());
        commands.put("saveUserProfileAsAdmin", new CommandSaveUserProfileAsAdmin());
        commands.put("deleteUserAccount", new CommandDeleteUser());
        commands.put("viewUserProfile", new CommandShowUserDetails()); //useless
        commands.put("openAddNewUserAsAdmin", new CommandOpenAddNewUserAsAdmin());
        commands.put("addNewUserAsAdmin", new CommandAddNewUserAsAdmin());

    }

    public ICommand getCommand(HttpServletRequest request) {
        ICommand command = commands.get(request.getParameter("command"));
        if (command == null)
            command = new CommandMissing();

        return command;
    }

    public static CommandResolver getInstance() {
        if (instance == null)
            instance = new CommandResolver();
        return instance;
    }
}