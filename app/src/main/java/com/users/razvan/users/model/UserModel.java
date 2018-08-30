package com.users.razvan.users.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private static final long serialVersionUID = -2285133688468747694L;

    private int id;
    private String name;
    private String email;
    private AddressModel address;
    private String phone;
    private String website;
    private CompanyModel company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        if (id != userModel.id) return false;
        if (name != null ? !name.equals(userModel.name) : userModel.name != null) return false;
        if (email != null ? !email.equals(userModel.email) : userModel.email != null) return false;
        if (address != null ? !address.equals(userModel.address) : userModel.address != null)
            return false;
        if (phone != null ? !phone.equals(userModel.phone) : userModel.phone != null) return false;
        if (website != null ? !website.equals(userModel.website) : userModel.website != null)
            return false;
        return company != null ? company.equals(userModel.company) : userModel.company == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }
}
