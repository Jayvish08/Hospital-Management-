package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class hospitalmanagementsystem
{ static final int id = 0;
    private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String username = "hospital";
    private static final String password = "doctor";

    public static void main(String []args){
        try{
            Class.forName("oracle.jdbc.OracleDriver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            patients p = new patients(connection, scanner);
            doctors  d = new doctors(connection);
            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();
                switch (choice){
                    case 1 :
                        p.addpatients(id);
                        System.out.println();
                        break;
                    case 2 :
                        p.viewpatients();
                        System.out.println();
                        break;
                    case 3 :
                        d.viewdoctors();
                        System.out.println();
                        break;
                    case 4 :
                        bookAppointment(p,d,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("Thank you!! For using Hosptal Management System!!");
                        return;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static boolean bookAppointment(patients p , doctors d , Connection connection,Scanner scanner){
        System.out.println("Enter Patient's Id");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor's Id");
        int doctorId = scanner.nextInt();
        System.out.println("Enter Appointment date (DD-MMM-YYYY): ");
        String appointmentdate = scanner.next();
        if (p.checkpatient(patientId) && d.checkdoctor(doctorId)){
            if(checkdoctoravailability(doctorId,appointmentdate,connection)){
                String query = "insert into appointment values(?, ?, ?, ?)";
                try{
                    int id = 0;
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1,id+1);
                    preparedStatement.setInt(2, patientId);
                    preparedStatement.setInt(3, doctorId);
                    preparedStatement.setString(4, appointmentdate);
                    int rowaffected = preparedStatement.executeUpdate();
                    if (rowaffected>0)
                        System.out.println("Appointment Booked !!");
                    else
                        System.out.println("Failed to Book Appointment!!");
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
          else
                System.out.println("Doctor not available on this date");
        }
        else
            System.out.println("Either doctor or patient doesn't exist!!!");
        return false;
    }
    public static boolean checkdoctoravailability(int doctorId,String appointmentdate,Connection connection){
        String sql = "Select Count(*) from appointment where doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentdate);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                if(count==0) return true;
                else return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
