package database.subcategoriesTable;

import database.categoriesTable.CategoriesEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subcategories")
public class SubcategoriesEntity implements Serializable {

    @Id
    @Column(name = "SUB_ID")
    private Integer subCatId;

    @Column(name = "Name")
    private String subCatName;

    @Column(name = "Description")
    private String subCatDescription;

    @OneToOne
    @JoinColumn(name="CategoryID")
    private CategoriesEntity categoryId;

    public SubcategoriesEntity(){}

    public SubcategoriesEntity(Integer subCatId, String subCatName, String subCatDescription, CategoriesEntity categoriesEntity) {
        this.subCatId = subCatId;
        this.subCatName = subCatName;
        this.subCatDescription = subCatDescription;
        this.categoryId = categoriesEntity;
    }

    public Integer getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(Integer subCatId) {
        this.subCatId = subCatId;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getSubCatDescription() {
        return subCatDescription;
    }

    public void setSubCatDescription(String subCatDescription) {
        this.subCatDescription = subCatDescription;
    }

    public CategoriesEntity getCategoriesEntity() {
        return categoryId;
    }

    public void setCategoriesEntity(CategoriesEntity categoriesEntity) {
        this.categoryId = categoriesEntity;
    }

    @Override
    public String toString() {
        return "ID: " + subCatId + "Nazwa: "+ subCatDescription;
    }
}
