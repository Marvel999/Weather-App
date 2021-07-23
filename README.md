<!--- Heading--->
# Weather App
<!---About-->
This is a simple weather App. Which displays your current Temperature and Location. This app is compatible in Android SDK-21.
<!---screenshot--->

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


Thank you, Happy Coding...:)
