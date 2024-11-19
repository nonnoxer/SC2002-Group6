package data.user;

import java.util.ArrayList;

import user.Role;
import user.Staff;

public interface UserDatabaseApiAdministrator {
    public abstract ArrayList<Staff> getStaff();
    public abstract Staff addStaff(String id, String name, Role role, String gender, int age);
    public abstract Staff updateStaff(String id, String name, Role role, String gender, int age);
    public abstract Staff removeStaff(String id);
}
