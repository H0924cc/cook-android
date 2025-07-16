# Cook App Quick Reference Guide

## Quick Navigation

- [Common Patterns](#common-patterns)
- [Code Snippets](#code-snippets)
- [File Structure](#file-structure)
- [Dependencies](#dependencies)
- [Testing](#testing)
- [Troubleshooting](#troubleshooting)

## Common Patterns

### 1. Fragment Setup Pattern
```kotlin
class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: MyViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        
        setupObservers()
        setupClickListeners()
        
        return binding.root
    }
    
    private fun setupObservers() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            // Update UI
        }
    }
    
    private fun setupClickListeners() {
        binding.button.setOnClickListener {
            // Handle click
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

### 2. ViewModel Pattern
```kotlin
class MyViewModel : ViewModel() {
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    fun loadData() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.getData()
                _data.value = result
            } catch (e: Exception) {
                // Handle error
            } finally {
                _loading.value = false
            }
        }
    }
}
```

### 3. Data Class Pattern
```kotlin
data class MyData(
    val id: Long,
    val name: String,
    val description: String
) {
    // Custom methods if needed
    fun isValid(): Boolean = name.isNotBlank() && description.isNotBlank()
}
```

## Code Snippets

### 1. Recipe Creation
```kotlin
val recipe = RecipeEntry(
    name = "Recipe Name",
    stuff = "Ingredient 1, Ingredient 2, Ingredient 3",
    bv = "Nutritional benefits",
    difficulty = "Easy|Medium|Hard",
    tags = "Tag1, Tag2, Tag3",
    methods = "Step 1\nStep 2\nStep 3",
    tools = "Tool1, Tool2, Tool3"
)
```

### 2. CSV Reading
```kotlin
val csvReader = CsvReader()
val recipes = csvReader.readData()

recipes.forEach { recipe ->
    println("Recipe: ${recipe.name}")
    println("Ingredients: ${recipe.stuff}")
    println("Difficulty: ${recipe.difficulty}")
}
```

### 3. LiveData Observation
```kotlin
viewModel.data.observe(viewLifecycleOwner) { data ->
    // Update UI with data
    binding.textView.text = data
}

viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
    binding.progressBar.isVisible = isLoading
}
```

### 4. Navigation
```kotlin
// Navigate to fragment
findNavController().navigate(R.id.navigation_home)

// Navigate with arguments
val bundle = Bundle().apply {
    putString("key", "value")
}
findNavController().navigate(R.id.navigation_home, bundle)
```

### 5. Error Handling
```kotlin
try {
    val result = riskyOperation()
    // Handle success
} catch (e: IOException) {
    // Handle network error
} catch (e: SecurityException) {
    // Handle permission error
} catch (e: Exception) {
    // Handle general error
}
```

### 6. Coroutines
```kotlin
// In ViewModel
fun loadData() {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = repository.getData()
            withContext(Dispatchers.Main) {
                _data.value = data
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _error.value = e.message
            }
        }
    }
}

// In Fragment/Activity
lifecycleScope.launch {
    // Coroutine code
}
```

## File Structure

```
app/src/main/java/cn/chiichen/cook/
├── MainActivity.kt                    # Main entry point
├── model/
│   └── RecipeEntry.kt                # Data model
├── service/
│   └── CookDataService.kt            # Business logic
├── ui/
│   ├── home/
│   │   ├── HomeFragment.kt           # Home screen
│   │   └── HomeViewModel.kt          # Home logic
│   ├── dashboard/
│   │   ├── DashboardFragment.kt      # Dashboard screen
│   │   └── DashboardViewModel.kt     # Dashboard logic
│   └── notifications/
│       ├── NotificationsFragment.kt  # Notifications screen
│       └── NotificationsViewModel.kt # Notifications logic
└── utils/
    └── CsvReader.kt                  # CSV utility
```

## Dependencies

### Core Dependencies
```kotlin
// Add to app/build.gradle.kts
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

### Testing Dependencies
```kotlin
dependencies {
    // Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    
    // UI Testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

## Testing

### Unit Test Template
```kotlin
@RunWith(MockitoJUnitRunner::class)
class MyViewModelTest {
    
    @Mock
    private lateinit var repository: MyRepository
    
    private lateinit var viewModel: MyViewModel
    
    @Before
    fun setup() {
        viewModel = MyViewModel(repository)
    }
    
    @Test
    fun `test method should do something`() = runTest {
        // Given
        val expected = "expected result"
        whenever(repository.getData()).thenReturn(expected)
        
        // When
        viewModel.loadData()
        
        // Then
        assertEquals(expected, viewModel.data.value)
    }
}
```

### UI Test Template
```kotlin
@RunWith(AndroidJUnit4::class)
class MyFragmentTest {
    
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun testFragmentDisplay() {
        // Given
        onView(withId(R.id.navigation_home)).perform(click())
        
        // Then
        onView(withId(R.id.text_home))
            .check(matches(isDisplayed()))
    }
}
```

## Troubleshooting

### Common Issues

#### 1. ViewBinding Null Pointer
```kotlin
// Problem: _binding is null
// Solution: Always check binding before use
private val binding get() = _binding ?: throw IllegalStateException("Binding is null")
```

#### 2. LiveData Not Updating
```kotlin
// Problem: LiveData observer not triggered
// Solution: Ensure observer is set up correctly
viewModel.data.observe(viewLifecycleOwner) { data ->
    // This will be called when data changes
}
```

#### 3. Navigation Issues
```kotlin
// Problem: Navigation not working
// Solution: Check navigation graph and fragment names
// Ensure fragment class name matches navigation graph
```

#### 4. CSV Reading Errors
```kotlin
// Problem: CSV file not found
// Solution: Check file path and ensure file exists in assets
private const val DATA_PATH = "data.csv" // Should be in app/src/main/assets/
```

### Debug Tips

#### 1. Logging
```kotlin
private val TAG = "MyClass"
Log.d(TAG, "Debug message: $variable")
Log.e(TAG, "Error message", exception)
```

#### 2. Breakpoints
- Set breakpoints in ViewModel methods
- Use conditional breakpoints for specific data values
- Use logpoints for non-intrusive debugging

#### 3. Layout Inspector
- Use Layout Inspector to debug UI issues
- Check view hierarchy and properties
- Verify view IDs and binding

## Performance Tips

### 1. ViewBinding
```kotlin
// Use lazy initialization
private val _binding by lazy { FragmentMyBinding.inflate(layoutInflater) }
```

### 2. LiveData
```kotlin
// Use distinctUntilChanged for performance
val data: LiveData<String> = _data.distinctUntilChanged()
```

### 3. Coroutines
```kotlin
// Use appropriate dispatchers
viewModelScope.launch(Dispatchers.IO) {
    // Heavy operations
    withContext(Dispatchers.Main) {
        // UI updates
    }
}
```

## Best Practices

### 1. Naming Conventions
- Classes: PascalCase (e.g., `HomeFragment`)
- Functions: camelCase (e.g., `loadData()`)
- Variables: camelCase (e.g., `recipeList`)
- Constants: UPPER_SNAKE_CASE (e.g., `DATA_PATH`)

### 2. Code Organization
- Keep functions small and focused
- Use meaningful variable names
- Add comments for complex logic
- Follow single responsibility principle

### 3. Error Handling
- Always handle exceptions
- Provide meaningful error messages
- Log errors for debugging
- Show user-friendly error messages

### 4. Testing
- Write tests for all public methods
- Test both success and failure scenarios
- Use descriptive test names
- Keep tests independent and isolated

## Quick Commands

### Gradle Commands
```bash
# Build project
./gradlew build

# Run tests
./gradlew test

# Install on device
./gradlew installDebug

# Clean project
./gradlew clean
```

### Git Commands
```bash
# Check status
git status

# Add changes
git add .

# Commit changes
git commit -m "Description of changes"

# Push changes
git push origin main
```

This quick reference guide provides essential information for developers working on the Cook App. Use it as a quick lookup for common patterns, code snippets, and troubleshooting tips.