package menus;

import user.Administrator;
import user.User;

public class AdministratorMenu extends Menu{
    @Override
    public void showMenu(User user){
        Administrator administrator = null;
        if (user instanceof Administrator){               //explicit down casting so that the pharmacist methods don't have compilation error
            administrator = (Administrator) user;               //as they don't exist in superclass User
        }else {
            System.out.println("This user is not a , check the method call");
        }

    }
}



