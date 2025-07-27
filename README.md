# Network Layer Kit

An easy‑to‑plug, Hilt‑powered network layer for Android apps built on Retrofit.  
Provides:

- **Base Repository** with `safeApiCall` wrapper for unified error handling  
- **Exception Mapping** for timeouts, network failures, HTTP errors, empty responses, unauthorized, etc.  
- **DI Modules** for Retrofit, repository implementations, interceptors  
- **TokenInterceptor** that skips auth on login/register endpoints and injects your Bearer token  
- **UnauthorizedInterceptor** that emits a global “unauthorized” event on 401 responses  
- **Sample ViewModel** showing how to observe auth state and handle session expiry  

---

## 📦 Features

- 🚀 **Simple API Calls**  
  ```kotlin
  class SampleRepo @Inject constructor(
      private val service: SampleService,
      private val baseRepository: BaseApiRepository
  ) : BaseApiRepository by baseRepository {
      suspend fun getUser(): User = safeApiCall {
          service.getUser()
      }
  }
  ```
- 🔄 **Centralized Error Handling**  
  Catches `SocketTimeoutException`, `IOException`, `HttpException`, and your custom API errors—maps them to:
  - `TimeoutException`
  - `NetworkException`
  - `ServerException`
  - `ApiException`
  - `EmptyResponseException`
  - `UnauthorizedException`
- 🧩 **Hilt Modules** for:
  - `Retrofit` & `OkHttpClient`
  - `BaseApiRepositoryImpl`
  - `TokenInterceptor`
  - `UnauthorizedInterceptor`
  - `SampleRepo` (and any of your domain repositories)
- 🔑 **Auth Flow**  
  - `AuthInterceptor` skips auth on specified endpoints, automatically attaches `Authorization: Bearer <token>`  
  - `UnauthorizedInterceptor` emits a global event on `HTTP 401` so you can navigate to login  

---

## 🛠️ Customization

- **Skip Auth Paths**  
  Update `AuthInterceptor.skipAuthPaths` set to add or remove endpoints.
- **Error Strings**  
  Strings live in `res/values/strings.xml` (e.g. `error_timeout`, `error_network`, etc.).
- **Global Unauthorized Handling**  
  Listen to `AuthEvents.unauthorizedFlow` to react to 401s anywhere in your app.

---

## 🧱 Architecture

```
[ Retrofit Service ] → [ Repository (safeApiCall) ] → [ ViewModel ] → [ UI ]
            ↓                           ↑
  [ AuthInterceptor ]      [ BaseApiRepositoryImpl ]
            ↓                           ↑
  [ UnauthorizedInterceptor ]
```

- **Services** define your Retrofit endpoints.
- **BaseApiRepositoryImpl** wraps every call in `safeApiCall`, processes `BaseResponse<T>`, and maps errors.
- **AuthInterceptor** adds your JWT token to each request, except login/register.
- **UnauthorizedInterceptor** publishes a 401 event so your UI can navigate to login.

---

## 📚 Resources

- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Hilt Documentation](https://dagger.dev/hilt/)
- [OkHttp Interceptors](https://square.github.io/okhttp/interceptors/)

---

## Contributing

1. Fork the repository  
2. Create a feature branch (`git checkout -b feature/my-feature`)  
3. Commit your changes (`git commit -m 'Add my-feature'`)  
4. Push to branch (`git push origin feature-my-feature`)  
5. Open a Pull Request

---

*Built with :coffee: and :heart: by Islam*  
