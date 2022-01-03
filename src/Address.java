public class Address {
    private String city;
    private String street;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

    public boolean equals(Address address) {
        return (this.street.equals(address.getStreet()) && this.city.equals(address.getCity()));
    }

    public void setCity(String city) {
        this.city = city;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}
