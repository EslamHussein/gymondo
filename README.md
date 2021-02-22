# Gymondo

App that connects to GitHub APIs to display a list of repositories and repository details.
------------------------------------------------
Architecture (MVVM - Model View ViewModel)
------------------------------------------------

--------------------------
Third party libraries
--------------------------

1. **Android Jetpack & suuport**: android support and jetpack libraries like Navigation 
2. **Retrofit**: used for making HTTP requests and parsing results in an interface looking way
3. **OkHttp**: used for making the actual HTTP requests by Retrofit
4. **Gson**: used for parsing network calls responses by Retrofit and is used through Retrofit's ConverterFactory
5. **coroutines flow**: adding a reactive flavor for the glue between our layers, all communication through Repository
6. **Coil**: used for image loading
