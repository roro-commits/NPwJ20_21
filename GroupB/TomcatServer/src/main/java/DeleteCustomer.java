
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/DeleteCustomer"})
public class DeleteCustomer extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
         int count = 0;
         response.setContentType("application/octet-stream");
         InputStream in = request.getInputStream();
         ObjectInputStream inputFromApplet = new ObjectInputStream(in);
         OutputStream outstr = response.getOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(outstr);             
  
         try{
           String ID = (String)inputFromApplet.readObject();
           
            if(ID != null)
            {
              Class.forName("MYSQL DRIVER");  // load the driver
              Connection con = DriverManager.getConnection("DriverManager WITH DATABASE, USER, PASSWORD");  
              PreparedStatement delete = con.prepareStatement("Delete from Customers where ID = ?");
              delete.setString(1, ID);
              count = delete.executeUpdate();
              delete.close();
              con.close();
            }
             
         }
         catch(Exception ex)
         {
            System.out.println(ex.toString());
         }
         finally {
            oos.writeInt(count);
            oos.flush();
	    oos.close();        
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
