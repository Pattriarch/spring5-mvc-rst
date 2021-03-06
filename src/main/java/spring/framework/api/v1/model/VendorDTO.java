package spring.framework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDTO {
    @ApiModelProperty(name = "This is name value of vendor", required = true)
    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;
}
