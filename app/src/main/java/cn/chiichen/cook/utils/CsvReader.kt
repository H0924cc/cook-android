package cn.chiichen.cook.utils

import cn.chiichen.cook.model.RecipeEntry
import com.opencsv.CSVReader
import java.io.FileReader
private const val dataPath : String = "data.csv"
class CsvReader {
    public fun readData():ArrayList<RecipeEntry>{
        val fileReader:FileReader = FileReader(dataPath);
        var ret : ArrayList<RecipeEntry>  =ArrayList();
        return ret;
    }
}