package HomeworkPackage;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MongoCollection<Document> collection = DatabaseConnection.createConnection();
        Documents documents = new Documents(collection);
        Scanner scanner = new Scanner(System.in);  // Initialize the scanner here

        try {
            while (true) {
                System.out.println("\nSelect an action:");
                System.out.println("1. Create a new document");
                System.out.println("2. Read documents");
                System.out.println("3. Update a document");
                System.out.println("4. Delete a document");
                System.out.println("5. Exit");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        documents.createDocument();
                        break;
                    case "2":
                        documents.readDocuments();
                        break;
                    case "3":
                        documents.updateDocument();
                        break;
                    case "4":
                        documents.deleteDocument();
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println("Invalid input, please try again.");
                }
            }
        } finally {
            scanner.close();
            System.out.println("Exiting...");
        }
    }
}
