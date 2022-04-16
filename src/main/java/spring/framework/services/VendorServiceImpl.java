package spring.framework.services;

import org.springframework.stereotype.Service;
import spring.framework.api.v1.mapper.VendorMapper;
import spring.framework.api.v1.model.VendorDTO;
import spring.framework.api.v1.model.VendorListDTO;
import spring.framework.controllers.v1.VendorController;
import spring.framework.domain.Vendor;
import spring.framework.exceptions.ResourceNotFoundException;
import spring.framework.repositories.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList()));
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {

        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);

        returnDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));

        return returnDTO;
    }

    private VendorDTO savedAndReturnDTO(Vendor customer) {

        Vendor savedVendor = vendorRepository.save(customer);

        VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);

        returnDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));

        return returnDTO;
    }

    @Override
    public VendorDTO saveVendorByVendorDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);

        return savedAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }
            return savedAndReturnDTO(vendor);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
