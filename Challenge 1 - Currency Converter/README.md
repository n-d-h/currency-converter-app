# Currency Converter App

This is a simple currency converter app for Android that allows users to select two currencies and input an amount for conversion. The app fetches real-time exchange rates from an API and provides the converted amount in the chosen target currency.

## Features
- Dropdown selection of `Convert From` and `Convert To` currencies
- Fetching APIs for currency exchange rates
- Loading indicators and error handling for network issues
- UI feedback for validation (e.g., highlights fields if required selections are missing)

## Setup Instructions

### Step 1: Clone the Repository
```bash
git clone https://github.com/n-d-h/currency-converter-app.git
```

### Step 2: Open in Android Studio
1. Launch Android Studio.
2. Select "Open an Existing Project" and open the cloned project directory.

### Step 3: Sync Gradle
- Android Studio should automatically download dependencies. If not, go to `File > Sync Project with Gradle Files`.

### Step 4: API Key Setup
In `CurrencyService.java`, replace the `API_KEY` value with your own key if needed to access the API:
```java
private final static String API_KEY = "your-api-key";
```

### Step 5: Running the App
1. Set up an emulator or connect an Android device via USB.
2. Click the "Play" button in Android Studio or go to `Run > Run 'app'`.

## Using the App
1. Choose the `From` and `To` currencies from the dropdowns.
2. Enter an amount to convert.
3. Press the **Convert** button to see the result displayed below.

## Code Structure

### MainActivity
- **Dropdown Listeners**: Opens dialogs for selecting currencies.
- **Validation Checks**: Ensures both `convertFrom` and `convertTo` fields are selected; displays validation messages if fields are left empty.
- **Conversion Button**: Initiates the conversion process by calling `CurrencyService.getConvertResult`.

### CurrencyService
- **fetchCurrencies**: Fetches available currencies for dropdown menus.
- **getConvertResult**: Calculates the converted amount using the fetched exchange rate.
- **showErrorDialog**: Displays an error dialog in case of network or API issues.

## Challenges & Notes
- **API Access**: Ensure the API key is valid, or replace it if needed.
- **Network Stability**: The app relies on internet access; network errors prompt dialogs to notify users.
- **UI Management**: Uses `CardView` and `FrameLayout` for a clean and responsive UI.
- **Null Safety**: Implemented null safety to prevent unexpected crashes.

## Download Debug APK
[Download Debug APK here](./app-debug.apk)

## Demo Video/GIF
Watch a demo of the app in action: 
![Demo GIF](./demo-gif.gif)
