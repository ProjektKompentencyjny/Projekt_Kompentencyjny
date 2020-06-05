module program {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.fasterxml.classmate;


    opens database to org.hibernate.orm.core;
    opens program to javafx.fxml;


    exports program;
}