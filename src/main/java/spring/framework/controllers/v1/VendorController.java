package spring.framework.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring.framework.api.v1.model.VendorDTO;
import spring.framework.api.v1.model.VendorListDTO;
import spring.framework.config.SwaggerConfig;
import spring.framework.domain.Vendor;
import spring.framework.services.VendorService;

@RestController
@RequestMapping(VendorController.BASE_URL)
@Api(tags = {SwaggerConfig.VENDOR_TAG})
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "This will get all vendors.", notes = "There are some notes about the API.")
    public VendorListDTO getAllVendors() {
        return vendorService.getAllVendors();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "This will create new vendor.", notes = "There are some notes about the API.")
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @DeleteMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "This will delete vendor by id.", notes = "There are some notes about the API.")
    public void deleteVendorById(@PathVariable Long vendorId) {
        vendorService.deleteVendorById(vendorId);
    }

    @GetMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "This will get vendor by id.", notes = "There are some notes about the API.")
    public VendorDTO getVendorById(@PathVariable Long vendorId) {
        return vendorService.getVendorById(vendorId);
    }

    @PatchMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "This will patch vendor by body and id.", notes = "There are some notes about the API.")
    public VendorDTO pathVendor(@PathVariable Long vendorId, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(vendorId, vendorDTO);
    }

    @PutMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "This will put vendor by body and id.", notes = "There are some notes about the API.")
    public VendorDTO putVendor(@PathVariable Long vendorId, @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorByVendorDTO(vendorId, vendorDTO);
    }
}
