# E-Commerce Android App

Welcome to the repository for our E-Commerce Android App, a fully functional shopping application built using modern technologies and best practices. This app offers a seamless shopping experience with secure user authentication, dynamic product listings, and integrated payment solutions.

## Screenshots
![mockup](https://github.com/khalilkml/PayWay/assets/98777119/61b6b757-f61f-4b3c-98cf-a33e2da79916)


## Features

- **User Authentication:**
    - OTP (One-Time Password) verification for secure login and registration.
    - Firebase Authentication for managing user sessions.
- **Product Catalog:**
    - Display of various product categories with detailed descriptions, images, and prices.
    - Dynamic product listing fetched from Firebase Firestore.
- **Product Details:**
    - Comprehensive product pages with images stored in Firebase Cloud Storage.
    - Detailed descriptions, material information, and price comparisons.
- **Shopping Cart:**
    - Easy-to-use cart management, allowing users to add, remove, and update items.
    - Real-time price updates and discount applications.
- **Payment Integration:**
    - Secure checkout process with PayPal API integration.
- **User Profile:**
    - Personalized user profiles with order history and saved preferences.

## Technologies Used

- **Firebase Firestore:** Real-time database for storing and syncing user and product data.
- **Firebase Cloud Storage:** Secure storage for product images and other media.
- **Firebase Authentication:** Secure user authentication using OTP.
- **PayPal API:** Integrated payment gateway for secure transactions.
- **Java:** Programming language used for Android development.
- **XML:** For designing user interfaces.

## Installation

To run this project locally, follow these steps:

1. **Clone the repository:**
    
    ```bash
    bashCopier le code
    git clone https://github.com/khalilkml/PayWay.git
    
    ```
    
2. **Open the project in Android Studio:**
    - Ensure you have the latest version of Android Studio installed.
3. **Set up Firebase:**
    - Create a Firebase project at Firebase Console.
    - Add your Android app to the Firebase project.
    - Download the `google-services.json` file and place it in the `app` directory.
4. **Configure PayPal:**
    - Set up a PayPal developer account at PayPal Developer.
    - Obtain the client ID and secret.
    - Update the PayPal configuration in the project.
5. **Build and run the app:**
    - Sync the project with Gradle files.
    - Run the app on an emulator or physical device.

## Usage

- **Home Screen:** Browse through top categories and featured products.
- **Product Details:** View detailed information and images of selected products.
- **Shopping Cart:** Manage your selected items and proceed to checkout.
- **User Profile:** Manage your account details and view favorit products.

## Contact

For any inquiries or feedback, feel free to reach out at khalilkml123@gmail.com.
