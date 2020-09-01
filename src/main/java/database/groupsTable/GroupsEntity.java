package database.groupsTable;

import database.subcategoriesTable.SubcategoriesEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groups")
public class GroupsEntity implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer groupId;

    @Column(name= "Name")
    private String groupName;

    @Column(name="Description")
    private String groupDescription;

    @Column(name="rate")
    private Double rateGroup;

    @OneToOne
    @JoinColumn(name = "SubcategoryID")
    private SubcategoriesEntity subCatId;

    public GroupsEntity(){}

    public GroupsEntity(Integer groupId, String groupName, String groupDescription, Double rateGroup, SubcategoriesEntity subCatId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.rateGroup = rateGroup;
        this.subCatId = subCatId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Double getRateGroup() {
        return rateGroup;
    }

    public void setRateGroup(Double rateGroup) {
        this.rateGroup = rateGroup;
    }

    public SubcategoriesEntity getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(SubcategoriesEntity subCatId) {
        this.subCatId = subCatId;
    }

    @Override
    public String toString() {
        return "ID:" + groupId + " Nazwa:" + groupDescription + " Procenty:" + rateGroup;
    }
}
