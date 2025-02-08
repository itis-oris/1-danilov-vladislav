package org.servlets.list_servlets;

import org.DB.DBHelper;
import org.models.AutoModel;
import org.models.Brand;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/list")
public class ListOfCarsServlet extends HttpServlet {
    private DBHelper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DBHelper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AutoModel> list;
        String brand_id = req.getParameter("brand");
        String model = req.getParameter("car_model");
        String sort = req.getParameter("sort");
        String city = req.getParameter("city");
        if (brand_id == null){
            list = db_helper.getAllAuto();
        }
        else{
            list = db_helper.getFilterAuto(brand_id, model, sort, null, city);
        }
        req.setAttribute("list", list);
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(0, "None","None"));
        brands.addAll(db_helper.getAllBrands());
        req.setAttribute("brands", brands);
        String[] uris = req.getRequestURI().split("/");
        String uri = uris[uris.length - 1];
        req.setAttribute("uri", "/" + uri);
        req.setAttribute("back", uris[uris.length - 2]);
        req.getRequestDispatcher("/WEB-INF/jsps/list_of_cars.jsp").forward(req, resp);
    }
}
