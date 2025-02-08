package org.servlets.pages;

import org.DB.DBHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/getImage")
public class CarPageServlet extends HttpServlet {
    private DBHelper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DBHelper) config.getServletContext().getAttribute("database");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<byte[]> list = db_helper.getImageFromThisAuto(Integer.parseInt(req.getParameter("auto_id")));
        int numb = Integer.parseInt(req.getParameter("number"));
        resp.setContentType("image/*");
        OutputStream os = resp.getOutputStream();
        os.write(list.get(numb - 1));
        os.flush();
        os.close();
    }
}
