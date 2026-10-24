package com.tourstravels.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import java.math.BigDecimal;
@Entity public class Hotel {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String name; @NotBlank private String location; @Column(length=1000) private String description; @NotBlank private String imageUrl;
 @NotNull @DecimalMin("0.0") private BigDecimal pricePerNight=BigDecimal.ZERO; private double rating=4.5; private String hotelType="Hotel"; private boolean active=true;
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getLocation(){return location;} public void setLocation(String v){location=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;} public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
 public BigDecimal getPricePerNight(){return pricePerNight;} public void setPricePerNight(BigDecimal v){pricePerNight=v;} public double getRating(){return rating;} public void setRating(double v){rating=v;}
 public String getHotelType(){return hotelType;} public void setHotelType(String v){hotelType=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
