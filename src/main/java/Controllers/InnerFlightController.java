package Controllers;

import com.google.common.base.CaseFormat;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static Main.Ticket.ticketRepository;
import static Utility.Utility.generateSuccessMsg;
import static com.mongodb.client.model.Filters.*;

public class InnerFlightController {

    public static String store(JSONObject jsonObject) {

        Document newDoc = new Document();

        for(String key : jsonObject.keySet()) {

            if(jsonObject.get(key) instanceof JSONObject) {
                newDoc.put(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key), jsonObject.getJSONObject(key).toString());
            }
            else {
                newDoc.put(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key), jsonObject.get(key));
            }
        }

        ticketRepository.insertOne(newDoc);
        return "ok";
    }

    public static String search(String src, String dest,
                                String startAt, String endAt,
                                int infant, int child, int adult) {

        List<Bson> filters = new ArrayList<>();
        filters.add(eq("src", src));
        filters.add(eq("dest", dest));
        filters.add(eq("infant", infant));
        filters.add(eq("child", child));
        filters.add(eq("adult", adult));
        filters.add(eq("start_date", startAt));

        if(endAt != null && !endAt.isEmpty())
            filters.add(eq("end_date", endAt));
        else
            filters.add(exists("end_date", false));

        List<Document> docs = ticketRepository.find(and(filters), null);
        if(docs.size() > 0)
            return docs.get(0).getString("result");

        return generateSuccessMsg("result", new ArrayList<>());

    }



}
