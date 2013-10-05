/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyPack;

import java.awt.Robot;
import java.io.*;
import java.security.MessageDigest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String eIn = "";
        String eOut = "";
        
        try
        {
            ObjectInputStream in = new ObjectInputStream(request.getInputStream());
            eIn = (String)in.readObject();
            in.close();
        }catch(Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace();
        }
        System.out.println(eIn);
        String pass = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader("c:\\projectData\\5862DB\\pass.dat"));
            pass = br.readLine();
            br.close();
        }catch(Exception e){
            System.out.println("Error Accessing Password File : " + e);
        }
        eIn = getSHA1(eIn);
        if(pass.equals(eIn)){
            eOut = "OK";
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

    String getSHA1(String str){
    	try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] datArr = str.getBytes();
            md.update(datArr, 0, datArr.length);
            byte[] sha1hash = md.digest();
            StringBuilder sb = new StringBuilder();
            int halfByte = 0;
            for(int i=0;i<sha1hash.length;i++){
                halfByte = (sha1hash[i] >> 4) & 0xf;
                if(halfByte < 10){
                    sb.append((char)(halfByte + 48));
                }else{
                    sb.append((char)(halfByte + 55));
                }
                halfByte = sha1hash[i] & 0xf;
                if(halfByte < 10){
                    sb.append((char)(halfByte + 48));
                }else{
                    sb.append((char)(halfByte + 55));
                }
            }
            return sb.toString();
    	}catch (Exception e) {
			// TODO: handle exception
	}
    	return "";
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
