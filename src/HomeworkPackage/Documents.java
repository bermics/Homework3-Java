package HomeworkPackage;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Documents {
    private final MongoCollection<Document> collection;

    public Documents(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public void createDocument() {
        String name = getValidInput("Enter name: ", InputValidation::validateName);
        if (collection.find(Filters.eq("name", name)).first() != null) {
            System.out.println("A document with that name already exists.");
            return;
        }

        String phone = getValidInput("Enter phone number: ", InputValidation::validatePhone);
        String email = getValidInput("Enter email address: ", InputValidation::validateEmail);
        String address = getValidInput("Enter address: ", InputValidation::validateAddress);

        Document document = new Document("name", name)
                .append("email", email)
                .append("phone", phone)
                .append("address", address)
                .append("created_at", LocalDateTime.now().toString());

        collection.insertOne(document);
        System.out.println("Document created with ID: " + document.getObjectId("_id"));
    }

    public void readDocuments() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the field to search by (e.g., 'name', 'email'): ");
            String field = scanner.nextLine().trim();

            if (field.isEmpty()) {
                System.out.println("Field is required to retrieve documents.");
                return;
            }

            System.out.print("Enter the value to filter by: ");
            String value = scanner.nextLine().trim();

            if (value.isEmpty()) {
                System.out.println("A value is required to filter by the specified field.");
                return;
            }

            Bson filter = Filters.eq(field, value);

            for (Document doc : collection.find(filter)) {
                System.out.println(doc.toJson());
            }

        } catch (Exception e) {
            System.out.println("Error reading documents: " + e.getMessage());
        }
    }

    public void updateDocument() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the document to update: ");
        String name = scanner.nextLine();

        Document document = collection.find(Filters.eq("name", name)).first();
        if (document == null) {
            System.out.println("No document found with that name.");
            return;
        }

        System.out.println("Current values: " + document.toJson());

        String newEmail = getValidInput("Enter new email: ", InputValidation::validateEmail);
        String newPhone = getValidInput("Enter new phone number: ", InputValidation::validatePhone);
        String newAddress = getValidInput("Enter new address: ", InputValidation::validateAddress);

        Document updates = new Document();
        if (!newEmail.isEmpty()) updates.append("email", newEmail);
        if (!newPhone.isEmpty()) updates.append("phone", newPhone);
        if (!newAddress.isEmpty()) updates.append("address", newAddress);

        if (!updates.isEmpty()) {
            collection.updateOne(Filters.eq("name", name), new Document("$set", updates));
            System.out.println("Document updated.");
        } else {
            System.out.println("No changes were made.");
        }
    }

    public void deleteDocument() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the document to delete: ");
        String name = scanner.nextLine();

        collection.deleteOne(Filters.eq("name", name));
        System.out.println("Document deleted.");
    }

    private String getValidInput(String prompt, InputValidator validator) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            ValidationResult result = validator.validate(input);
            if (result.isValid()) {
                return input;
            } else {
                System.out.println("Invalid input: " + result.getMessage());
            }
        }
    }

    interface InputValidator {
        ValidationResult validate(String input);
    }
}
