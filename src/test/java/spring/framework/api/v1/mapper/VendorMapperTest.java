package spring.framework.api.v1.mapper;

import org.junit.jupiter.api.Test;
import spring.framework.api.v1.model.VendorDTO;
import spring.framework.domain.Vendor;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {

    public static final String NAME = "Sergey";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(NAME, vendorDTO.getName());
    }

}