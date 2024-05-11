package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class patients {
    private Connection connection;
    private Scanner scanner;
    public patients(Connection connection,Scanner scanner){
            this.connection = connection;
            this.scanner=scanner;
    }
    public void addpatients(int id){
        System.out.println("Enter the patient name :");
        String name  = scanner.next();
        System.out.println("Enter patient's age :");
        int age = scanner.nextInt();
        System.out.println("Enter patient's gender");
        String gender = scanner.next();
        try{
            String sql = "insert into patients values(?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id+1);
            ps.setString(2,name);
            ps.setInt(3,age);
            ps.setString(4,gender);
            int affectedrows = ps.executeUpdate();
            if(affectedrows>0){
                System.out.println("Patient's Data Saved Successfully!!");
            }
            else{
                System.out.println("Might have some problem !!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void viewpatients(){
        String sql = "Select * from patients";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Patients :");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("pname");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("| %-10s | %-18s |%-8s | %-10s |\n",id,name,age,gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkpatient(int id){
        String sql = "Select * from patients where id = ?";
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
