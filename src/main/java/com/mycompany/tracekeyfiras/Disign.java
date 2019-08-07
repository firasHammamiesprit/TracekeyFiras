/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tracekeyfiras;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author ASUS
 */
public class Disign {
         
    public Disign(){
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
      
              
        String name = serialName.getValue();
        //String number = numberOfSerial.getValue().toString();
        
        
        
        
        
        
        
        layout.setMargin(true);
        layout.setSpacing(true);
        /*}*/
}
}