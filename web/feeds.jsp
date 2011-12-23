<%@page import="persistence.Snapshot"%>
<%@page import="twitter4j.auth.RequestToken"%>
<%@page import="twitter4j.auth.AccessToken"%>
<%@page import="twitter4j.User"%>
<%@page import="twitter4j.ResponseList"%>
<%@page import="twitter4j.examples.friendsandfollowers.GetFriendsIDs"%>
<%@page import="twitter4j.IDs"%>
<%@page import="java.util.Iterator"%>
<%@page import="twitter4j.Tweet"%>
<%@page import="twitter4j.QueryResult"%>
<%@page import="twitter4j.Query"%>
<%@page import="java.util.List"%>
<%@page import="twitter4j.TwitterFactory"%>
<%@page import="twitter4j.Twitter"%>
<%@page import="java.util.*"%>

<%@page import="services.GoogleChart"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Tweets</title>
</head>
<body>

    <h1>Sosání tweetů</h1>
    <%
        // vytahneme feedy
        Twitter twitter = new TwitterFactory().getInstance();
        Query query = new Query("#doctrine2");
        query.setRpp(10);
        QueryResult result = twitter.search(query);
        String total = "" + result.getTweets().size();
        List<Tweet> tweets = result.getTweets();
    %>
    <p>Celkem tweetů per page je: <%= total %></p>

    <h2>Výpis tweetů:</h2>
    <% for (Tweet tweet : tweets) { %>
        <%
            long userId = tweet.getFromUserId();
            String user = tweet.getFromUser();


            //Twitter twitter = new Twitter();

            //twitter.setOAuthConsumer("p8tgKdo4Thla9yopDd6dQ", "1SSpKxFkPQ8PmvJnvjARVgaVU6km4lshvXlxQow");
            //RequestToken requestToken  = twitter.getOAuthRequestToken();
            //String token = requestToken.getToken();
            //String tokenSecret = requestToken.getTokenSecret();

            //String authUrl = requestToken.getAuthorizationURL();

            //System.out.println("token "+token);
            //System.out.println("tokensecret "+tokenSecret);
            //System.out.println("authURL "+authUrl);
            //RequestToken twitterRequestToken = twitter.getOAuthRequestToken();

            //AccessToken accessToken = twitter.getOAuthAccessToken((String) session.getAttribute("token"), (String) session.getAttribute("tokenSecret"));
            //twitter.setOAuthAccessToken(twitterRequestToken);

            //User user2 = twitter.verifyCredentials();

            /*
            String token = twitterRequestToken.getToken();
            String tokenSecret = twitterRequestToken.getTokenSecret();
            persist(token,tokenSecret);
            */

            //Twitter twitter = new Twitter();
            /*
            HttpSession session = request.getSession();

            twitter.setOAuthConsumer(oauth.consumerSecret,
            Constants.CONSUMER_SECRET);
            AccessToken accessToken = twitter.getOAuthAccessToken(
            (String) session.getAttribute("token"), (String) session
            .getAttribute("tokenSecret"));
            twitter.setOAuthAccessToken(accessToken);

            User user = twitter.verifyCredentials();
            */


            //User user2 = twitter.verifyCredentials();
            //System.out.println("user "+user2);
            // info o autorovi
            /*IDs idecka = twitter.getFriendsIDs(userId, -1);
            //IDs idecka = twitter.getFollowersIDs(userId, twitter.c);
            long[] ideckalong = idecka.getIDs();
            System.out.println("cau");
            for (int i = 0; i < ideckalong.length; i++){
                System.out.println("iceko "+ideckalong[i]);
                }
 * */
            //List<User> kamosi = twitter.lookupUsers(ideckalong);            
            //IDs idecka = twitter.getFollowersIDs(userId);
            //long[] ideckalong = idecka.getIDs();
            //List<User> nasledovnici = twitter.lookupUsers(ideckalong);


                /*Twitter twitter = new TwitterFactory().getInstance();
                twitter.setOAuthConsumer("p8tgKdo4Thla9yopDd6dQ", "1SSpKxFkPQ8PmvJnvjARVgaVU6km4lshvXlxQow");
                RequestToken requestToken = twitter.getOAuthRequestToken();
                AccessToken accessToken = null;
                //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (null == accessToken) {
                  System.out.println("Open the following URL and grant access to your account:");
                  System.out.println(requestToken.getAuthorizationURL());
                  System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
                  String pin = br.readLine();
                  try{
                     if(pin.length() > 0){
                       accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                     }else{
                       accessToken = twitter.getOAuthAccessToken();
                     }
                  } catch (TwitterException te) {
                    if(401 == te.getStatusCode()){
                      System.out.println("Unable to get the access token.");
                    }else{
                      te.printStackTrace();
                    }
                  }
                }
                //persist to the accessToken for future reference.
                storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
                Status status = twitter.updateStatus(args[0]);
                System.out.println("Successfully updated the status to [" + status.getText() + "].");
                System.exit(0);
                */
                /*
            List<Snapshot> snapshots = new LinkedList<Snapshot>();
            for(int i  = 0; i < 15; i++){
                Snapshot snap = new Snapshot(new Date(), i*4+3, i*7-2);
                snapshots.add(snap);
            }

            GoogleChart gch = new GoogleChart();
            String googleUrl = gch.getGoogleURL(snapshots);
            */
        %>

        <p>ciste priprava generatoru googleChart url, dokud ale nerozbehneme tahani kamaradu a nasledniku, tak je to k nicemu
            
        </p>

    <p>
        <strong>@<%= user %> - </strong> <%= tweet.getText() %> (id: <%= tweet.getId() %>, userId: <%= userId %>)<br />
        <small>Author:</small>
    </p>
    <% } %>

</body>
</html>
