/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyPack;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Parth
 */
@WebServlet(name = "PropogateMouse", urlPatterns = {"/PropogateMouse"})
public class PropogateMouse extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        SingleMouse eIn = new SingleMouse();
        String eOut = "";
        
        try 
        {
            ObjectInputStream in = new ObjectInputStream(request.getInputStream());
            eIn = (SingleMouse)in.readObject();
            in.close();
        }catch(Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace();
        }
        try{
            Robot bot = new Robot();
            if(eIn.click == 0){
                Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
                double sx = sd.width / 640.0;
                double sy = sd.height / 480.0;
                bot.mouseMove((int)(eIn.mX * sx), (int)(eIn.mY * sy));
            }else if(eIn.click == 1){
                bot.mousePress(InputEvent.BUTTON1_MASK);
                bot.delay(5);
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
            }else if(eIn.click == 2){
                bot.mousePress(InputEvent.BUTTON1_MASK);
                bot.delay(5);
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
                bot.delay(5);
                bot.mousePress(InputEvent.BUTTON1_MASK);
                bot.delay(5);
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
            }else if(eIn.click == 3){
                bot.mousePress(InputEvent.BUTTON3_MASK);
                bot.delay(5);
                bot.mouseRelease(InputEvent.BUTTON3_MASK);
            }else if(eIn.click == 4){
                bot.mousePress(InputEvent.BUTTON1_MASK);
            }else if(eIn.click == 5){
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
        }catch(Exception e){
            System.out.println("Robot error : " + e);
        }
        try {
            // write object to file...
            ObjectOutputStream obOut = new ObjectOutputStream(response.getOutputStream());
            obOut.writeObject(eOut);
            obOut.close();
        }catch(Exception e) {
            System.out.println("ERROR Writing op : " + e);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        new JavaLib.LoadForm();
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         new JavaLib.LoadForm();
       processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
