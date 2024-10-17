package menus;

import user.Doctor;
import java.util.Scanner;

import user.Patient;
import user.User;

public class DoctorMenu extends Menu{
    @Override
    public void showMenu(User user){

        Doctor doctor = null;
        if (user instanceof Doctor){               //explicit down casting so that the pharmacist methods don't have compilation error
            doctor = (Doctor) user;               //as they don't exist in superclass User
        }else{
            System.out.println("This user is not a , check the method call");
        }
    }


}