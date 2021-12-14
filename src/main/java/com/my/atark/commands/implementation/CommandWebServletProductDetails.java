package com.my.atark.commands.implementation;

import com.my.atark.domain.Product;
import com.my.atark.exceptions.ProductServiceException;
import com.my.atark.service.IProductServ;
import com.my.atark.service.ServiceFactory;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getProductInfo", urlPatterns ="/api/products/getProductByCode/*")
public class CommandWebServletProductDetails extends HttpServlet {

    private Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        String str = request.getRequestURI();
        String code = str.split("=")[1];
        Product result = null;
        try {
            IProductServ productServ = ServiceFactory.getProductService();
            result = productServ.findProductByCode(code);
        }
        catch (ProductServiceException e) {
            e.printStackTrace();
        }
        String productJsonString = this.gson.toJson(result);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(productJsonString);
        out.flush();
    }


}
