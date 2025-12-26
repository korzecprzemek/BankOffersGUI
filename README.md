# BankOffersGUI

**BankOffersGUI** is a Java desktop application built using the **MVC (Model–View–Controller)** design pattern.  
The project uses **Swing** as the graphical user interface framework and allows the user to select the most effective bank loan offer based on defined scoring rules.

## 🏗 Architecture

The application strictly follows the MVC pattern:

- **Model** – business logic, loan offers, scoring strategies, domain exceptions  
  `src/main/java/pl/polsl/model`

- **View** – Swing GUI components and table models  
  `src/main/java/pl/polsl/view`

- **Controller** – connects the GUI with the business logic  
  `src/main/java/pl/polsl/controller`

## 🎯 Features

- Load and manage bank loan offers from CSV file  
- Evaluate offers using configurable scoring strategies  
- Display offers in a Swing-based GUI  
- Automatically select the most effective loan for the customer profile  
- Fully unit-tested model layer  

## 📄 Documentation

The full Javadoc documentation is available online at:

👉 https://korzecprzemek.github.io/BankOffersGUI/

## 🧪 Tests

Unit tests are located in:

