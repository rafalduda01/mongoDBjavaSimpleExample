package src;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class Main {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("vehicles"); //db
        MongoCollection mongoCollection = mongoDatabase.getCollection("cars"); //table

//        save(mongoCollection);
//        read(mongoCollection);
//        readByParam(mongoCollection, "Mark", "BMW");
//        delete(mongoCollection, "Mark", "BMW");
//        update(mongoCollection);

        MongoCollection mongoCollectionBike = mongoDatabase.getCollection("bike"); //table
        saveBike(mongoCollectionBike);

    }

    private static void saveBike(MongoCollection mongoCollectionBike) {
        Document document = new Document();
        document.put("name", "Rower Rafałą");
        document.put("owner", new Person("Rafal", "Rafalek"));

        Document documentPerson = new Document();
        documentPerson.put("Name", "Rafal");
        documentPerson.put("Surname", "Rafalek");

        document.put("owner", documentPerson);
        mongoCollectionBike.insertOne(document);


    }

    private static void update(MongoCollection mongoCollection) {

        Bson eq = Filters.eq("Mark", "Audi");
        Bson newDocument = combine(set("Model", "A2"), set("Color", "Black"));

        mongoCollection.updateOne(eq, newDocument);
    }

    private static void delete(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param, value);
        mongoCollection.deleteMany(document);
    }

    private static void readByParam(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param, value);
        MongoCursor iterator = mongoCollection.find(document).iterator();
        while (iterator.hasNext()) {
            Document next = (Document) iterator.next();
            System.out.println(next.toJson());
        }
    }

    private static void read(MongoCollection mongoCollection) {
        Document first = (Document) mongoCollection.find().first();
        System.out.println(first.toJson());
    }

    private static void save(MongoCollection mongoCollection) {
        Document document1 = new Document();
        document1.put("Mark", "Fiat");
        document1.put("Model", "126p");

        Document document2 = new Document();
        document2.put("Mark", "BMW");
        document2.put("Model", "One");
        document2.put("Color", "Red");

        Document document3 = new Document();
        document3.put("Mark", "BMW");
        document3.put("Model", "One");
        document3.put("Color", Arrays.asList("Red", "Blue", "Gray"));

        Document document4 = new Document();
        document1.put("Mark", "BMW");
        document1.put("Model", "Two");


        mongoCollection.insertMany(Arrays.asList(document1, document2, document3, document4));
    }
}
