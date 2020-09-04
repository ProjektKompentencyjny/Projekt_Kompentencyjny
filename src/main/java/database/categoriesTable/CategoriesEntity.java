package database.categoriesTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "categories")
public class CategoriesEntity implements Serializable {

    @Id
    @Column(name = "CAT_ID")
    private Integer catId;

    @Column(name = "Name")
    private String catName;

    @Column(name = "Description")
    private String catDescription;

    public CategoriesEntity(){}

    public CategoriesEntity(Integer catId, String catName, String catDescription) {
        this.catId = catId;
        this.catName = catName;
        this.catDescription = catDescription;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    @Override
    public String toString() {
        return "Id kategorii: "+ catId +" Nazwa: " + catDescription;
    }
}
