package se.lexicon.mail;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TicketStorage {

    public static ConcurrentMap<String, String> tickets = new ConcurrentHashMap<>();
}
