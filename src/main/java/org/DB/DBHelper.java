package org.DB;
import org.models.*;

import java.io.InputStream;
import java.util.List;

public interface DBHelper {
    boolean addImageToThisAuto(InputStream inputStream,String is, int auto_id);

    boolean deleteUserByName(String name);

    int getEmpIdByName(String name);

    Employee getEmpById(int emp_id);

    boolean addImageToThisEmp(InputStream is, int emp_id);

    boolean deleteImageById(int image_id);

    int getImageIdFromThisAutoWithNumber(int auto_id, int number);

    List<byte[]> getImageFromThisAuto(int auto_id);

    byte[] getImageFromThisEmp(int emp_id);

    int getCountOfImageFromThisAuto(int auto_id);

    List<User> getAllUsers();

    List<AutoModel> getFilterAutoLike(String brand_id, String model, String sort, String user_id, int this_user_id, String city);

    List<AutoModel> getFilterAuto(String brand_id, String model, String sort, String user_id, String city);

    void updateAutoById_city(int auto_id, String city);

    boolean addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    List<User> getAllUsers(String str);

    boolean checkUser(String username);

    User getUser(String username);

    boolean checkPermission(String username, int auto_id);

    int getLastAutoFromThisUser(String username);

    boolean addReport(Report report);

    List<Report> getAllReports();

    boolean deleteReport(int rep_id);

    boolean checkUsersPassword(String username, String password);

    boolean addUserToDatabase(User user);

    boolean deleteEmpById(int emp_id);

    boolean deleteAutoById(int auto_id);

    boolean addAutoToDatabase(AutoModel auto);

    boolean addLikeToDatabase(int user_id, int auto_id);

    List<Integer> getAllLikes(int user_id);

    boolean deleteLike(int user_id, int auto_id);

    boolean checkLike(int user_id, int auto_id);

    boolean addBrandToDatabase(Brand brand);

    List<AutoModel> getAllAuto();

    List<AutoModel> getAutoByThisIds(List<Integer> ints);

    List<AutoModel> getAllAuto(String username);

    List<Brand> getAllBrands();

    Brand getBrandById(int id);

    User getUserById(int id);

    AutoModel getAutoById(int id);

    boolean changeStatusThisUser(int user_id, String status);

    void updateAutoById_brand(int auto_id, int brand_id);

    void updateAutoById_model(int auto_id, String model);

    void updateAutoById_year(int auto_id, int year);

    void updateAutoById_price(int auto_id, int price);

    void updateAutoById_mileage(int auto_id, int mileage);

    void updateAutoById_description(int auto_id, String description);

    void updateAutoById(int auto_id, String model, int year, int price,int mileage,String city, String description);

    Brand getBrandByName(String brand_name);
}
