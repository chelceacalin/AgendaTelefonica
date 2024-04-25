# Correspondence Agenda

## Video Presentation

Link: [TODO](link_video)

## Hosted Application

Link: [TODO](link_hosted_app)

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

[TODO: Add screenshots of the application]

## 7. References

[TODO: Add references]
