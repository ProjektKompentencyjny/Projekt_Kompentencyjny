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
    requires java.desktop;
    requires javafx.swing;
    requires core;
    requires itextpdf;
    requires poi;
    requires poi.ooxml;


    opens database.usersTable to org.hibernate.orm.core, javafx.base;
    opens database.stocktakingItemsTable to org.hibernate.orm.core, javafx.base;
    opens database.stocktaking to org.hibernate.orm.core, javafx.base;
    opens database.amortizationTable to org.hibernate.orm.core, javafx.base;
    opens database.itemsTable to org.hibernate.orm.core, javafx.base;
    opens database.locationsTable to org.hibernate.orm.core, javafx.base;
    opens database.roomTable to org.hibernate.orm.core, javafx.base;
    opens database.itemsTableUsual to org.hibernate.orm.core, javafx.base;
    opens database.invoicesTable to org.hibernate.orm.core, javafx.base;
    opens database.itemsTableTemp to org.hibernate.orm.core, javafx.base;
    opens database.categoriesTable to org.hibernate.orm.core, javafx.base;
    opens database.groupsTable to org.hibernate.orm.core, javafx.base;
    opens database.subcategoriesTable to org.hibernate.orm.core, javafx.base;
    opens program to javafx.fxml,org.hibernate.orm.core;
    opens program.administrator to javafx.fxml,org.hibernate.orm.core;
    opens program.administrator.locations to javafx.fxml,org.hibernate.orm.core;
    opens program.administrator.Stocktaking to javafx.fxml,org.hibernate.orm.core;
    opens program.administrator.assortment to javafx.fxml,org.hibernate.orm.core;
    opens program.accountant.amortization to javafx.fxml, org.hibernate.orm.core;
    opens program.accountant to javafx.fxml;
    opens program.usualUser to javafx.fxml;


    exports program;
    exports program.administrator;
}