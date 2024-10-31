package HomeworkPackage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Scanner;



public class DatabaseConnection {
    private static final String DATABASE_NAME = "testdb";
    private static final String COLLECTION_NAME = "testcollection";
    private static MongoCollection<Document> collection;

    public static MongoCollection<Document> createConnection() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase(DATABASE_NAME);
        
        if (database.getCollection(COLLECTION_NAME) == null) {
            database.createCollection(COLLECTION_NAME);
        }
        
        collection = database.getCollection(COLLECTION_NAME);
        return collection;
    }
}
