package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.Edge;
import persistence.Snapshot;
import persistence.SnapshotDAO;
import persistence.User;
import twitter4j.IDs;
import twitter4j.Tweet;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Nacteni tweetu a jejich autoru
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class GetTweets extends HttpServlet {

    /**
     * Zpracujeme request na ziskani tweetu (pouze GET)
     * @param request
     * @param response
     * @throws TwitterException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
              throws TwitterException, IOException {

        PrintWriter out = response.getWriter();

        // vytvorime snapshot
        Snapshot snapshot = new Snapshot(new Date());
        SnapshotDAO sndao = new SnapshotDAO();

        try {
            out.println("<html><body>");
            // vytahneme tweety
            Twitter twitter = new TwitterFactory().getInstance();
            Query query = new Query("#doctrine2");
            // nastavime pocet tweetu co se bude stahovat
            query.setRpp(10);
            QueryResult result = twitter.search(query);
            System.out.println("------------------------------------------------");
            out.println("Total tweets: " + result.getTweets().size() + "<br />");
            System.out.println("Total tweets: " + result.getTweets().size());
            List<Tweet> tweets = result.getTweets();

            // omezeni poctu vazeb
            int friendCounter = 0;

            // projedeme tweety
            for (Tweet tweet : tweets) {
                long userId = tweet.getFromUserId();
                String nick = tweet.getFromUser();
                String text = tweet.getText();
                System.out.println("Nick " + nick + " (userid " + userId + ")");
                out.println("<strong>@" + nick + " - </strong> " +
                            text + " (id: " + tweet.getId() +
                            ", userId: " + userId + ")<br />");

                // ulozime uzivatele do snapshotu
                snapshot.addUser(new User(userId, nick));

                // podivame se na vazby uzivatele
                IDs ids;
                long cursor = -1;
                String friendName = "";
                String followerName = "";

                // projdeme vsechny friends a followers
                do {
                    // projdeme vsechny followers
                    friendCounter = 0;
                    System.out.println("GetTweets: Followers of " + nick + ": ");
                    ids = twitter.getFollowersIDs(nick, cursor);
                    for (long id : ids.getIDs()) {
                        System.out.print(id + ", ");
                        // pridame hranu Edge(from, to)
                        snapshot.addEdge(new Edge(id, userId));
                        // zkusime najit jmeno followera
                        try {
                            followerName = twitter.showUser(id).getName();
                        } catch (Exception e) {
                            System.out.println("GetTweets: chyba pri nacitani jmena followera s id " + id + ": " + e.toString());
                            followerName = "";
                        }
                        // pridame followera User(userId, nick);
                        snapshot.addUser(new User(id, followerName));
                        if ((friendCounter++) > 5) break;
                    }

                    // projdeme vsechny friends
                    friendCounter = 0;
                    System.out.println("GetTweets: Friends of " + nick + ": ");
                    ids = twitter.getFriendsIDs(nick, cursor);
                    for (long id : ids.getIDs()) {
                        System.out.print(id + ", ");
                        // pridame hranu Edge(idEdge, from, to)
                        snapshot.addEdge(new Edge(userId, id));
                        // zkusime najit jmeno Frienda
                        try {
                            friendName = twitter.showUser(id).getName();
                        } catch (Exception e) {
                            System.out.println("GetTweets: chyba pri nacitani jmena frienda s id " + id + ": " + e.toString());
                        }
                        // pridame uzivatele User(userId, nick);
                        snapshot.addUser(new User(id, friendName));
                        if ((friendCounter++) > 5) break;
                    }
                } while ((cursor = ids.getNextCursor()) != 0);
            }

            // ulozeni snapshotu viz. finally

        } catch (TwitterException te) {
            System.out.println("GetTweets: chyba nacitani tweetu: " + te.getMessage());

        } finally {
            // ulozime i kdyz je chyba (aspon neco)
            sndao.save(snapshot);
            out.println("</body></html>");
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (TwitterException ex) {
            Logger.getLogger(GetTweets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
