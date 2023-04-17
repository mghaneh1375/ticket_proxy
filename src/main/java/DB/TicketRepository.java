package DB;


import static Main.Ticket.mongoDatabase;

public class TicketRepository extends Common {

    public TicketRepository() {
        init();
    }

    @Override
    void init() {
        table = "ticket";
        documentMongoCollection = mongoDatabase.getCollection(table);
    }

}
