package main.Implementations.Hashing;


public class Main {
    public static void main(String[] args) throws Exception {
        Database db = Database.getInstance();

        db.registerUser();
        System.out.println(db.getUser("amy"));
        //db.getUser("new");
    }
}
