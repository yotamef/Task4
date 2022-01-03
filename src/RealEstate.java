import java.util.Scanner;

public class RealEstate {
    private User[] users;
    private Property[] properties;
    private Address[] addresses;

    public static final int NUMBER_OF_ADDRESSES = 10;

    public RealEstate() {
        users = null;
        properties = null;
        addresses  = new Address[NUMBER_OF_ADDRESSES];
        for (int i=0; i<addresses.length; i++) {
            addresses[i] = new Address(null, null);
        }
        for (int i=0; i<NUMBER_OF_ADDRESSES; i=i+1) {
            if (i<3) {
                addresses[i].setCity("Tel Aviv");
            }
            else if(i<6) {
                addresses[i].setCity("Ashkelon");
            }
            else if (i<10) {
                addresses[i].setCity("Jerusalem");
            }
            if (i%3==0 && i!=9) {
                addresses[i].setStreet("Ben Guryon");
            }
            else if (i%3==1) {
                addresses[i].setStreet("Hertzel");
            }
            else if (i%3==2) {
                addresses[i].setStreet("Hagadid");
            }
            else {
                addresses[i].setStreet("Ha'atzmaut");
            }
        }
    }
    private static final String NO_PARAMETER = "-999";

    public Property[] search() {
        Scanner scanner = new Scanner(System.in);
        boolean rentParameter = true, kindParameter = true, roomsParameter = true, priceParameterMax = true, priceParameterMin = true;

        System.out.println("true- for rent or false- for sell?");
        String forRentStr = scanner.nextLine();
        rentParameter = check999(forRentStr);

        System.out.println("1- for regular apartment, 2- for penthouse, 3- for private house");
        int kind = scanner.nextInt();
        kindParameter = check999(kind+"");

        System.out.println("How many rooms do you want?");
        int rooms = scanner.nextInt();
        roomsParameter = check999(rooms+"");

        System.out.println("insert max price");
        int maxPrice = scanner.nextInt();
        priceParameterMax = check999(maxPrice+"");

        System.out.println("Insert min price");
        int minPrice = scanner.nextInt();
        priceParameterMin = check999(minPrice+"");

        Property[] searched = null;
        if (properties!=null) {
            for (int i=0; i<properties.length; i++) {
                boolean good = true;
                if (rentParameter) {
                    if (properties[i].isForRent()!=Boolean.parseBoolean(forRentStr))
                        good = false;
                }
                if (kindParameter) {
                    if (properties[i].getKind()!=kind)
                        good = false;
                }
                if (roomsParameter) {
                    if (properties[i].getNumRooms()!=rooms)
                        good = false;
                }
                if (priceParameterMax) {
                    if (properties[i].getPrice()>maxPrice)
                        good = false;
                }
                if (priceParameterMin) {
                    if (properties[i].getPrice()<minPrice)
                        good = false;
                }

                if (good) {
                    searched = addProperty(properties[i],searched);
                }

            }
        }
        return searched;
    }
    private boolean check999(String str) {
        if (str.equals(NO_PARAMETER))
            return false;
        else
            return true;
    }


    public void printAllProperties () {
        if (properties!=null) {
            for (int i=0; i<properties.length; i++) {
                System.out.println(properties[i]);
            }
        }
    }

    public void removeProperty(User user) {
        Scanner scanner = new Scanner(System.in);
        int numberOfProperties = propertiesByUser(user);
        if (numberOfProperties==0) {
            System.out.println("you haven't publish properties yet");
        }
        else {
            printAllProperties(user);
            System.out.println("choose property to remove");
            int remove = scanner.nextInt();
            Property toRemove = getUsersProperty(user,remove);
            removeProperty(toRemove);
            System.out.println("The property removed successfully");
        }
    }
    private void removeProperty(Property property) {
        Property[] properties1 = new Property[properties.length-1];
        int j=0;
        for (int i=0; i<properties.length; i++) {
            if (!properties[i].equals(property)) {
                properties1[j] = properties[i];
                j++;
            }
        }
        properties = properties1;
    }

    public boolean postNewProperty(User user) {
        int limit;
        if(user.isAgent()) {
            limit = 10;
        }
        else {
            limit = 3;
        }

        int countProperties = propertiesByUser(user);
        if (countProperties>=limit) {
            System.out.println("You have reached your properties limit");
            return false;
        }
        else {
            Scanner scanner = new Scanner(System.in);
            printCities();
            System.out.println("Pick city");
            String city = scanner.nextLine();

            boolean cityExist = printStreets(city);
            if (!cityExist) {
                System.out.println("There is no such city");
                return false;
            }

            System.out.println("Pick street");
            String street = scanner.nextLine();
            if (!streetExistInCity(city,street)) {
                System.out.println("no such a street");
                return false;
            }

            System.out.println("Insert kind of property: 1- regular apartment, 2- penthouse, 3- private house ");
            int kind = scanner.nextInt();
            int floor;
            if (kind==1) {
                System.out.println("insert floor");
                floor = scanner.nextInt();
            }
            else {
                floor = -1;
            }

            System.out.println("insert number of rooms");
            int rooms = scanner.nextInt();

            System.out.println("insert house number");
            int houseNumber = scanner.nextInt();

            System.out.println("insert 1 for rent and 2 for sell");
            int forRent = scanner.nextInt();
            boolean rent;
            if (forRent==1) {
                rent = true;
            }
            else {
                rent = false;
            }

            System.out.println("insert price");
            int price = scanner.nextInt();

            Property newProperty = new Property(city, street, rooms, price, kind, rent, houseNumber, floor, user);
            addProperty(newProperty, this.properties);
            return true;
        }
    }
    private int propertiesByUser(User user) {
        int countProperties = 0;
        if (properties==null)
            return 0;
        for (int i=0; i< properties.length; i++) {
            if (properties[i].getOwner().equals(user))
                countProperties++;
        }
        return countProperties;
    }
    public void printAllProperties(User user) {
        int number = 1;
        if (properties!= null) {
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].getOwner().equals(user)) {
                    System.out.println(number + ". " + properties[i]);
                    System.out.println();
                    number++;
                }
            }
        }
    }
    private Property getUsersProperty(User user, int index) {
        int number = 1;
        Property toReturn = null;
        if (properties!=null) {
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].getOwner().equals(user)) {
                    if (number == index) {
                        toReturn = properties[i];
                    }
                    number++;
                }
            }
        }
        return toReturn;
    }
    private Property[] addProperty(Property property, Property [] properties) {
        Property[] properties1;
        if (properties!=null) {
            properties1 = new Property[properties.length+1];
            for (int i=0; i< users.length; i++) {
                properties1[i] = properties[i];
            }
        }
        else {
            properties1 = new Property[1];
        }
        properties1[properties1.length-1] = property;
        if (properties == this.properties) {
            this.properties = properties1;
        }
        else {
            properties = properties1;
        }
        return properties;
    }

    private boolean streetExistInCity(String city, String street) {
        boolean exist = false;
        for (int i=0; i<addresses.length; i++) {
            if (addresses[i].getCity().equals(city) && addresses[i].getStreet().equals(street)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    private boolean printStreets(String city) {
        boolean thereStreets = false;
        for (int i=0; i<addresses.length; i++) {
            if (addresses[i].getCity().equals(city)) {
                thereStreets = true;
                System.out.println(addresses[i].getStreet()+", ");
            }
        }
        System.out.println();
        return thereStreets;
    }

    private void printCities() {
        String[] cities = new String[addresses.length];
        for (int i=0; i<cities.length; i++) {
            boolean exist = false;
            for (int j=0; j<i; j++) {
                if (cities[j]!=null) {
                    if (cities[j].equals(addresses[i].getCity())) {
                        exist = true;
                    }
                }
            }
            if (!exist) {
                cities[i] = addresses[i].getCity();
            }
        }
        for (int i=0; i<cities.length; i++) {
            if (cities[i]!=null)
                System.out.print(cities[i] +", ");
        }
    }

    public User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("username: ");
        String username = scanner.nextLine();
        System.out.println("password: ");
        String password = scanner.nextLine();
        int user = -1;
        if (users!=null) {
            for (int i = 0; i < users.length; i++) {
                if (users[i].getUsername().equals(username) && users[i].getPassword().equals(password))
                    user = i;
            }
        }
        if (user==-1)
            return null;
        else {
            return users[user];
        }
    }

    public void createUser() {
        Scanner scanner = new Scanner (System.in);
        User newUser = new User();

        String username = returnAnswer("Insert username", 1);
        newUser.setUsername(username);

        String password = returnAnswer("Insert password", 2);
        newUser.setPassword(password);

        String phoneNumber = returnAnswer("Insert legal phone number", 3);
        newUser.setPhoneNumber(phoneNumber);

        int choice;
        do {
            System.out.println("Insert 1 for agent and 2 for regular user");
            choice = scanner.nextInt();
            if (choice == 1)
                newUser.setAgent(true);
            else if (choice == 2)
                newUser.setAgent(false);
        } while(choice!=1 && choice!=2);

        addUser(newUser);
        System.out.println("User added");
    }

    private String returnAnswer(String text, int function) {
        Scanner scanner = new Scanner(System.in);
        String toReturn = "";
        do {
            System.out.println(text);
            toReturn = scanner.nextLine();
        } while (function==1 ? !unExist(toReturn) : (function==2 ? !strongPassword(toReturn) : !legalPhone(toReturn)));

        return toReturn;
    }

    private void addUser(User user) {
        User[] users1;
        if (users!=null) {
        users1 = new User[users.length+1];
            for (int i=0; i< users.length; i++) {
                users1[i] = users[i];
            }
        }
        else {
            users1 = new User[1];
        }
        users1[users1.length-1] = user;
        users = users1;
    }

    private boolean unExist(String username) {
        boolean flag = true;
        if (users!=null) {
            for (int i = 0; i < this.users.length; i++) {
                if (username.equals(users[i].getUsername()))
                    flag = false;
            }
        }
        return flag;
    }
    private boolean strongPassword(String password) {
        boolean special = false;
        boolean number = false;
        for (int i=0; i<password.length(); i++) {
            if (password.charAt(i)>=48 && password.charAt(i)<=57)
                number = true;
            else if (password.charAt(i) == '_' || password.charAt(i) == '$' || password.charAt(i) == '%')
                special = true;
        }
        return (special && number);
    }
    private boolean legalPhone(String number) {
        return number.length()==10 && number.charAt(0) == '0' && number.charAt(1) == '5';
    }

}
