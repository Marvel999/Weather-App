<!--- Heading--->
# Weather App
<!---About-->
This is a simple weather App. Which displays your current Temperature and Location. This app is compatible in Android SDK-21.
<!---screenshot--->
<p align="center">
     <img src="https://user-images.githubusercontent.com/43094705/126774546-679446aa-6745-4db3-afd8-2e91daa25d0f.jpeg" width="200"  title="no internet">
<img src="https://user-images.githubusercontent.com/43094705/126773896-5fcf48ae-b7ee-4b16-93c2-9d1a5cef45b3.jpeg" width="200"  title="no internet">
  <img src="https://user-images.githubusercontent.com/43094705/126774044-20eb0c4b-5fd9-42ca-851c-1de42d56a0b0.jpeg" width="200"  title="no internet">

</p>



## Demo Link
[Build APK](https://drive.google.com/file/d/1ikpo4rykblbF4qMfcwa9ZJ-4JdF66OXL/view?usp=sharing)
[Demo Video](https://drive.google.com/file/d/1ij5Uvbmcnez9318Oq4Jh1UEW8M35gMSG/view?usp=sharing)
<!---Techstack--->
## Techstack
1. kotlin
2. Android
3. MVVM
4. LiveData
5. Retrofit
6. Google play services
7. Moshi
8. Coroutines
9. WeatherStack (Weather API)

<!---Project Setup--->
## Project Setup

#### Project Requirements
1. Android Studio 4.2 Beta 6
2. Kotlin 1.4.30

<!---Feature--->
## Feature
1. Featch temperature of your current location using apixu weather API(Now change into weatherstack) [Know More](https://apidashboard.io/companies/apixu-weather)
2. For location I am using fused location manager.

<!-- Assumptions-->
## Assumptions
1. I am assuming the app will use in portrait mode only.
2. I am assuming that user want to seen only his current location(state)
3. I am showing error message in Toast so It is easy for code review.
4. I am assuming that user strickly have to give there location permission.

<!-- Testcase--->

## Testcase Cover
- [x] Check internet
- [x] Server Error
- [x] Location permission deny
- [x] Proper Location fetching.
- [x] Enable GPS

## TestCase Not cove due to time constrain.
1. Aeroplan mode location fetching
2. Unit tests for codes
3. API_KEY Security

## What is more to improve in codebase.
1. Some location calls are depricated and I am using that beacuse of time constrain.
2. Beacuse in this project have only one Fragment so we added fragment statically but can use dynamic fragment transaction(could be Navigation component).  

## Bug In API
   Some time API give wrong temperature [Know More](https://user-images.githubusercontent.com/43094705/126774968-c932b4c4-7227-440d-b1e4-d86850a0f951.png)
   


Thank you, Happy Coding...:)
