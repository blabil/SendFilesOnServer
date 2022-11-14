/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.sendfilesonserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author thebl
 */
public class SendFile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendFile</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendFile at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        if(procesReq(request))generateResponse(response,
                                                request.getPart("file").getSubmittedFileName(),
                                                request.getParameter("destination"));
        else
        generateResponse(response,
                        request.getPart("file").getSubmittedFileName());
    }
    private void generateResponse(HttpServletResponse response, String fileName, String dest)
            throws ServletException, IOException 
    {
        String res =   initRes()
                     + addToRes("Nazwa pliku: "+fileName)
                     + addToRes("Folder docelowy: "+dest)
                     + returnRes()
                     + endRes();
        PrintWriter writer = response.getWriter();
        writer.println(res);
    }
     private void generateResponse(HttpServletResponse response, String fileName)
             throws ServletException, IOException 
    {
        String res =   initRes()
                     + addToRes("Nie udało się dodać pliku: "+fileName)
                     + returnRes()
                     + endRes();
        PrintWriter writer = response.getWriter();
        writer.println(res);
    }
    
    private boolean procesReq(HttpServletRequest request) 
            throws ServletException, IOException
    {
        try
        {
        String appPath = request.getServletContext().getRealPath("");
        String dirPath = request.getParameter("destination");
        String PathDestination = appPath+"/"+dirPath+"/";
        String name = request.getPart("file").getSubmittedFileName();
        request.getPart("file").write(PathDestination+name);
        return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    /*private String nazwa(Part part) {
        String dane = part.getHeader("content-disposition");
        String[] elem = dane.split(";");
        for (String s : elem) {
        if (s.trim().startsWith("filename")) {
        return s.subString(s.indexOf("=") + 2, s.length()-1);
        }
     }
        return "";
    }*/

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private String initRes()
    {            
        return    "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\""
                + "<title>SendFileOnServer</title>"
                + "<link rel=\"stylesheet\" href=\"sytle.css\""
                + "</head"
                + "<body>"; 
    }
    private String endRes()
    {            
        return    "</body>"
                + "</html>"; 
    }
    private String returnRes()
    {            
        return  "<br>"
                + "<form name=\"return\" action=\"/SendFilesOnServer/\" method=\"post\">"
                + "<input type=\"submit\" value=\"Wroc\">"
                + "</form>";
    }
    private String addToRes(String text)
    {
        return    "<br>"
                + "<H1>"
                + text
                + "</H1>";  
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
