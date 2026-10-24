package com.tourstravels.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity
public class Destination {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String name; @NotBlank private String country;
 @Column(length=1200) private String description;
 @NotBlank private String imageUrl;
 private String category="Popular"; private double rating=4.8; private boolean featured=true; private boolean active=true;
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getCountry(){return country;} public void setCountry(String v){country=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;} public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public double getRating(){return rating;} public void setRating(double v){rating=v;} public boolean isFeatured(){return featured;} public void setFeatured(boolean v){featured=v;}
 public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
