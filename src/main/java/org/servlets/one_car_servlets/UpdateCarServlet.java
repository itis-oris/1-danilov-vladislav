package org.servlets.one_car_servlets;

import org.DB.DBHelper;
import org.models.Brand;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(urlPatterns = {"/list/info/update","/my_cars/info/update","/my_likes/info/update","/user_cars/info/update","/list_of_reports/info/update"})
public class UpdateCarServlet extends HttpServlet {
    private DBHelper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DBHelper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Brand brand = new Brand("Не изменять", null);
        brand.setId(-1);
        LinkedList<Brand> list = new LinkedList<>();
        list.add(brand);
        list.addAll(db_helper.getAllBrands());
        req.setAttribute("list", list);
        req.setAttribute("uri", collectTheString(req.getRequestURI()) + "?number=" + req.getParameter("auto_id"));
        req.setAttribute("car", db_helper.getAutoById(Integer.parseInt(req.getParameter("auto_id"))));
        req.getRequestDispatcher("/WEB-INF/jsps/update_car.jsp").forward(req, resp);
    }

    private String collectTheString(String uri){
        String[] arr = uri.split("/");
        String rez = "/";
        for (int i = 1; i < arr.length - 1; i++) {
            rez += arr[i];
            if (i != arr.length - 2) {
                rez += "/";
            }
        }
        return rez;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(Integer.parseInt(req.getParameter("brand")) != -1) {
            db_helper.updateAutoById_brand(Integer.parseInt(req.getParameter("auto_id")),Integer.parseInt(req.getParameter("brand")));
        }
        if (!req.getParameter("car_model").isEmpty() &&
                !req.getParameter("year").isEmpty() &&
                !req.getParameter("price").isEmpty() &&
                !req.getParameter("mileage").isEmpty() &&
                !req.getParameter("city").isEmpty() &&
                !(req.getParameter("description") == null)) {
            int year = Integer.parseInt(req.getParameter("year"));
            int price = Integer.parseInt(req.getParameter("price"));
            int mileage = Integer.parseInt(req.getParameter("mileage"));
            String city = req.getParameter("city");
            String description = req.getParameter("description");
            String auto_model = req.getParameter("car_model");
            int auto_id = Integer.parseInt(req.getParameter("auto_id"));
            if(year >= 1900 && year <= 2024 && price >= 0 &&
                    mileage >= 0){
                db_helper.updateAutoById(auto_id, auto_model, year, price, mileage, city, description);
            }
        }
        resp.sendRedirect(req.getContextPath()+collectTheString(req.getRequestURI()) + "?number=" + req.getParameter("auto_id"));
    }
}
