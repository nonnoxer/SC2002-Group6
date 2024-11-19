package data.user;

import java.util.ArrayList;

import user.Role;
import user.Staff;
import user.UserId;

public interface UserDatabaseApiAdministrator {
    public abstract ArrayList<Staff> getStaff();
    public abstract Staff addStaff(String name, Role role, String gender, int age);
    public abstract Staff updateStaff(UserId id, String name, Role role, String gender, int age);
    public abstract Staff removeStaff(UserId id);
}
