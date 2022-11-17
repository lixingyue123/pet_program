import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/PetDatabase")
public class PetDatabase extends HttpServlet {

    // JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/petstore?&useSSL=false&serverTimezone=UTC";

    // ���ݿ���û��������룬��Ҫ�����Լ�������
    static final String USER = "root";
    static final String PASS = "lxy790524";
    String sqlStr = "select pet_ID,pet_Name,Age,Sex,visit_time,visit_attention from medical_record";
   // String sqlInsert = "insert into medical_record " +"VALUES (23, 'Mike', '3','男','2022-09-12','发热')";
    String sqlInsert = "";
    //String sqlUpdate = "update medical_record " +"set visit_time = '2022-10-10' where pet_ID in (1,3)";
    String sqlUpdate="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetDatabase() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        // ������Ӧ��������
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String title = "Servlet MySQL Connection";
        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println( "���������ɹ�!" );

            Connection con = DriverManager.getConnection(DB_URL, USER,PASS );

            System.out.println( "�������ݿ�ɹ�!" );

            Statement st = con.createStatement();
            System.out.println( "����Statement�ɹ�!" );
            System.out.println( "��ʼ��ѯ����" );
            //�������
            //st.executeUpdate(sqlInsert);
            //System.out.println("��������ݳɹ�");
            String m_time=request.getParameter("m_time");
            System.out.println(m_time);
            sqlUpdate="update medical_record "+"set visit_time = "+m_time+" where pet_ID in (1,3)";

            //��������
            st.executeUpdate(sqlUpdate);
            System.out.println("�������ݳɹ�");

            String pet_name=request.getParameter("pname");
            String pet_id=request.getParameter("id");
            String pet_age=request.getParameter("age");
            String pet_sex=request.getParameter("sex");
            String pet_attention=request.getParameter("attention");
            System.out.println(pet_name);
            sqlInsert = "insert into medical_record VALUES " +
                    "("+"'"+pet_id+"'"+","+"'"+pet_name+"'"+","+"'"+pet_age+"'"+","+"'"+pet_sex+"'"+","+"'"+m_time+"'"+","+"'"+pet_attention+"'"+")";
            st.executeUpdate(sqlInsert);
            System.out.println("�������ݳɹ�");

            //��ѯ����
            ResultSet rs = st.executeQuery( sqlStr );
            System.out.println( "��ѯ���ݲ����ɹ�!" );
            System.out.println( "----------------!" );


            while(rs.next()){
                // ͨ���ֶμ���
                int id  = rs.getInt("pet_ID");
                String name = rs.getString("pet_Name");
                int age  = rs.getInt("Age");
                String sex = rs.getString("Sex");
                String time = rs.getString("visit_time");
                String attention = rs.getString("visit_attention");

                // �������
                out.println("pet_ID: " + id);
                out.println(", name: " + name);
                out.println(", age: " + age);
                out.println(", sex: " + sex);
                out.println(", visit_time: " +time );
                out.println(", visit_attention: " + attention);
                out.println("<br />");
            }
            out.println("</body></html>");

            // ��ɺ�ر�

        } catch(SQLException se) {
            // ���� JDBC ����
            se.printStackTrace();
        } catch(Exception e) {
            // ���� Class.forName ����
            e.printStackTrace();
        }finally{
            // ��������ڹر���Դ�Ŀ�
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

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }



}
