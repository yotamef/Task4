public class Property {
    private Address address;
    private int numRooms;
    private int price;
    private int kind;
    private boolean forRent;
    private int houseNumber;
    private int floor;
    private User owner;

    public Property(String city, String street, int numRooms, int price, int kind, boolean forRent, int houseNumber, int floor, User owner) {
        Address address = new Address(city, street);
        this.address = address;
        this.numRooms = numRooms;
        this.price = price;
        this.kind = kind;
        this.forRent = forRent;
        this.houseNumber = houseNumber;
        this.floor = floor;
        this.owner = owner;
    }

    public User getOwner() {
        return this.owner;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public boolean isForRent() {
        return forRent;
    }

    public void setForRent(boolean forRent) {
        this.forRent = forRent;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean equals(Property property) {
        boolean equals = true;
        if (!this.address.equals(property.getAddress()))
            equals = false;
        if (this.numRooms != property.getNumRooms())
            equals = false;
        if (this.floor != property.getFloor())
            equals = false;
        if (this.price != property.getPrice())
            equals = false;
        if (this.kind != property.getKind())
            equals = false;
        if (this.houseNumber != property.getHouseNumber())
            equals = false;
        if (this.forRent != property.isForRent())
            equals = false;
        if (!this.owner.equals(property.getOwner()))
            equals = false;
        return equals;
    }

    public String toString() {
        String toReturn = "";
        String kindStr = "";
        if (kind==1) {
            kindStr = "regular apartment";
        }
        else if (kind==2) {
            kindStr = "penthouse";
        }
        else {
            kindStr = "private house";
        }

        toReturn = kindStr + " –  for"+ (this.forRent? " rent":" sell")+": "+numRooms+" rooms, floor "+floor+".\n" +
                "Price: " +this.price+ "$.\n" +
                "Contact info:"+ owner.getUsername()+ " "+ owner.getPhoneNumber()+ " (real estate"+ (owner.isAgent()?" broker":" regular user")+")"+"\n";
        return toReturn;
    }
}
