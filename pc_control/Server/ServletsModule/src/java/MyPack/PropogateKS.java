/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyPack;

import java.awt.Robot;
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

@WebServlet(name = "PropogateKS", urlPatterns = {"/PropogateKS"})
public class PropogateKS extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        SingleCommand eIn = new SingleCommand();
        String eOut = "";
        
        try
        {
            ObjectInputStream in = new ObjectInputStream(request.getInputStream());
            eIn = (SingleCommand)in.readObject();
            in.close();
        }catch(Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace();
        }
        try{
            Robot bot = new Robot();
            int size = eIn.command.size();
            if(!eIn.commandName.equals("")){
                for(int i=0;i<size;i++){
                    bot.keyPress(eIn.command.elementAt(i));
                    System.out.println("Pressing  : " + eIn.desc.elementAt(i));
                    bot.delay(1);
                }
                for(int i=size-1;i>=0;i--){
                    bot.keyRelease(eIn.command.elementAt(i));
                    System.out.println("Relesaing  : " + eIn.desc.elementAt(i));
                    bot.delay(1);
                }
            }else{
                for(int i=0;i<size;i++){
                    if(((eIn.command.elementAt(i) > 97) && (eIn.command.elementAt(i) < 123))){
                        bot.keyPress(eIn.command.elementAt(i)-32);
                        System.out.println("Pressing  : " + eIn.command.elementAt(i) + "  " + ((int)eIn.command.elementAt(i)));
                        bot.delay(1);
                        bot.keyRelease(eIn.command.elementAt(i)-32);
                        bot.delay(1);
                    }else{
                        bot.keyPress(KeyEvent.VK_SHIFT);
                        bot.delay(1);
                        bot.keyPress(eIn.command.elementAt(i));
                        System.out.println("Pressing  : " + eIn.command.elementAt(i) + "  " + ((int)eIn.command.elementAt(i)));
                        bot.delay(1);
                        bot.keyRelease(eIn.command.elementAt(i));
                        bot.delay(1);
                        bot.keyRelease(KeyEvent.VK_SHIFT);
                        bot.delay(1);
                    }
                }
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
