import java.util.Scanner;

public class Main {
    public static final int CREATE_USER = 1;
    public static final int SIGN_IN = 2;
    public static final int END = 3;

    public static void main(String[] args) {
        RealEstate realEstate = new RealEstate();
        int choice = 0;
        while (choice!=3) {
            choice = firstMenu(realEstate);
        }
    }

    public static int firstMenu(RealEstate realEstate) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pick option: " + "\n" +
                "1- createUser" + "\n" +
                "2- sign in" + "\n" +
                "3- end");
        int choice = scanner.nextInt();

        switch (choice) {
            case CREATE_USER:
                realEstate.createUser();
                break;
            case SIGN_IN:
                User user = realEstate.login();
                if (user==null) {
                    System.out.println("There is no such a user");
                }
                else {
                    int choice2 = 0;
                    while (choice2 != 6) {
                        choice2 = secondMenu(realEstate, user);
                    }
                }
                break;
            case END:
                break;

        }
        return choice;
    }

    public static final int POST_PROPERTY = 1;
    public static final int REMOVE_PROPERTY = 2;
    public static final int PRINT_ALL_PROPERTIES = 3;
    public static final int PRINT_USERS_PROPERTIES = 4;
    public static final int SEARCH = 5;
    public static final int LOG_OUT = 6;

    public static int secondMenu(RealEstate realEstate, User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pick option: "+"\n"+
                "1- publish new property" + "\n"+
                "2- remove property" + "\n" +
                "3- show all properties in RealEstate" + "\n"+
                "4- show your properties"+"\n" +
                "5- search property"+"\n" +
                "6- log out");
        int choice = scanner.nextInt();
        switch (choice) {
            case POST_PROPERTY:
                realEstate.postNewProperty(user);
                break;
            case REMOVE_PROPERTY:
                realEstate.removeProperty(user);
                break;
            case PRINT_ALL_PROPERTIES:
                realEstate.printAllProperties();
                break;
            case PRINT_USERS_PROPERTIES:
                realEstate.printAllProperties(user);
                break;
            case SEARCH:
                Property[] searched = realEstate.search();
                if (searched!=null) {
                    for (int i=0; i<searched.length; i++) {
                        System.out.println(searched[i]);
                    }
                }
                break;
            case LOG_OUT:
                break;
        }
        return choice;
    }

}