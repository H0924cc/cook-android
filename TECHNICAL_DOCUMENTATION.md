# Cook App Technical Documentation

## Development Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Kotlin 1.8+
- Android SDK 34
- Minimum SDK: 24 (Android 7.0)

### Project Configuration

#### Gradle Configuration
```kotlin
// app/build.gradle.kts
android {
    namespace = "cn.chiichen.cook"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "cn.chiichen.cook"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    
    buildFeatures {
        viewBinding = true
    }
}
```

#### Key Dependencies
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

## Architecture Overview

### MVVM Pattern Implementation

The application follows the MVVM (Model-View-ViewModel) architecture pattern:

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│       View      │    │    ViewModel     │    │      Model      │
│   (Fragment)    │◄──►│   (ViewModel)    │◄──►│  (Data Class)   │
│                 │    │                  │    │                 │
│ - UI Components │    │ - Business Logic │    │ - RecipeEntry   │
│ - User Input    │    │ - State Mgmt     │    │ - CsvReader     │
│ - Data Display  │    │ - LiveData       │    │ - CookDataService│
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

### Data Flow

1. **User Interaction** → Fragment
2. **Fragment** → ViewModel (calls methods)
3. **ViewModel** → Model (processes data)
4. **Model** → ViewModel (returns results)
5. **ViewModel** → Fragment (updates LiveData)
6. **Fragment** → UI (observes LiveData changes)

## Core Components

### 1. Data Model Layer

#### RecipeEntry Data Class
```kotlin
data class RecipeEntry(
    val name: String,
    val stuff: String,
    val bv: String,
    val difficulty: String,
    val tags: String,
    val methods: String,
    val tools: String
)
```

**Design Patterns:**
- **Immutable Data Class:** All properties are read-only
- **Value Object Pattern:** Represents a single recipe entity
- **Builder Pattern Ready:** Kotlin data classes support copy() for modifications

**Usage Examples:**
```kotlin
// Creating a recipe
val recipe = RecipeEntry(
    name = "Pasta Carbonara",
    stuff = "Spaghetti, Eggs, Bacon, Parmesan",
    bv = "High protein",
    difficulty = "Medium",
    tags = "Italian, Pasta",
    methods = "1. Boil pasta\n2. Cook bacon\n3. Mix with eggs",
    tools = "Pot, Pan, Whisk"
)

// Copying with modifications
val modifiedRecipe = recipe.copy(
    difficulty = "Easy",
    tags = "Italian, Pasta, Quick"
)

// Destructuring
val (name, ingredients, benefits, difficulty, tags, methods, tools) = recipe
```

### 2. Service Layer

#### CookDataService
```kotlin
class CookDataService {
    // Future implementation for data operations
}
```

**Planned Implementation:**
```kotlin
class CookDataService @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val csvReader: CsvReader
) {
    suspend fun getAllRecipes(): List<RecipeEntry> {
        return recipeRepository.getAllRecipes()
    }
    
    suspend fun getRecipesByDifficulty(difficulty: String): List<RecipeEntry> {
        return recipeRepository.getRecipesByDifficulty(difficulty)
    }
    
    suspend fun searchRecipes(query: String): List<RecipeEntry> {
        return recipeRepository.searchRecipes(query)
    }
    
    suspend fun loadRecipesFromCsv(): List<RecipeEntry> {
        return csvReader.readData()
    }
}
```

### 3. Utility Layer

#### CsvReader Implementation
```kotlin
class CsvReader {
    companion object {
        private const val DATA_PATH = "data.csv"
    }
    
    fun readData(): ArrayList<RecipeEntry> {
        return try {
            val fileReader = FileReader(DATA_PATH)
            val csvReader = CSVReader(fileReader)
            val recipes = ArrayList<RecipeEntry>()
            
            // Skip header row
            csvReader.readNext()
            
            var line: Array<String>?
            while (csvReader.readNext().also { line = it } != null) {
                line?.let { data ->
                    if (data.size >= 7) {
                        val recipe = RecipeEntry(
                            name = data[0],
                            stuff = data[1],
                            bv = data[2],
                            difficulty = data[3],
                            tags = data[4],
                            methods = data[5],
                            tools = data[6]
                        )
                        recipes.add(recipe)
                    }
                }
            }
            
            csvReader.close()
            fileReader.close()
            recipes
        } catch (e: Exception) {
            e.printStackTrace()
            ArrayList()
        }
    }
}
```

**Error Handling:**
```kotlin
sealed class CsvReadResult {
    data class Success(val recipes: List<RecipeEntry>) : CsvReadResult()
    data class Error(val message: String, val exception: Exception?) : CsvReadResult()
}

fun readDataWithResult(): CsvReadResult {
    return try {
        val recipes = readData()
        CsvReadResult.Success(recipes)
    } catch (e: Exception) {
        CsvReadResult.Error("Failed to read CSV file", e)
    }
}
```

## UI Components

### 1. Fragment Implementation Pattern

#### Base Fragment Structure
```kotlin
abstract class BaseFragment : Fragment() {
    protected var _binding: ViewBinding? = null
    protected val binding get() = _binding!!
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    protected fun observeViewModel() {
        // Common ViewModel observation logic
    }
}
```

#### HomeFragment Implementation
```kotlin
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var homeViewModel: HomeViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        
        setupObservers()
        setupClickListeners()
        
        return binding.root
    }
    
    private fun setupObservers() {
        homeViewModel.text.observe(viewLifecycleOwner) { text ->
            binding.textHome.text = text
        }
        
        homeViewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            updateRecipeList(recipes)
        }
    }
    
    private fun setupClickListeners() {
        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            homeViewModel.searchRecipes(query)
        }
    }
    
    private fun updateRecipeList(recipes: List<RecipeEntry>) {
        // Update RecyclerView or other UI components
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

### 2. ViewModel Implementation Pattern

#### Base ViewModel
```kotlin
abstract class BaseViewModel : ViewModel() {
    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    protected val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    protected fun handleError(throwable: Throwable) {
        _error.value = throwable.message
        _loading.value = false
    }
}
```

#### HomeViewModel Implementation
```kotlin
class HomeViewModel @Inject constructor(
    private val cookDataService: CookDataService
) : BaseViewModel() {
    
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    
    private val _recipes = MutableLiveData<List<RecipeEntry>>()
    val recipes: LiveData<List<RecipeEntry>> = _recipes
    
    init {
        loadRecipes()
    }
    
    fun loadRecipes() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val recipeList = cookDataService.getAllRecipes()
                _recipes.value = recipeList
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _loading.value = false
            }
        }
    }
    
    fun searchRecipes(query: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val searchResults = cookDataService.searchRecipes(query)
                _recipes.value = searchResults
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _loading.value = false
            }
        }
    }
    
    fun updateText(newText: String) {
        _text.value = newText
    }
}
```

### 3. Navigation Implementation

#### Navigation Graph
```xml
<!-- res/navigation/mobile_navigation.xml -->
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/mobile_navigation"
    app:startDestination="@id/navigation_home">

    <fragment
        android:id="@+id/navigation_home"
        android:name="cn.chiichen.cook.ui.home.HomeFragment"
        android:label="@string/title_home" />

    <fragment
        android:id="@+id/navigation_dashboard"
        android:name="cn.chiichen.cook.ui.dashboard.DashboardFragment"
        android:label="@string/title_dashboard" />

    <fragment
        android:id="@+id/navigation_notifications"
        android:name="cn.chiichen.cook.ui.notifications.NotificationsFragment"
        android:label="@string/title_notifications" />
</navigation>
```

#### Navigation Setup in MainActivity
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupNavigation()
    }
    
    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navigation_home)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
```

## Data Management

### 1. Repository Pattern (Future Implementation)

```kotlin
interface RecipeRepository {
    suspend fun getAllRecipes(): List<RecipeEntry>
    suspend fun getRecipesByDifficulty(difficulty: String): List<RecipeEntry>
    suspend fun searchRecipes(query: String): List<RecipeEntry>
    suspend fun saveRecipe(recipe: RecipeEntry)
    suspend fun deleteRecipe(recipe: RecipeEntry)
}

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val csvReader: CsvReader
) : RecipeRepository {
    
    override suspend fun getAllRecipes(): List<RecipeEntry> {
        return withContext(Dispatchers.IO) {
            recipeDao.getAllRecipes()
        }
    }
    
    override suspend fun getRecipesByDifficulty(difficulty: String): List<RecipeEntry> {
        return withContext(Dispatchers.IO) {
            recipeDao.getRecipesByDifficulty(difficulty)
        }
    }
    
    override suspend fun searchRecipes(query: String): List<RecipeEntry> {
        return withContext(Dispatchers.IO) {
            recipeDao.searchRecipes("%$query%")
        }
    }
    
    override suspend fun saveRecipe(recipe: RecipeEntry) {
        withContext(Dispatchers.IO) {
            recipeDao.insertRecipe(recipe)
        }
    }
    
    override suspend fun deleteRecipe(recipe: RecipeEntry) {
        withContext(Dispatchers.IO) {
            recipeDao.deleteRecipe(recipe)
        }
    }
}
```

### 2. Room Database (Future Implementation)

```kotlin
@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val stuff: String,
    val bv: String,
    val difficulty: String,
    val tags: String,
    val methods: String,
    val tools: String
)

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<RecipeEntity>
    
    @Query("SELECT * FROM recipes WHERE difficulty = :difficulty")
    suspend fun getRecipesByDifficulty(difficulty: String): List<RecipeEntity>
    
    @Query("SELECT * FROM recipes WHERE name LIKE :query OR tags LIKE :query")
    suspend fun searchRecipes(query: String): List<RecipeEntity>
    
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity)
    
    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}

@Database(entities = [RecipeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
```

## Testing Strategy

### 1. Unit Tests

#### ViewModel Testing
```kotlin
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    
    @Mock
    private lateinit var cookDataService: CookDataService
    
    @Mock
    private lateinit var recipeRepository: RecipeRepository
    
    private lateinit var homeViewModel: HomeViewModel
    
    @Before
    fun setup() {
        homeViewModel = HomeViewModel(cookDataService)
    }
    
    @Test
    fun `loadRecipes should update recipes LiveData`() = runTest {
        // Given
        val expectedRecipes = listOf(
            RecipeEntry("Test Recipe", "Test Ingredients", "Test BV", "Easy", "Test", "Test Methods", "Test Tools")
        )
        whenever(cookDataService.getAllRecipes()).thenReturn(expectedRecipes)
        
        // When
        homeViewModel.loadRecipes()
        
        // Then
        assertEquals(expectedRecipes, homeViewModel.recipes.value)
    }
    
    @Test
    fun `searchRecipes should update recipes with search results`() = runTest {
        // Given
        val query = "pasta"
        val expectedRecipes = listOf(
            RecipeEntry("Pasta Carbonara", "Pasta, Eggs", "High protein", "Medium", "Italian", "Cook pasta", "Pot")
        )
        whenever(cookDataService.searchRecipes(query)).thenReturn(expectedRecipes)
        
        // When
        homeViewModel.searchRecipes(query)
        
        // Then
        assertEquals(expectedRecipes, homeViewModel.recipes.value)
    }
}
```

#### Repository Testing
```kotlin
@RunWith(MockitoJUnitRunner::class)
class RecipeRepositoryTest {
    
    @Mock
    private lateinit var recipeDao: RecipeDao
    
    @Mock
    private lateinit var csvReader: CsvReader
    
    private lateinit var recipeRepository: RecipeRepository
    
    @Before
    fun setup() {
        recipeRepository = RecipeRepositoryImpl(recipeDao, csvReader)
    }
    
    @Test
    fun `getAllRecipes should return recipes from DAO`() = runTest {
        // Given
        val expectedRecipes = listOf(
            RecipeEntry("Test Recipe", "Test Ingredients", "Test BV", "Easy", "Test", "Test Methods", "Test Tools")
        )
        whenever(recipeDao.getAllRecipes()).thenReturn(expectedRecipes.map { it.toEntity() })
        
        // When
        val result = recipeRepository.getAllRecipes()
        
        // Then
        assertEquals(expectedRecipes, result)
    }
}
```

### 2. UI Tests

#### Fragment Testing
```kotlin
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun testHomeFragmentDisplay() {
        // Given
        onView(withId(R.id.navigation_home)).perform(click())
        
        // Then
        onView(withId(R.id.text_home))
            .check(matches(isDisplayed()))
            .check(matches(withText("This is home Fragment")))
    }
    
    @Test
    fun testSearchFunctionality() {
        // Given
        onView(withId(R.id.navigation_home)).perform(click())
        
        // When
        onView(withId(R.id.search_edit_text))
            .perform(typeText("pasta"), closeSoftKeyboard())
        
        onView(withId(R.id.search_button))
            .perform(click())
        
        // Then
        // Verify search results are displayed
        onView(withId(R.id.recipe_list))
            .check(matches(isDisplayed()))
    }
}
```

## Performance Optimization

### 1. ViewBinding Optimization
```kotlin
// Use lazy initialization for ViewBinding
private val _binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
private val binding get() = _binding!!

// Proper cleanup
override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
```

### 2. LiveData Optimization
```kotlin
// Use distinctUntilChanged for performance
val recipes: LiveData<List<RecipeEntry>> = _recipes.distinctUntilChanged()

// Use Transformations for data transformation
val recipeCount: LiveData<Int> = Transformations.map(recipes) { it.size }
```

### 3. Coroutines Best Practices
```kotlin
class HomeViewModel : ViewModel() {
    
    fun loadRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipes = cookDataService.getAllRecipes()
                withContext(Dispatchers.Main) {
                    _recipes.value = recipes
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}
```

## Security Considerations

### 1. Input Validation
```kotlin
fun validateRecipe(recipe: RecipeEntry): ValidationResult {
    return when {
        recipe.name.isBlank() -> ValidationResult.Error("Recipe name cannot be empty")
        recipe.stuff.isBlank() -> ValidationResult.Error("Ingredients cannot be empty")
        recipe.methods.isBlank() -> ValidationResult.Error("Cooking methods cannot be empty")
        else -> ValidationResult.Success
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}
```

### 2. File Access Security
```kotlin
class CsvReader {
    fun readData(context: Context): ArrayList<RecipeEntry> {
        return try {
            val inputStream = context.assets.open(DATA_PATH)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val csvReader = CSVReader(reader)
            
            // Process CSV data safely
            // ...
            
        } catch (e: Exception) {
            Log.e("CsvReader", "Error reading CSV file", e)
            ArrayList()
        }
    }
}
```

## Error Handling

### 1. Global Error Handling
```kotlin
class ErrorHandler {
    companion object {
        fun handleError(throwable: Throwable, context: Context) {
            when (throwable) {
                is IOException -> showError(context, "Network error occurred")
                is SecurityException -> showError(context, "Permission denied")
                else -> showError(context, "An unexpected error occurred")
            }
        }
        
        private fun showError(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
```

### 2. ViewModel Error Handling
```kotlin
class HomeViewModel : ViewModel() {
    private val _error = MutableLiveData<ErrorEvent>()
    val error: LiveData<ErrorEvent> = _error
    
    private fun handleError(throwable: Throwable) {
        val errorMessage = when (throwable) {
            is IOException -> "Network error occurred"
            is SecurityException -> "Permission denied"
            else -> "An unexpected error occurred"
        }
        _error.value = ErrorEvent(errorMessage)
    }
}

data class ErrorEvent(val message: String)
```

## Build Configuration

### 1. ProGuard Rules
```proguard
# Keep data classes
-keep class cn.chiichen.cook.model.** { *; }

# Keep ViewModels
-keep class cn.chiichen.cook.ui.**.ViewModel { *; }

# Keep CSV reader
-keep class com.opencsv.** { *; }
```

### 2. Build Variants
```kotlin
android {
    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}
```

This technical documentation provides comprehensive coverage of the Cook App's architecture, implementation patterns, and development guidelines. It serves as a reference for developers working on the project and can be extended as new features are added.