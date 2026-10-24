package com.tourstravels.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import java.time.LocalDateTime;
@Entity public class Review {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private AppUser user; @ManyToOne(fetch=FetchType.EAGER) private TourPackage tourPackage;
 @Min(1) @Max(5) private int rating=5; @NotBlank @Column(length=1000) private String comment; private LocalDateTime createdAt=LocalDateTime.now(); private boolean approved=true;
 public Long getId(){return id;} public AppUser getUser(){return user;} public void setUser(AppUser v){user=v;} public TourPackage getTourPackage(){return tourPackage;} public void setTourPackage(TourPackage v){tourPackage=v;}
 public int getRating(){return rating;} public void setRating(int v){rating=v;} public String getComment(){return comment;} public void setComment(String v){comment=v;} public LocalDateTime getCreatedAt(){return createdAt;} public boolean isApproved(){return approved;} public void setApproved(boolean v){approved=v;}
}
