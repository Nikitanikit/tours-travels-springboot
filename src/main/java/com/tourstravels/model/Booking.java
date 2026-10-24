package com.tourstravels.model;
import jakarta.persistence.*; import java.math.BigDecimal; import java.time.LocalDate; import java.time.LocalDateTime; import java.time.LocalTime;
@Entity public class Booking {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false, fetch=FetchType.EAGER) private AppUser user;
 @ManyToOne(fetch=FetchType.EAGER) private TourPackage tourPackage;
 @ManyToOne(fetch=FetchType.EAGER) private Hotel hotel;
 private LocalDate startDate; private LocalDate endDate; private int travelers=2;
 @Column(nullable=false) private BigDecimal totalAmount=BigDecimal.ZERO;
 @Enumerated(EnumType.STRING) private BookingStatus status=BookingStatus.PENDING;
 private LocalDateTime createdAt=LocalDateTime.now();
 private String specialRequests;
 @Column(length=2000) private String travelPlan;
 @Column(length=1000) private String travelPlaces;
 private LocalDate travelDate;
 private LocalTime travelTime;
 private String pickupLocation;
 @Column(length=2000) private String adminNotes;
 @Column(nullable=false) private BigDecimal amountDue=BigDecimal.ZERO;
 private String paymentReference;
 private LocalDateTime paymentReceivedAt;
 @Column(length=500) private String planPdfPath;
 private boolean planSent=false;
 private boolean finalConfirmationSent=false;
 public Long getId(){return id;} public AppUser getUser(){return user;} public void setUser(AppUser v){user=v;} public TourPackage getTourPackage(){return tourPackage;} public void setTourPackage(TourPackage v){tourPackage=v;}
 public Hotel getHotel(){return hotel;} public void setHotel(Hotel v){hotel=v;} public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;} public LocalDate getEndDate(){return endDate;} public void setEndDate(LocalDate v){endDate=v;}
 public int getTravelers(){return travelers;} public void setTravelers(int v){travelers=v;} public BigDecimal getTotalAmount(){return totalAmount;} public void setTotalAmount(BigDecimal v){totalAmount=v;}
 public BookingStatus getStatus(){return status;} public void setStatus(BookingStatus v){status=v;} public LocalDateTime getCreatedAt(){return createdAt;} public String getSpecialRequests(){return specialRequests;} public void setSpecialRequests(String v){specialRequests=v;}
 public String getTravelPlan(){return travelPlan;} public void setTravelPlan(String v){travelPlan=v;} public String getTravelPlaces(){return travelPlaces;} public void setTravelPlaces(String v){travelPlaces=v;} public LocalDate getTravelDate(){return travelDate;} public void setTravelDate(LocalDate v){travelDate=v;} public LocalTime getTravelTime(){return travelTime;} public void setTravelTime(LocalTime v){travelTime=v;} public String getPickupLocation(){return pickupLocation;} public void setPickupLocation(String v){pickupLocation=v;} public String getAdminNotes(){return adminNotes;} public void setAdminNotes(String v){adminNotes=v;} public BigDecimal getAmountDue(){return amountDue;} public void setAmountDue(BigDecimal v){amountDue=v==null?BigDecimal.ZERO:v;} public String getPaymentReference(){return paymentReference;} public void setPaymentReference(String v){paymentReference=v;} public LocalDateTime getPaymentReceivedAt(){return paymentReceivedAt;} public void setPaymentReceivedAt(LocalDateTime v){paymentReceivedAt=v;} public String getPlanPdfPath(){return planPdfPath;} public void setPlanPdfPath(String v){planPdfPath=v;} public boolean isPlanSent(){return planSent;} public void setPlanSent(boolean v){planSent=v;} public boolean isFinalConfirmationSent(){return finalConfirmationSent;} public void setFinalConfirmationSent(boolean v){finalConfirmationSent=v;}
 public String getDisplayName(){return tourPackage!=null?tourPackage.getTitle():(hotel!=null?hotel.getName():"Travel booking");}
}
