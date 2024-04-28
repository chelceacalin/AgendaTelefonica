# Title: Correspondence Agenda

## 1. Introduction

Correspondence Agenda is a web application designed to streamline email management for users. With features to create, update, and delete contacts, it aims to simplify the process of organizing and communicating via email. By integrating an alternative SMTP password, users can send emails directly from the application, eliminating the need for separate email interfaces like Gmail.

## 2. Architecture

The Correspondence Agenda application is built using the following technologies:
- Spring Boot
- Spring MVC
- Mustache
- Docker
- OAuth2
- Gmail SMTP

## 3. Problem Description

The Correspondence Agenda addresses the challenge of managing emails
efficiently by offering a centralized platform for email organization and communication.
By integrating with other applications and providing direct email sending capabilities, 
it reduces dependency on external email clients like Gmail.

## 4. API Description

### Authentication Controller:
- GET "/", "/login" -> Redirects users to log in with Google or Github
- GET "/" -> Redirects logged-in users to the main page displaying contacts
- GET "/logout" -> Invalidates user session and redirects to login
- GET "*" -> Shows error page for invalid URLs

### Contact Controller:
- POST "/contact/add" -> Creates a new contact
- POST "/contact/update" -> Updates an existing contact
- POST "/contact/delete/{email}" -> Deletes an existing contact
- POST "/contact/sendMail" -> Sends email to existing contact

### User Controller:
- POST "/user/updateProfile/{email}" -> Updates user data (email being unique)

## 5. Data Flow

- User attempts to access the app and is redirected to the login page.
- User authenticates using OAuth2 with Google or Github.
- Upon successful authentication, user is redirected to the main page showing contacts.
- User can add new contacts and configure SMTP password in the profile settings.
- User can send emails to contacts directly from the interface.

## 6. Screenshots App

<img src="https://github.com/chelceacalin/CorrespondenceAgenda/assets/76866499/2a570d13-4d64-434b-9850-a9dc064b09d9" width="400" height="250">
<img src="https://github.com/chelceacalin/CorrespondenceAgenda/assets/76866499/7f7bd9ed-37eb-4e70-b4d3-a9ad4d03c1bb" width="400" height="250">
<img src="https://github.com/chelceacalin/CorrespondenceAgenda/assets/76866499/db24f450-7026-4997-afcf-0b0125e5f914" width="400" height="250">
<img src="https://github.com/chelceacalin/CorrespondenceAgenda/assets/76866499/58ef75a4-5a0a-4858-89c9-8881ed63dbf7" width="400" height="250">
<img src="https://github.com/chelceacalin/CorrespondenceAgenda/assets/76866499/c38b5b2e-140a-4e39-a552-103cddf5b904" width="400" height="250">
<img src="https://github.com/chelceacalin/CorrespondenceAgenda/assets/76866499/d6db7256-4df4-4223-892d-12639583e0c9" width="400" height="250">


## 7. References
* https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html
* https://www.javatpoint.com/spring-mvc-tutorial
* https://mustache.github.io/
* https://oauth.net/2/
