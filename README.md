# Tours & Travels — Full-stack Spring Boot project

A responsive travel agency web application using **Java 21, Maven, Spring Boot, Spring MVC, Thymeleaf, Spring Security, Spring Data JPA and MySQL**. The app can also run locally with an H2 file database so you can try it without installing MySQL first.

## Features

- Responsive premium travel UI: homepage, destinations, destination details, packages, package detail/booking form, hotels, activities and contact page.
- Account registration and secure password hashing (BCrypt), login/logout, role-based access control.
- Customer booking requests, booking history and cancellation.
- Admin-prepared travel plans, downloadable PDF itineraries and plan/payment/confirmation status tracking.
- Contact enquiry form stored in SQL database.
- Admin dashboard with summary counts and recent bookings; create/delete destinations, tour packages and hotels.
- Search destinations and tour packages, category filtering, seeded sample data, mobile navigation, responsive cards.
- MySQL profile and Docker Compose option; H2 profile for quick local testing.

## Requirements

- Java 21 (JDK)
- Maven 3.9+
- MySQL 8+ for MySQL mode (optional if using H2)

## Run locally (easiest: H2 database)

From this folder run:

```bash
mvn spring-boot:run
```

Open http://localhost:8080

H2 data is stored under `./data/tours-travels`; sample destinations/packages/hotels and demo users are inserted on first run.

### Demo accounts

- Admin: `admin@tours.com` / `ChangeMe123!`
- Traveler: `traveler@example.com` / `Travel123!`

**Change or remove demo credentials before deploying anywhere public.** The default demo admin password is only for local development.

## Run with MySQL

1. Create a database (or allow the app account to create it):

```sql
CREATE DATABASE tours_travels CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Set environment variables, then run:

```bash
# Linux/macOS
export SPRING_PROFILES_ACTIVE=mysql
export DB_URL='jdbc:mysql://localhost:3306/tours_travels?createDatabaseIfNotExist=true&serverTimezone=UTC'
export DB_USERNAME='your_mysql_user'
export DB_PASSWORD='your_mysql_password'
mvn spring-boot:run
```

Windows PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE="mysql"
$env:DB_URL="jdbc:mysql://localhost:3306/tours_travels?createDatabaseIfNotExist=true&serverTimezone=UTC"
$env:DB_USERNAME="your_mysql_user"
$env:DB_PASSWORD="your_mysql_password"
mvn spring-boot:run
```

The JPA schema is updated automatically at startup (`spring.jpa.hibernate.ddl-auto=update`). For production, use a migration tool such as Flyway and set `ddl-auto=validate`.

## Run using Docker Compose

Docker Compose starts MySQL and the application. Review and change the sample database passwords in `docker-compose.yml` before use.

```bash
docker compose up --build
```

Then visit http://localhost:8080.

## Free public deployment on Render

The repository includes a `render.yaml` Blueprint and a `render` Spring profile. After pushing this project to GitHub:

1. Sign in to [Render](https://render.com), choose **New → Blueprint**, and connect this repository.
2. Set a strong `ADMIN_PASSWORD` when Render asks for the Blueprint secret. The admin email defaults to `tours&travels@gmail.com`; change it by setting `ADMIN_EMAIL` if needed.
3. Deploy the Blueprint. Render assigns a public `onrender.com` URL.

The Render profile disables the H2 console and does not create the local demo accounts. The admin account is created from the Render environment variables; never put its password in GitHub.

Render's free web service sleeps after 15 minutes without traffic and can take about a minute to wake up. Its filesystem is temporary, and this Blueprint uses an in-memory H2 database, so accounts, bookings and generated PDFs are lost when the service restarts, sleeps or redeploys. This free setup is for a public demo, not real customer bookings. For persistent booking data, connect a separately hosted database and configure backups.

Email delivery requires valid SMTP settings (`MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `MAIL_SMTP_AUTH`, and `MAIL_SMTP_STARTTLS`). The booking flow does not charge cards; configure a payment gateway before accepting real payments.

## Main routes

| Route | Purpose | Access |
|---|---|---|
| `/` | Home | Public |
| `/destinations` | Search/list destinations | Public |
| `/packages` | Search/filter tour packages | Public |
| `/packages/{id}` | Package detail and booking request | Public; booking requires login |
| `/hotels` | Hotel listings | Public |
| `/activities` | Activity categories | Public |
| `/contact` | Submit contact enquiry | Public |
| `/register`, `/login` | Account creation and login | Public |
| `/account` | Customer booking dashboard | Signed-in users |
| `/admin` | Admin dashboard | ADMIN role |
| `/admin/destinations` | Manage destinations | ADMIN role |
| `/admin/packages` | Manage tour packages | ADMIN role |
| `/admin/hotels` | Manage hotels | ADMIN role |
| `/h2-console` | H2 database console (H2 profile only) | Local development only |

## Database entities

`AppUser`, `Destination`, `TourPackage`, `Hotel`, `Booking`, `Review`, `ContactMessage`.

## Image and business configuration notes

- Demo travel photos are loaded from Unsplash URLs, so an internet connection is needed to display them. Replace URLs with licensed photos or your own uploaded images for production.
- Contact email, phone and office details should be replaced with your real business details before publishing.
- Package prices and ratings are sample/demo data; confirm actual rates, availability, cancellation terms and payment handling before taking real bookings.
- Payment is recorded as a user-submitted payment reference; there is no live payment gateway. Email sending requires valid SMTP settings. Maps APIs and third-party hotel inventory are not integrated.
- For production, configure HTTPS, CSRF-safe custom workflows, rate limiting, email verification, password reset, database migrations, backups, secrets management and privacy/terms pages.

## Project structure

```text
src/main/java/com/tourstravels/
  config/       Security and sample data setup
  controller/   MVC routes and booking/admin actions
  model/        JPA entities
  repository/   Spring Data repositories
  service/      Registration and user lookup
src/main/resources/
  templates/    Thymeleaf pages and admin screens
  static/css/   Responsive design system
  static/js/    Small frontend interactions
  application*.properties
```
