package com.mongodb;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.text.DecimalFormat;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author bmincey (blaine.mincey@gmail.com)
 * Date Created: 3/19/18
 */
public class DocumentProducer {


    MongoCollection<Document> collection = null;

    /**
     *
     */
    public DocumentProducer() {

        this.initDB();
        this.produceDocs();
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
     * @return
     */
    private double getRandomDouble() {
        double nextDouble = current().nextDouble(50,100);
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.parseDouble(df.format(nextDouble));
    }

    /**
     *
     */
    private void produceDocs() {

        while(true) {
            Document document = new Document();
            document.put("Symbol", "MDB");
            document.put("Price", getRandomDouble());
            collection.insertOne(document);
            System.out.println(document.toJson());
            try {
                Thread.sleep(2000L + (long) (1000 * Math.random()));
            }
            catch(Exception e) {
                System.err.println(e);
            }
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        new DocumentProducer();
    }
}
