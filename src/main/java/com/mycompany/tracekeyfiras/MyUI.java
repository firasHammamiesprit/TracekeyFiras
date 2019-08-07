package com.mycompany.tracekeyfiras;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
      
      final VerticalLayout layout = new VerticalLayout();
        FormLayout f = new FormLayout();
Label LserialName = new Label("Serial set name");
         TextField serialName = new TextField();
           Label numberOfSerialLavel = new Label("Number of serials");
                 ListSelect numberOfSerial = new ListSelect();
     for(int i = 1;i<=100;i++){
    numberOfSerial.addItem(i);
        }          
         Button Create = new Button("Create");
       Button Cancel = new Button("Cancel");
       
     Label configurationLabel = new Label("configuration");
CheckBox numbers = new CheckBox("numbers");
CheckBox lowercase = new CheckBox("lowercase");
CheckBox uppercase = new CheckBox("uppercase");
       
 Label ExcludeL = new Label("exclude");
         TextField exclude = new TextField();
         exclude.setValue("exemple O,B,Z");
         
         Label lengthL = new Label("serial length");
          TextField length = new TextField();

       layout.addComponent(f);
        f.addComponents(LserialName,serialName,numberOfSerialLavel,numberOfSerial,configurationLabel,numbers,lowercase,uppercase,ExcludeL,exclude,lengthL,length);
       HorizontalLayout h = new HorizontalLayout();
       h.addComponents(Create,Cancel);
        f.addComponents(h);
      
              //get var values
      
       
         Create.addClickListener( e -> {
             //get values
           String name  = serialName.getValue();
        int number = (int)numberOfSerial.getValue();
        int len = Integer.parseInt(length.getValue());
        //make sure that the 10< len <20
        if(len > 20 || len < 10){
            Notification.show("impossible",
                  "nooooooooo",
                  Notification.Type.WARNING_MESSAGE);
        }
        // here I made a String to collect all the configurations 
        String all = "";
        // here I have to get the selected configuration 
        if(numbers.getValue()){
            all = all + "0123456789";
        }
        if(lowercase.getValue()){
            all = all + "abcdefghijklmnopqrstuvwxyz";
        }
         if(uppercase.getValue()){
            all = all + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        
         // this is teh JDBC part
          try {
              Class.forName("com.mysql.jdbc.Driver");
          } catch (ClassNotFoundException ex) {
              Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
          }
        String url = "jdbc:mysql://localhost:3306/Tracekey";
String username = "root";
String password = "";

System.out.println("Connecting database...");

try (Connection conn = DriverManager.getConnection(url, username, password)) {
    System.out.println("Database connected!");
    PreparedStatement pst = conn.prepareStatement("insert into serialset(name,number) values(?,?)"); 
    pst.setString(1, name);
    pst.setInt(2, len); 
int i = pst.executeUpdate();
      if(i!=0){
        System.out.println("added");
        
      }
      else{
        System.out.println("failed to add");
      }
      
      pst.close();
        PreparedStatement sps = conn.prepareStatement("select id from serialset where name=?"); 
        sps.setString(1, name);
        
        ResultSet rs = sps.executeQuery();
        rs.next();
        
        int id = rs.getInt("id");
        
        rs.close();
        sps.close();
       for(int j=0;j<number;j++){
              Random rnd = new Random();
                List<String> result = new ArrayList<>();
                Consumer<String> appendChar = s ->
                        result.add("" + s.charAt(rnd.nextInt(s.length())));
            appendChar.accept("0123456789");
            
            while (result.size() < len)
                appendChar.accept(all);
            Collections.shuffle(result, rnd);
            String str = String.join("", result);
            
            PreparedStatement ps = conn.prepareStatement("insert into serial(value,setid) values(?,?)"); 
            ps.setString(1, str);
            ps.setInt(2, id); 
            
            ps.executeUpdate();
            ps.close();
            
          }
     conn.close();
} catch (SQLException e1) {
    throw new IllegalStateException("Cannot connect the database!", e1);
}

    });
        
        //String number = numberOfSerial.getValue().toString();
        
        
        
        
        
        
        
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
