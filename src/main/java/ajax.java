import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ajax")
public class ajax extends HttpServlet {
    // JDBC ????????????? URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/petstore?&useSSL=false&serverTimezone=UTC";

    // ?????????????????????????????????
    static final String USER = "root";
    static final String PASS = "lxy790524";
    // String sqlStr = "select pet_ID,pet_Name,Age,Sex,visit_time,visit_attention from medical_record";
    String sqlStr = "select pet_ID,pet_Name,Age,Sex,visit_time,visit_attention from medical_record";
    // String sqlInsert = "insert into medical_record " +"VALUES (23, 'Mike', '3','男','2022-09-12','发热')";
    String sqlInsert = "";
    //String sqlUpdate = "update medical_record " +"set visit_time = '2022-10-10' where pet_ID in (1,3)";
    String sqlUpdate="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ajax() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out= response.getWriter();
        StringBuilder json=new StringBuilder();
        String jsonStr="";
        Connection conn = null;
        Statement stmt = null;
        //out.print("<font color='red'>测试post方法</font>");
        //String username=request.getParameter("username");
        //String password=request.getParameter("password");
        //out.print("用户名是："+username+" 密码是："+password);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println( "???????????!" );

            Connection con = DriverManager.getConnection(DB_URL, USER,PASS );

            System.out.println( "???????????!" );

            Statement st = con.createStatement();
            System.out.println( "????Statement???!" );
            System.out.println( "??????????" );
            //???????

            //???????
            ResultSet rs = st.executeQuery( sqlStr );
            System.out.println( "?????????????!" );
            System.out.println( "----------------!" );

            json.append("[");


            while(rs.next()){
                // ?????μ???
                int pet_ID  = rs.getInt("pet_ID");
               // String pet_ID = rs.getString("pet_ID");
                String pet_Name = rs.getString("pet_Name");
                int Age  = rs.getInt("Age");
              //  String Age = rs.getString("Age");
                String Sex = rs.getString("Sex");
                String visit_time = rs.getString("visit_time");
                String visit_attention = rs.getString("visit_attention");
                //String jsonStr="[{\"name\":\"wangwu\",\"age\":四\",\"age\":24,\"addr\":\"北京大兴\"}]";
                // ???????
                json.append("{\"pet_ID\":");
                json.append(pet_ID);
                json.append(",\"pet_Name\":\"");
                json.append(pet_Name);
                json.append("\",\"Age\":");
                json.append(Age);
                json.append(",\"Sex\":\"");
                json.append(Sex);
                json.append("\",\"visit_time\":\"");
                json.append(visit_time);
                json.append("\",\"visit_attention\":\"");
                json.append(visit_attention);
                json.append("\"},");
            }

            // ??????
            jsonStr=json.substring(0,json.length()-1)+"]";

        } catch(SQLException se) {
            // ???? JDBC ????
            se.printStackTrace();
        } catch(Exception e) {
            // ???? Class.forName ????
            e.printStackTrace();
        }finally{
            // ?????????????????
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        out.print(jsonStr);

    }
}