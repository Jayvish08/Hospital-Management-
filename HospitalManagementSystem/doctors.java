package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class doctors {
    private Connection connection;
    public doctors(Connection connection){
        this.connection = connection;
    }
    public void viewdoctors(){
        String sql = "Select * from doctors";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("doctors :");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("dname");
                String spec = rs.getString("specialization");
                System.out.printf("| %-10s | %-18s | %-16s |\n",id,name,spec);
                System.out.println("+------------+--------------------+------------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkdoctor(int id){
        String sql = "Select * from doctors where id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
            else
                return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
