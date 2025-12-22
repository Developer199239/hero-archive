# 🚀 CosmicsLibrary

**CosmicsLibrary** is a modern, feature-rich Android application designed for comic book enthusiasts to explore the vast universe of characters. Built with **Jetpack Compose** and the **Comicvine API**, it allows users to search for their favorite heroes and villains, manage a personal collection, and keep detailed notes—all within a sleek, responsive interface.

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack-Compose-green.svg)](https://developer.android.com/jetpack/compose)
[![Hilt](https://img.shields.io/badge/Dependency%20Injection-Hilt-orange.svg)](https://dagger.dev/hilt/)
[![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](https://www.android.com)

---

## ✨ Features

- 🔍 **Real-time Character Search**: Search through thousands of characters using the Comicvine API with optimized debounce searching.
- 📚 **Personal Collection**: Save your favorite characters to a local Room database for offline access.
- 📝 **Character Notes**: Add, edit, and delete personal notes for every character in your collection.
- 🌐 **Network Monitoring**: Integrated connectivity observer that alerts users when they are offline.
- 🎨 **Modern UI/UX**: Fully built with Jetpack Compose following Material 3 design guidelines.
- 🖼️ **Image Caching**: High-performance image loading and caching using Coil.

---

## 🛠 Tech Stack & Architecture

This project follows **Clean Architecture** principles and the **MVVM (Model-View-ViewModel)** pattern.

- **Language**: [Kotlin](https://kotlinlang.org/) (2.0.21)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room) (with KSP)
- **Networking**: [Retrofit 3](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
- **Async Programming**: Kotlin Coroutines & Flow
- **JSON Parsing**: Gson
- **Navigation**: Jetpack Compose Navigation

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or newer.
- JDK 17+.
- A **Comicvine API Key**. You can get one for free at [Comicvine API](https://comicvine.gamespot.com/api/).

### Installation & Setup

1. **Clone the repository**:
   ```sh
   git clone https://github.com/your-username/cosmicsLibrary.git
   ```

2. **API Key Configuration**:
   Create a file named `apikey.properties` in the root directory of the project and add your key:
   ```properties
   COMICVINE_API_KEY=your_api_key_here
   ```

3. **Build the project**:
   Sync project with Gradle files and run the `:app` module.

---

## 📂 Project Structure

```
com.example.cosmicslibrary
├── api              # Retrofit interfaces, Repositories, Network state helpers
├── model            # Data classes (API & DB entities)
│   └── db           # Room Database, DAOs, and Entity mapping
├── view             # Compose Screens, Navigation, and UI Components
├── viewmodel        # Business logic and UI state management
└── ui.theme         # Material 3 Theme definitions
```

---

## 🛡️ License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

Contributions are welcome! If you have a feature request or found a bug, please open an issue or submit a pull request.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📮 Contact

Developed by **Your Name** - [Your Portfolio/LinkedIn]  
Project Link: [https://github.com/your-username/cosmicsLibrary](https://github.com/your-username/cosmicsLibrary)

***