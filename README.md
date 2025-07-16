# Cook App

A modern Android application for managing cooking recipes, built with Kotlin and following MVVM architecture patterns.

## 📱 Features

- **Recipe Management**: Store and organize cooking recipes
- **CSV Import**: Import recipes from CSV files
- **Modern UI**: Material Design with bottom navigation
- **MVVM Architecture**: Clean separation of concerns
- **Reactive Programming**: LiveData for UI updates

## 🏗️ Architecture

The app follows the **MVVM (Model-View-ViewModel)** architecture pattern:

- **Model**: Data classes and business logic
- **View**: Fragments and UI components
- **ViewModel**: State management and data processing

### Key Components

- **MainActivity**: Application entry point with navigation setup
- **RecipeEntry**: Data model for recipe information
- **CsvReader**: Utility for reading recipe data from CSV files
- **Fragments**: UI components for different screens (Home, Dashboard, Notifications)
- **ViewModels**: Business logic and state management

## 📁 Project Structure

```
app/src/main/java/cn/chiichen/cook/
├── MainActivity.kt                    # Main application entry point
├── model/
│   └── RecipeEntry.kt                # Recipe data model
├── service/
│   └── CookDataService.kt            # Business logic service
├── ui/
│   ├── home/                         # Home screen components
│   ├── dashboard/                    # Dashboard screen components
│   └── notifications/                # Notifications screen components
└── utils/
    └── CsvReader.kt                  # CSV file reading utility
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Kotlin 1.8+
- Android SDK 34
- Minimum SDK: 24 (Android 7.0)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd cook-app
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the project directory and select it

3. **Build and Run**
   ```bash
   ./gradlew build
   ./gradlew installDebug
   ```

### Configuration

The app uses the following key dependencies:

```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    
    // Architecture Components
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    
    // Data Processing
    implementation("com.opencsv:opencsv:5.8")
}
```

## 📚 Documentation

### API Documentation
- **[API Documentation](API_DOCUMENTATION.md)**: Comprehensive documentation of all public APIs, functions, and components
- **[Technical Documentation](TECHNICAL_DOCUMENTATION.md)**: Detailed technical implementation guide
- **[Quick Reference](QUICK_REFERENCE.md)**: Quick reference guide for developers

### Key APIs

#### RecipeEntry Data Model
```kotlin
data class RecipeEntry(
    val name: String,        // Recipe name
    val stuff: String,       // Ingredients
    val bv: String,          // Nutritional benefits
    val difficulty: String,  // Difficulty level
    val tags: String,        // Recipe tags
    val methods: String,     // Cooking methods
    val tools: String        // Required tools
)
```

#### CsvReader Utility
```kotlin
class CsvReader {
    fun readData(): ArrayList<RecipeEntry>
}
```

#### ViewModel Pattern
```kotlin
class HomeViewModel : ViewModel() {
    val text: LiveData<String>
    fun updateText(newText: String)
}
```

## 🧪 Testing

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

### Test Structure

- **Unit Tests**: Test ViewModels and business logic
- **UI Tests**: Test Fragment interactions and UI components
- **Integration Tests**: Test data flow between components

## 🔧 Development

### Code Style

The project follows Kotlin coding conventions:

- Use meaningful variable and function names
- Keep functions small and focused
- Add comments for complex logic
- Follow single responsibility principle

### Best Practices

1. **ViewBinding**: Use ViewBinding for type-safe view access
2. **Lifecycle Awareness**: Respect Android lifecycle in ViewModels
3. **Error Handling**: Always handle exceptions gracefully
4. **Testing**: Write tests for all public methods

### Common Patterns

#### Fragment Setup
```kotlin
class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(...): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

#### ViewModel with LiveData
```kotlin
class MyViewModel : ViewModel() {
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data
    
    fun updateData(newData: String) {
        _data.value = newData
    }
}
```

## 🚀 Future Enhancements

### Planned Features

1. **Database Integration**: Add Room database for local storage
2. **Search and Filter**: Implement recipe search and filtering
3. **Recipe Details**: Add detailed recipe view with step-by-step instructions
4. **User Preferences**: Add user settings and preferences
5. **Offline Support**: Implement offline data caching
6. **Image Support**: Add recipe image handling
7. **Sharing**: Add recipe sharing functionality
8. **Categories**: Organize recipes by categories

### Technical Improvements

1. **Dependency Injection**: Implement Hilt for dependency injection
2. **Network Layer**: Add API integration for remote data
3. **Image Loading**: Implement Glide or Coil for image loading
4. **Analytics**: Add usage analytics and crash reporting
5. **Performance**: Optimize app performance and memory usage

## 🤝 Contributing

### Development Workflow

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make your changes**
4. **Write tests** for new functionality
5. **Commit your changes**
   ```bash
   git commit -m "Add feature: description of changes"
   ```
6. **Push to your branch**
   ```bash
   git push origin feature/your-feature-name
   ```
7. **Create a Pull Request**

### Code Review Guidelines

- Ensure all tests pass
- Follow the established code style
- Add appropriate documentation
- Include usage examples for new APIs

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

### Getting Help

- **Documentation**: Check the [API Documentation](API_DOCUMENTATION.md) first
- **Quick Reference**: Use the [Quick Reference Guide](QUICK_REFERENCE.md) for common patterns
- **Issues**: Report bugs and request features through GitHub Issues

### Common Issues

#### Build Issues
```bash
# Clean and rebuild
./gradlew clean
./gradlew build
```

#### Navigation Issues
- Ensure fragment class names match navigation graph
- Check that all navigation destinations are properly configured

#### CSV Reading Issues
- Verify CSV file exists in assets folder
- Check CSV file format matches expected structure

## 📊 Project Status

- ✅ **Core Architecture**: MVVM pattern implemented
- ✅ **Basic UI**: Navigation and fragments working
- ✅ **Data Model**: RecipeEntry data class defined
- ✅ **CSV Reader**: Basic CSV reading utility
- 🔄 **Service Layer**: CookDataService placeholder
- 🔄 **Database**: Room database integration planned
- 🔄 **Testing**: Basic test structure in place

## 🎯 Roadmap

### Version 1.0 (Current)
- [x] Basic app structure
- [x] Navigation setup
- [x] Data model definition
- [x] CSV reading utility

### Version 1.1 (Next)
- [ ] Complete CSV reader implementation
- [ ] Recipe list display
- [ ] Basic search functionality
- [ ] Recipe detail view

### Version 2.0 (Future)
- [ ] Database integration
- [ ] Advanced search and filtering
- [ ] User preferences
- [ ] Recipe sharing

---

**Happy Cooking! 🍳**

For more information, check out the detailed documentation:
- [API Documentation](API_DOCUMENTATION.md)
- [Technical Documentation](TECHNICAL_DOCUMENTATION.md)
- [Quick Reference](QUICK_REFERENCE.md)