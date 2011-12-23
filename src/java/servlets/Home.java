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

import javax.servlet.http.HttpSession;

import twitter4j.*;
import twitter4j.Twitter.*;
import twitter4j.auth.*;
/**
 *
 * @author Michal
 */
public class Home extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String pin = request.getParameter("pin");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<?xml version=\"1.0\" encoding=\"iso-8859-2\"?>");
          out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"");
          out.println("    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
          out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
          out.println("<head>");
          out.println("<meta http-equiv=\"content-type\"");
          out.println("    content=\"text/html; charset=iso-8859-2\" />");
          out.println("<title>Sample Servlet</title>");
          out.println("</head>");
          out.println("<body>");         
          out.println("<p>testovaci page</p>");
          out.println("<p>pin</p>");
          out.print(pin);
          
        try {            
            Twitter twitter = new TwitterFactory().getInstance();            
            HttpSession session = request.getSession();
            
            twitter.setOAuthConsumer("p8tgKdo4Thla9yopDd6dQ", "1SSpKxFkPQ8PmvJnvjARVgaVU6km4lshvXlxQow");            
            RequestToken requestToken = twitter.getOAuthRequestToken();            
            //AccessToken accessToken = twitter.getOAuthAccessToken((String) session.getAttribute("token"), (String) session.getAttribute("tokenSecret"));
//            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            AccessToken accessToken = twitter.getOAuthAccessToken();

            twitter.setOAuthAccessToken(accessToken);            
            User user = twitter.verifyCredentials();            
            out.println("user "+user);
        } catch (Exception e)  {
            out.println("e "+e);
        } finally {
            out.println("</body>");
            out.println("</html>");

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
