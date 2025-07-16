# Cook App API Documentation

## Overview

The Cook App is an Android application built with Kotlin that provides a cooking recipe management system. The app uses the MVVM (Model-View-ViewModel) architecture pattern and follows Android best practices with Navigation Components, ViewBinding, and LiveData.

## Table of Contents

1. [Project Structure](#project-structure)
2. [Main Activity](#main-activity)
3. [Data Models](#data-models)
4. [Services](#services)
5. [UI Components](#ui-components)
6. [Utilities](#utilities)
7. [Dependencies](#dependencies)
8. [Usage Examples](#usage-examples)

## Project Structure

```
cn.chiichen.cook/
├── MainActivity.kt                 # Main application entry point
├── model/
│   └── RecipeEntry.kt             # Data model for recipes
├── service/
│   └── CookDataService.kt         # Service layer for data operations
├── ui/
│   ├── home/                      # Home screen components
│   ├── dashboard/                 # Dashboard screen components
│   └── notifications/             # Notifications screen components
└── utils/
    └── CsvReader.kt               # CSV file reading utility
```

## Main Activity

### MainActivity

**Package:** `cn.chiichen.cook`

**Description:** The main entry point of the application that sets up navigation and the bottom navigation bar.

**Public Methods:**

#### `onCreate(savedInstanceState: Bundle?)`

**Description:** Initializes the main activity, sets up view binding, navigation controller, and bottom navigation.

**Parameters:**
- `savedInstanceState: Bundle?` - The saved instance state bundle

**Usage Example:**
```kotlin
// This method is automatically called by the Android system
// No manual invocation required
```

**Implementation Details:**
- Inflates the main activity layout using ViewBinding
- Sets up the Navigation Controller with the host fragment
- Configures the AppBar with top-level destinations
- Connects the bottom navigation view with the navigation controller

**Navigation Destinations:**
- `R.id.navigation_home` - Home screen
- `R.id.navigation_dashboard` - Dashboard screen  
- `R.id.navigation_notifications` - Notifications screen

## Data Models

### RecipeEntry

**Package:** `cn.chiichen.cook.model`

**Description:** Data class representing a cooking recipe with all its properties.

**Properties:**
- `name: String` - The name of the recipe
- `stuff: String` - Ingredients required for the recipe
- `bv: String` - Nutritional value or benefits
- `difficulty: String` - Difficulty level of the recipe
- `tags: String` - Tags or categories for the recipe
- `methods: String` - Cooking methods and instructions
- `tools: String` - Required cooking tools and equipment

**Usage Example:**
```kotlin
val recipe = RecipeEntry(
    name = "Spaghetti Carbonara",
    stuff = "Pasta, Eggs, Bacon, Parmesan, Black Pepper",
    bv = "High protein, Moderate carbs",
    difficulty = "Medium",
    tags = "Italian, Pasta, Quick",
    methods = "1. Cook pasta\n2. Fry bacon\n3. Mix with eggs",
    tools = "Large pot, Frying pan, Whisk"
)
```

## Services

### CookDataService

**Package:** `cn.chiichen.cook.service`

**Description:** Service class for handling cooking data operations. Currently empty but designed for future data management functionality.

**Public Methods:** None currently implemented

**Usage Example:**
```kotlin
val cookService = CookDataService()
// Future methods will be implemented here for data operations
```

## UI Components

### Home Screen

#### HomeFragment

**Package:** `cn.chiichen.cook.ui.home`

**Description:** Fragment representing the home screen of the application.

**Public Methods:**

##### `onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View`

**Description:** Creates and returns the view hierarchy associated with the fragment.

**Parameters:**
- `inflater: LayoutInflater` - The LayoutInflater object
- `container: ViewGroup?` - The parent view that the fragment's UI should be attached to
- `savedInstanceState: Bundle?` - The saved instance state bundle

**Returns:** `View` - The fragment's view

**Usage Example:**
```kotlin
// This method is automatically called by the FragmentManager
// No manual invocation required
```

##### `onDestroyView()`

**Description:** Called when the view hierarchy associated with the fragment is being removed.

**Usage Example:**
```kotlin
// This method is automatically called by the FragmentManager
// No manual invocation required
```

#### HomeViewModel

**Package:** `cn.chiichen.cook.ui.home`

**Description:** ViewModel for the home screen that manages UI-related data.

**Public Properties:**

##### `text: LiveData<String>`

**Description:** Observable text data for the home screen.

**Usage Example:**
```kotlin
val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
homeViewModel.text.observe(viewLifecycleOwner) { text ->
    // Update UI with the text value
    textView.text = text
}
```

### Dashboard Screen

#### DashboardFragment

**Package:** `cn.chiichen.cook.ui.dashboard`

**Description:** Fragment representing the dashboard screen of the application.

**Public Methods:**

##### `onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View`

**Description:** Creates and returns the view hierarchy associated with the fragment.

**Parameters:**
- `inflater: LayoutInflater` - The LayoutInflater object
- `container: ViewGroup?` - The parent view that the fragment's UI should be attached to
- `savedInstanceState: Bundle?` - The saved instance state bundle

**Returns:** `View` - The fragment's view

**Usage Example:**
```kotlin
// This method is automatically called by the FragmentManager
// No manual invocation required
```

##### `onDestroyView()`

**Description:** Called when the view hierarchy associated with the fragment is being removed.

**Usage Example:**
```kotlin
// This method is automatically called by the FragmentManager
// No manual invocation required
```

#### DashboardViewModel

**Package:** `cn.chiichen.cook.ui.dashboard`

**Description:** ViewModel for the dashboard screen that manages UI-related data.

**Public Properties:**

##### `text: LiveData<String>`

**Description:** Observable text data for the dashboard screen.

**Usage Example:**
```kotlin
val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
dashboardViewModel.text.observe(viewLifecycleOwner) { text ->
    // Update UI with the text value
    textView.text = text
}
```

### Notifications Screen

#### NotificationsFragment

**Package:** `cn.chiichen.cook.ui.notifications`

**Description:** Fragment representing the notifications screen of the application.

**Public Methods:**

##### `onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View`

**Description:** Creates and returns the view hierarchy associated with the fragment.

**Parameters:**
- `inflater: LayoutInflater` - The LayoutInflater object
- `container: ViewGroup?` - The parent view that the fragment's UI should be attached to
- `savedInstanceState: Bundle?` - The saved instance state bundle

**Returns:** `View` - The fragment's view

**Usage Example:**
```kotlin
// This method is automatically called by the FragmentManager
// No manual invocation required
```

##### `onDestroyView()`

**Description:** Called when the view hierarchy associated with the fragment is being removed.

**Usage Example:**
```kotlin
// This method is automatically called by the FragmentManager
// No manual invocation required
```

#### NotificationsViewModel

**Package:** `cn.chiichen.cook.ui.notifications`

**Description:** ViewModel for the notifications screen that manages UI-related data.

**Public Properties:**

##### `text: LiveData<String>`

**Description:** Observable text data for the notifications screen.

**Usage Example:**
```kotlin
val notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
notificationsViewModel.text.observe(viewLifecycleOwner) { text ->
    // Update UI with the text value
    textView.text = text
}
```

## Utilities

### CsvReader

**Package:** `cn.chiichen.cook.utils`

**Description:** Utility class for reading CSV files containing recipe data.

**Constants:**
- `dataPath: String` - Path to the CSV data file (private constant)

**Public Methods:**

#### `readData(): ArrayList<RecipeEntry>`

**Description:** Reads recipe data from the CSV file and returns a list of RecipeEntry objects.

**Returns:** `ArrayList<RecipeEntry>` - List of recipe entries

**Usage Example:**
```kotlin
val csvReader = CsvReader()
val recipes = csvReader.readData()

// Process the recipes
for (recipe in recipes) {
    println("Recipe: ${recipe.name}")
    println("Ingredients: ${recipe.stuff}")
    println("Difficulty: ${recipe.difficulty}")
}
```

**Implementation Notes:**
- Currently returns an empty ArrayList
- Uses OpenCSV library for CSV parsing
- Expected to read from "data.csv" file in assets

## Dependencies

The project uses the following key dependencies:

### Core Android Dependencies
- `androidx.core.ktx` - Kotlin extensions for Android core functionality
- `androidx.appcompat` - AppCompat library for backward compatibility
- `androidx.constraintlayout` - ConstraintLayout for flexible UI layouts

### Architecture Components
- `androidx.lifecycle.livedata.ktx` - LiveData for reactive programming
- `androidx.lifecycle.viewmodel.ktx` - ViewModel for UI state management
- `androidx.navigation.fragment.ktx` - Navigation component for fragment navigation
- `androidx.navigation.ui.ktx` - Navigation UI components

### UI Components
- `material` - Material Design components
- `androidx.junit` - JUnit testing framework
- `androidx.espresso.core` - UI testing framework

### Data Processing
- `opencsv` - CSV file reading and parsing library

## Usage Examples

### Complete Application Setup

```kotlin
// MainActivity automatically handles the setup
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Navigation and UI setup is handled automatically
    }
}
```

### Working with Recipe Data

```kotlin
// Create a recipe entry
val recipe = RecipeEntry(
    name = "Chicken Stir Fry",
    stuff = "Chicken, Vegetables, Soy Sauce, Oil",
    bv = "High protein, Low carb",
    difficulty = "Easy",
    tags = "Asian, Quick, Healthy",
    methods = "1. Cut chicken\n2. Stir fry vegetables\n3. Add sauce",
    tools = "Wok, Spatula, Cutting board"
)

// Read recipes from CSV
val csvReader = CsvReader()
val recipes = csvReader.readData()

// Display recipes in UI
recipes.forEach { recipe ->
    // Update UI components with recipe data
    recipeNameTextView.text = recipe.name
    ingredientsTextView.text = recipe.stuff
    difficultyTextView.text = recipe.difficulty
}
```

### ViewModel Integration

```kotlin
// In a Fragment
class MyFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        
        // Observe data changes
        viewModel.text.observe(viewLifecycleOwner) { text ->
            // Update UI when data changes
            textView.text = text
        }
        
        return binding.root
    }
}
```

### Navigation Usage

```kotlin
// Navigate between screens using the bottom navigation
// This is handled automatically by the Navigation Component
// Users can tap on the bottom navigation items to switch between:
// - Home
// - Dashboard  
// - Notifications
```

## Architecture Patterns

### MVVM (Model-View-ViewModel)
- **Model:** `RecipeEntry` data class and `CsvReader` utility
- **View:** Fragment classes (`HomeFragment`, `DashboardFragment`, `NotificationsFragment`)
- **ViewModel:** ViewModel classes that manage UI state and business logic

### Repository Pattern (Future Implementation)
- `CookDataService` is designed to implement the repository pattern for data operations

### Navigation Component
- Uses Android Navigation Component for fragment navigation
- Bottom navigation with three main destinations
- Automatic handling of navigation state and back stack

## Best Practices

1. **ViewBinding:** All UI components use ViewBinding for type-safe view access
2. **Lifecycle Awareness:** ViewModels and LiveData respect Android lifecycle
3. **Separation of Concerns:** Clear separation between UI, business logic, and data layers
4. **Reactive Programming:** LiveData for reactive UI updates
5. **Memory Management:** Proper cleanup in `onDestroyView()` methods

## Future Enhancements

1. **Complete CSV Reader Implementation:** Implement actual CSV parsing logic
2. **Database Integration:** Add Room database for local storage
3. **Network Layer:** Add API integration for remote recipe data
4. **Search and Filter:** Implement recipe search and filtering functionality
5. **Recipe Details:** Add detailed recipe view with step-by-step instructions
6. **User Preferences:** Add user settings and preferences management
7. **Offline Support:** Implement offline data caching
8. **Image Support:** Add recipe image handling and display

## Testing

The project includes testing dependencies for:
- **Unit Testing:** JUnit framework
- **UI Testing:** Espresso framework
- **Instrumentation Testing:** AndroidJUnitRunner

Example test structure:
```kotlin
@Test
fun testRecipeCreation() {
    val recipe = RecipeEntry(
        name = "Test Recipe",
        stuff = "Test Ingredients",
        bv = "Test Benefits",
        difficulty = "Easy",
        tags = "Test",
        methods = "Test Methods",
        tools = "Test Tools"
    )
    
    assertEquals("Test Recipe", recipe.name)
    assertEquals("Test Ingredients", recipe.stuff)
}
```