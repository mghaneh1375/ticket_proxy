package DB;


import com.mongodb.client.FindIterable;
import org.bson.Document;

import static Main.Ticket.mongoDatabase;
import static Utility.Statics.USER_EXPIRATION_SEC;
import static Utility.Statics.USER_LIMIT_CACHE_SIZE;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

public class UserRepository extends Common {

    public UserRepository() {
        init();
    }

    @Override
    void init() {
        table = "user";
        secKey = "NID";
        documentMongoCollection = mongoDatabase.getCollection(table);
    }

    public synchronized Document findByUsername(String username) {

        Document user = isInCache(table, username);
        if (user != null)
            return user;

        FindIterable<Document> cursor = documentMongoCollection.find(
                or(
                        eq("phone", username),
                        eq("mail", username)

                )
        );

        if (cursor.iterator().hasNext()) {
            Document doc = cursor.iterator().next();
            addToCache(table, doc, username, USER_LIMIT_CACHE_SIZE, USER_EXPIRATION_SEC);
            return doc;
        }

        return null;
    }
}
