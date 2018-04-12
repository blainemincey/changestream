package com.mongodb;

import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;

import static java.util.Arrays.asList;
import static com.mongodb.client.model.Filters.*;

/**
 * @author bmincey (blaine.mincey@gmail.com)
 * Date Created: 3/19/18
 */
public class ChangeStreamListener {

    MongoCollection<Document> collection = null;

    /**
     *
     */
    public ChangeStreamListener() {
        initDB();
        listen();
    }

    /**
     *
     */
    private void initDB() {
        collection = new MongoClient(
                new MongoClientURI(Constants.DB_URI))
                .getDatabase(Constants.DB)
                .getCollection(Constants.COLLECTION);
    }

    /**
     *
     */
    private void listen() {
        ChangeStreamIterable<Document> changes = collection.watch(asList(
                Aggregates.match(and(asList(
                        in("operationType", asList("insert")),
                        gt("fullDocument.Price", 75)))
                )));

        changes.forEach(new Block<ChangeStreamDocument<Document>>() {
            @Override
            public void apply(ChangeStreamDocument<Document> t) {
                System.out.println("received: " + t.getFullDocument());
            }
        });
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        new ChangeStreamListener();
    }
}
