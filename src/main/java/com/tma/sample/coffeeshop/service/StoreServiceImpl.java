package com.tma.sample.coffeeshop.service;

import com.tma.sample.coffeeshop.dto.ProductDTO;
import com.tma.sample.coffeeshop.dto.StoreDTO;
import com.tma.sample.coffeeshop.mapper.StoreMapper;
import com.tma.sample.coffeeshop.model.Address;
import com.tma.sample.coffeeshop.model.Store;
import com.tma.sample.coffeeshop.repository.AddressRepository;
import com.tma.sample.coffeeshop.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private AddressRepository addressRepository;

    private final StoreMapper storeMapper;

    public StoreServiceImpl(StoreMapper storeMapper) {
        this.storeMapper = storeMapper;
    }

    @Override
    public Page<StoreDTO> findAll(Pageable pageable) {
        return storeRepository.findAll(pageable).map(p -> storeMapper.map(p));
    }

    @Override
    public StoreDTO viewStoreDetail(long id) {
        Store store = storeRepository.findById(id).orElse(null);
        StoreDTO storeDTO = storeMapper.map(store);
        return storeDTO;
    }

    @Override
    public Store save(StoreDTO storeDTO, long addressId) {
        Store store = storeMapper.map(storeDTO);
        storeRepository.save(store);

        Address address = addressRepository.findById(addressId).orElse(null);
        address.setStore(storeRepository.findById(store.getId()).orElse(null));
        addressRepository.save(address);

        return store;
    }

    @Override
    public Store edit(long storeId, StoreDTO storeDTO, long addressId) {
        Store store = storeRepository.findById(storeId).orElse(null);
        if (store == null) return null;

        //find old address
        List<Address> addresses = addressRepository.findAllByStoreId(storeId);
        Address oldAddress;
        if (addresses.size() > 0) {
            oldAddress= addresses.get(0);
        } else {
            oldAddress = null;
        }

        //save the store
        store = storeMapper.map(storeDTO);
        store.setId(storeId);
        storeRepository.save(store);

        //set store for new address
        Address address = addressRepository.findById(addressId).orElse(null);
        address.setStore(storeRepository.findById(store.getId()).orElse(null));
        addressRepository.save(address);

        //remove store field of old address
        if (oldAddress != null) {
            oldAddress.setStore(null);
        }
        return store;
    }

    @Override
    public void delete(long storeId) {
        storeRepository.deleteById(storeId);
    }

}
