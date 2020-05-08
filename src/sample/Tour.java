package sample;

public class Tour {
    private Integer id;
    private String hotel;
    private String stars;
    private String price;
    private String location;
    private String description;
    private String from;

    public Tour() {
    }

    public Tour(Integer id, String hotel, String stars, String price, String location, String description, String from) {
        this.id = id;
        this.hotel = hotel;
        this.stars = stars;
        this.price = price;
        this.location = location;
        this.description = description;
        this.from=from;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Tour{" +
                ", hotel='" + hotel + '\'' +
                ", stars='" + stars + '\'' +
                ", price='" + price + '\'' +
                ", from='" + from + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}