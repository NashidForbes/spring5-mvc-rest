package guru.springfamework.services.interfaces;

import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO getVendorById(Long id);

    VendorDTO saveVendorById(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendorById(Long id, VendorDTO vendorD);

    void deleteVendorById(Long id);
}
