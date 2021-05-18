package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import guru.springfamework.services.interfaces.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VendorServiceImpl implements VendorService {

    public final VendorMapper vendorMapper;
    public final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper,
                             VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + vendor.getId());
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }


    private VendorDTO saveVendor(Long id, Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        savedVendor.setId(id);
        savedVendor.setVendorUrl(vendor.getVendorUrl());
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        return vendorDTO;
    }


    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        VendorDTO savedVendor = saveVendor(vendor.getId(), vendor);
        return savedVendor;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    // set API URL
                    vendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + id);
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);

    }

    @Override
    public VendorDTO saveVendorById(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);
        VendorDTO savedVendorDTO = saveVendor(id, vendor);
        return savedVendorDTO;
    }

    @Override
    public VendorDTO patchVendorById(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }
            if (vendorDTO.getVendorUrl() != null) {
                vendor.setVendorUrl(vendorDTO.getVendorUrl());
            }
            return saveVendor(id, vendorMapper.vendorDTOToVendor(vendorDTO));


        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        log.info("Delete vendor id" + id);
        vendorRepository.deleteById(id);
    }
}
