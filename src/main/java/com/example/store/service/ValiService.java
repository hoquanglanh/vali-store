package com.example.store.service;

import com.example.store.model.Vali;
import com.example.store.repository.ValiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValiService {
    @Autowired
    private ValiRepository valiRepository;

    // Lấy tất cả vali
    public List<Vali> getAllValis() {
        return valiRepository.findAll();
    }

    // Lấy vali theo ID
    public Vali getValiById(Long id) {
        return valiRepository.findById(id).orElse(null);
    }

    // Lấy vali theo thương hiệu
    public List<Vali> getValisByBrand(String brand) {
        return valiRepository.findByBrand(brand);
    }

    // Lấy vali theo khoảng giá
    public List<Vali> getValisByPriceRange(double minPrice, double maxPrice) {
        return valiRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // Lấy vali theo size
    public List<Vali> getValisBySize(String size) {
        return valiRepository.findBySize(size);
    }

    // Lấy vali theo chất liệu
    public List<Vali> getValisByMaterial(String material) {
        return valiRepository.findByMaterial(material);
    }

    // Lấy top vali bán chạy
    public List<Vali> getTopSellingValis() {
        return valiRepository.findTopSelling();
    }
}