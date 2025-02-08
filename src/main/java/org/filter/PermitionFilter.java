package org.filter;

import org.DB.DBHelper;
import org.models.AutoModel;
import org.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebFilter("/*")
public class PermitionFilter implements Filter {
    private DBHelper db_helper;
    private String[] pagesNeedUser = {"add", "my_likes", "my_cars", "setting_like", "report","delete_my_account"};
    private String[] pagesNeedPermOwnmer = {"add_employee", "emp_image", "delete_emp", "doAdmin"};
    private String[] pagesNeedAuthor = { "image", "delete", "delete_image", "update"};
    private String[] pagesNeedAdmin = { "list_of_reports", "delete_report"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        db_helper = (DBHelper) filterConfig.getServletContext().getAttribute("database");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String[] list = req.getRequestURI().split("/");
        if (list.length == 0) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        String check = list[list.length - 1];
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (Arrays.stream(pagesNeedUser).collect(Collectors.toList()).contains(check)) {
            if (req.getSession().getAttribute("username") == null) {
                resp.sendRedirect(req.getContextPath() + "/");
            }
        } else {
            if (Arrays.stream(pagesNeedPermOwnmer).collect(Collectors.toList()).contains(check)) {
                if (req.getSession().getAttribute("status") == null || !req.getSession().getAttribute("status").equals("owner")) {
                    resp.sendRedirect(req.getContextPath() + "/");
                }
            } else {
                if (Arrays.stream(pagesNeedAdmin).collect(Collectors.toList()).contains(check)) {
                    if (req.getSession().getAttribute("status") == null || req.getSession().getAttribute("status").equals("default")) {
                        resp.sendRedirect(req.getContextPath() + "/");
                    }
                } else {
                    if (Arrays.stream(pagesNeedAuthor).collect(Collectors.toList()).contains(check)) {
                        if (req.getParameter("auto_id") == null || req.getParameter("auto_id").isEmpty()
                                || req.getSession().getAttribute("username") == null) {
                            resp.sendRedirect(req.getContextPath() + "/");
                            return;
                        }
                        if (!db_helper.checkPermission((String) req.getSession().getAttribute("username"), Integer.parseInt(req.getParameter("auto_id")))
                                && db_helper.getUser((String) req.getSession().getAttribute("username")).getStatus().equals("default")
                        ) {
                            resp.sendRedirect(req.getContextPath() + "/");
                            return;
                        } else {
                            req.setAttribute("pravo", true);
                        }
                    } else {
                        if (check.contains("info")) {
                            AutoModel auto;
                            try {
                                auto = db_helper.getAutoById(Integer.parseInt(req.getParameter("number")));
                            } catch (Exception e) {
                                resp.sendRedirect(req.getContextPath() + "/");
                                return;
                            }
                            if (req.getSession().getAttribute("username") != null) {
                                User user = db_helper.getUser((String) req.getSession().getAttribute("username"));
                                boolean flag = db_helper.checkPermission(user.getName(), auto.getId()) || !user.getStatus().equals("default");
                                req.setAttribute("pravo", flag);
                            }
                        } else {
                            if (check.equals("list_of_emp")) {
                                User user = db_helper.getUser((String) req.getSession().getAttribute("username"));
                                if (user.getStatus().equals("owner")) {
                                    req.setAttribute("pravo", true);
                                }
                            }
                        }

                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
