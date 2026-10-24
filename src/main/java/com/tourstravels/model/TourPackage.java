package com.tourstravels.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
@Entity @Table(name="tour_packages")
public class TourPackage {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String title; @ManyToOne(fetch=FetchType.EAGER) private Destination destination;
 @Column(length=1200) private String description; @NotBlank private String imageUrl;
 @NotNull @DecimalMin("0.0") private BigDecimal price=BigDecimal.ZERO;
 private int durationDays=3; private int durationNights=2; private double rating=4.7; private String category="Adventure"; private boolean featured=true; private boolean active=true;
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public Destination getDestination(){return destination;} public void setDestination(Destination v){destination=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;} public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
 public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;} public int getDurationDays(){return durationDays;} public void setDurationDays(int v){durationDays=v;}
 public int getDurationNights(){return durationNights;} public void setDurationNights(int v){durationNights=v;} public double getRating(){return rating;} public void setRating(double v){rating=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;} public boolean isFeatured(){return featured;} public void setFeatured(boolean v){featured=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
