package user;

public class UserId {
    private char prefix;
    private int num;

    public UserId(String id) throws IllegalArgumentException {
        if (id.length() < 2) {
            throw new IllegalArgumentException("User ID should have at least a character and a number");
        }
        try {
            this.prefix = id.charAt(0);
            this.num = Integer.parseInt(id.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(id.substring(1) + " is not a valid number.");
        }
    }

    public UserId(char prefix, int num) {
        this.prefix = prefix;
        this.num = num;
    }

    public String toString() {
        return String.format("%c%03d", prefix, num);
    }
    
    public char getPrefix() {
        return this.prefix;
    }

    public int getNum() {
        return this.num;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }
}
