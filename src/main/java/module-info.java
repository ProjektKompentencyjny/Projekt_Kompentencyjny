module program {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.fasterxml.classmate;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens database to org.hibernate.orm.core, javafx.base;
    opens program to javafx.fxml,org.hibernate.orm.core;
    opens program.administrator to javafx.fxml,org.hibernate.orm.core;
    opens program.accountant to javafx.fxml;
    opens program.usualUser to javafx.fxml;

    exports program;
    exports program.administrator;
}