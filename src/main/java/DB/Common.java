package DB;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

public abstract class Common extends Repository {

    MongoCollection<Document> documentMongoCollection = null;
    String table = "";
    String secKey = "";


    public void insertOne(Document newDoc) {
        documentMongoCollection.insertOne(newDoc);
    }

    public ArrayList<Document> find(Bson filter, Bson project) {

        FindIterable<Document> cursor;

        if (project == null) {
            if (filter == null)
                cursor = documentMongoCollection.find();
            else
                cursor = documentMongoCollection.find(filter);
        } else {
            if (filter == null)
                cursor = documentMongoCollection.find().projection(project);
            else
                cursor = documentMongoCollection.find(filter).projection(project);
        }

        ArrayList<Document> result = new ArrayList<>();

        for (Document doc : cursor)
            result.add(doc);

        return result;
    }

    // read only
    public ArrayList<Document> findByIds(List<ObjectId> objectIds) {

        ArrayList<Document> infos = new ArrayList<>();
        ArrayList<Document> tmp = new ArrayList<>();

        FindIterable<Document> cursor =
                documentMongoCollection.find(in("_id", objectIds));

        for (Document doc : cursor)
            tmp.add(doc);

        for(ObjectId oId : objectIds) {

            boolean find = false;
            for (Document doc : tmp) {
                if(doc.getObjectId("_id").equals(oId)) {
                    infos.add(doc);
                    find = true;
                    break;
                }
            }

            if(!find)
                return null;
        }

        return infos;
    }

    public void replaceOne(ObjectId id, Document newDoc) {
        documentMongoCollection.replaceOne(eq("_id", id), newDoc);
    }

    public synchronized Document findById(ObjectId id) {
        FindIterable<Document> cursor = documentMongoCollection.find(eq("_id", id));
        if (cursor.iterator().hasNext()) {
            return cursor.iterator().next();
        }

        return null;
    }

    public void updateOne(Bson filter, Bson update) {
        documentMongoCollection.updateOne(filter, update);
    }

    abstract void init();

}
