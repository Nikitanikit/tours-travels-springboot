package com.tourstravels.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import java.time.LocalDateTime;
@Entity public class ContactMessage {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @NotBlank private String fullName; @Email @NotBlank private String email; private String phone; private String destination;
 @NotBlank @Column(length=2000) private String message; private LocalDateTime createdAt=LocalDateTime.now(); private boolean handled=false;
 public Long getId(){return id;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;} public String getDestination(){return destination;} public void setDestination(String v){destination=v;} public String getMessage(){return message;} public void setMessage(String v){message=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public boolean isHandled(){return handled;} public void setHandled(boolean v){handled=v;}
}
