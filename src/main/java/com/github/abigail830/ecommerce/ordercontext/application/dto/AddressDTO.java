package com.github.abigail830.ecommerce.ordercontext.application.dto;

import com.github.abigail830.ecommerce.ordercontext.domain.order.model.Address;
import lombok.Value;

@Value
public class AddressDTO {

    private String province;
    private String city;
    private String detail;

    private AddressDTO(String province, String city, String detail) {
        this.province = province;
        this.city = city;
        this.detail = detail;
    }

    public static AddressDTO of(String province, String city, String detail) {
        return new AddressDTO(province, city, detail);
    }

    public static AddressDTO of(Address address) {
        return new AddressDTO(address.getProvince(), address.getCity(), address.getDetail());
    }

    public Address toAddress() {
        return Address.of(province, city, detail);
    }

}
