/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import twitter4j.*;
import twitter4j.Twitter.*;
import twitter4j.auth.*;
import twitter4j.auth.RequestToken;
/**
 *
 * @author Michal
 */
public class Auth extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
/*
            // The factory instance is re-useable and thread safe.
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer("[consumer key]", "[consumer secret]");
            RequestToken requestToken = twitter.getOAuthRequestToken();
            AccessToken accessToken = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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

        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
