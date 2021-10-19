package com.tma.sample.coffeeshop.restController;

import com.tma.sample.coffeeshop.dto.StoreDTO;
import com.tma.sample.coffeeshop.model.Store;
import com.tma.sample.coffeeshop.repository.AddressRepository;
import com.tma.sample.coffeeshop.repository.StoreRepository;
import com.tma.sample.coffeeshop.repository.WardReposiroty;
import com.tma.sample.coffeeshop.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreRestController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private WardReposiroty wardReposiroty;

    @GetMapping
    public List<StoreDTO> storeList(){
        return storeService.findAll();
    }

    @GetMapping("/{id}")
    public StoreDTO viewStoreDetail(@PathVariable long id){
        StoreDTO storeDTO = storeService.viewStoreDetail(id);
        return storeDTO;
    }

    @PostMapping
    public void saveStore(@RequestBody StoreDTO storeDTO,@RequestParam("addressId") long addressId){
        storeService.save(storeDTO,addressId);
    }

    @PutMapping("/{id}")
    public void editStore(@RequestBody StoreDTO storeDTO
            ,@PathVariable("id") long storeId
            ,@RequestParam("addressId") long addressId){
        storeService.edit(storeId,storeDTO,addressId);
    }

    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable long id){
        storeService.delete(id);
    }
}
