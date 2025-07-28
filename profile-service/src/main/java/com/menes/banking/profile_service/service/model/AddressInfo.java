package com.menes.banking.profile_service.service.model;

import com.menes.banking.profile_service.repository.model.Address;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfo {

    private String additionalAddress;

    private String state;

    private String city;

    private String countryCode;

    private String zipCode;

    private String mailCode;

    private String addressStr;

    private String countryName;

    private String administrativeAreaLevel1;

    private String addressType;

    public static List<AddressInfo> from(List<Address> addresses) {
        return addresses.stream()
                .map(address -> AddressInfo.builder()
                        .additionalAddress(address.getAdditionalAddress())
                        .state(address.getState())
                        .city(address.getCity())
                        .countryCode(address.getCountryCode())
                        .zipCode(address.getZipCode())
                        .mailCode(address.getMailCode())
                        .addressType(address.getAddressType())
                        .addressStr(address.getAddressStr())
                        .countryName(address.getCountryName())
                        .administrativeAreaLevel1(address.getAdministrativeAreaLevel1())
                        .build())
                .collect(Collectors.toList());
    }
}