/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Noko
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    , @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id")
    , @NamedQuery(name = "Customer.findByFirstName", query = "SELECT c FROM Customer c WHERE c.firstName = :firstName")
    , @NamedQuery(name = "Customer.findByLastName", query = "SELECT c FROM Customer c WHERE c.lastName = :lastName")
    , @NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address")
    , @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone")})
public class Customer implements Serializable {

    public static String db_url = "jdbc:mysql://localhost/projekat";
    public static String username = "root";
    public static String password = "123";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;

    public Customer() {
    }

    public Customer(Integer id) {
        this.id = id;
    }

    public Customer(Integer id, String firstName, String lastName, String address, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "customer.Customer[ id=" + id + " ]";
    }

    public static void insertCustomer(String first_name, String last_name, String address, String phone) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try (java.sql.Connection conn = DriverManager.getConnection(db_url, username, password);) {

            Statement st = conn.createStatement();
            st.execute("insert into customer(first_name,last_name,address,phone)values('" + first_name + "','" + last_name + "','" + address + "','" + phone + "')");

        } catch (SQLException ex) {
            System.out.println("Error in database conection: \n" + ex.getMessage());
        }
    }

    public static String allCustomers() throws ClassNotFoundException {
        StringBuilder all_customers = new StringBuilder();

        Class.forName("com.mysql.jdbc.Driver");
        try (java.sql.Connection conn = DriverManager.getConnection(db_url, username, password);) {
            Statement st = conn.createStatement();
            st.executeQuery("select * from customer");
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                all_customers.append("Id: "+rs.getString("id")+"\n");
                all_customers.append("First name: "+rs.getString("first_name")+"\n");
                all_customers.append("Last name: "+rs.getString("last_name")+"\n");
                all_customers.append("Address: "+rs.getString("address")+"\n");
                all_customers.append("Phone: "+rs.getString("phone")+"\n");
                all_customers.append("\n");
            }

        } catch (SQLException ex) {
            System.out.println("Error in database conection: \n" + ex.getMessage());
        }

        return all_customers.toString();
    }
    
    public static void edit_customer_byID (int id, String firstname, String lastname, String address, String phone) throws ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(db_url, username, password);) {

            Statement st = conn.createStatement();
            st.execute("update customer set first_name='"+firstname+"',last_name='"+lastname+"',address='"+address+"',phone='"+phone+"' where id='"+id+"'");

        } catch (SQLException ex) {
            System.out.println("Error in database conection: \n" + ex.getMessage());
        }
    }
    
    public static String customer_Search(String search_by, String keyword ) throws ClassNotFoundException{
        StringBuilder sb = new StringBuilder();
         Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(db_url, username, password);) {

            Statement st = conn.createStatement();
            st.execute("select * from customer where "+search_by+"='"+keyword+"'");
            ResultSet rs = st.getResultSet();
       
            while (rs.next()) {
                sb.append("Id: "+rs.getString("id")+"\n");
                sb.append("First name: "+rs.getString("first_name")+"\n");
                sb.append("Last name: "+rs.getString("last_name")+"\n");
                sb.append("Address: "+rs.getString("address")+"\n");
                sb.append("Phone: "+rs.getString("phone")+"\n");
                sb.append("\n");
            }
           if(!rs.first()){
             sb.append("No search results for this keyword. Please enter a different keyword for search!");
         }

        } catch (SQLException ex) {
            System.out.println("Error in database conection: \n" + ex.getMessage());
        }
        
        return sb.toString();
    }

}
