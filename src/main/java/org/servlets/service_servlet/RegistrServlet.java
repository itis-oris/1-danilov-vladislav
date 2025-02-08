package org.servlets.service_servlet;

import org.DB.DBHelper;
import org.models.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registr")
public class RegistrServlet extends HttpServlet {
    private DBHelper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DBHelper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if(!req.getParameter("nameReq").isEmpty() && !req.getParameter("passwordReq").isEmpty() && !req.getParameter("phoneReq").isEmpty()){
            String name = req.getParameter("nameReq");
            String password = req.getParameter("passwordReq");
            String phone = "+7" + req.getParameter("phoneReq");

            if (db_helper.checkUser(name)){
                req.getSession().setAttribute("error", "Такой пользователь существует");
                resp.sendRedirect(getServletContext().getContextPath()+"/");
                return;
            }
            db_helper.addUserToDatabase(new User(name,password,"default",phone));
            req.getSession().setAttribute("username",name);
            req.getSession().setAttribute("status",db_helper.getUser(name).getStatus());
        }else{
            req.getSession().setAttribute("error", "Не все данные указаны");
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/");
    }
}
