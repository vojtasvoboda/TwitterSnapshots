package persistence;

import com.google.appengine.repackaged.org.joda.time.DateTime;

/**
 * Trida reprezentujici jeden Tweet
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class Tweet {

    /**
     * Dostupne parametry pres API:
     * - id (unique identifikator tweetu)
     * - fromUser (autor tweetu)
     * - fromUserId (id autora)
     * - location (lokace)
     * - geoLocation (asi souradnice)
     * - place
     * - source (odkud byl tweet napsany)
     * - text (text zpravy)
     * - toUser (asi nejaka direct message)
     * - toUserId
     * - annotations
     * - createAt
     */
    private long id;
    private String user;
    private int user_id;
    private String location;
    private String source;
    private String text;
    private DateTime createAt;

    /**
     * Default constructor
     */
    public Tweet() {
    }

    /**
     * Full constructor
     * @param id - id autora
     * @param user - jmeno uzivatele
     * @param user_id - id uzivatele
     * @param location - lokace
     * @param source - zdroj zpravy
     * @param text - text zpravy
     * @param createAt - datum vytvoreni
     */
    public Tweet(long id, String user, int user_id, String location, String source, String text, DateTime createAt) {
        this.id = id;
        this.user = user;
        this.user_id = user_id;
        this.location = location;
        this.source = source;
        this.text = text;
        this.createAt = createAt;
    }

    public DateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(DateTime createAt) {
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
