/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyPack;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FetchImage", urlPatterns = {"/FetchImage"})
public class FetchImage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedImage scSmall = null;
        try{
            BufferedImage sc = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            scSmall = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB);
            Graphics2D gSrc = sc.createGraphics();
            Graphics2D gDest = scSmall.createGraphics();
            //gDest.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            //gDest.drawImage(sc,0, 0, fb.WW, fb.HH, null);
            AffineTransform at = AffineTransform.getScaleInstance((double)640/sc.getWidth(),(double)480/sc.getHeight());
            gDest.drawRenderedImage(sc, at);
            
            
            MyImage mi = new MyImage();
            mi.ww = 640;
            mi.hh = 480;
            mi.img = new int[mi.ww * mi.hh];
            mi.img = scSmall.getRGB(0, 0, mi.ww, mi.hh, mi.img, 0, mi.ww);
            
            ObjectOutputStream obOut = new ObjectOutputStream(response.getOutputStream());
            obOut.writeObject(mi);
            obOut.close();
        }catch(Exception e){}

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
