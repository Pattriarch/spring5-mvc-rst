package spring.framework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spring.framework.api.v1.model.VendorDTO;
import spring.framework.domain.Vendor;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDTO(Vendor customer);

    Vendor vendorDtoToVendor(VendorDTO customerDTO);
}
