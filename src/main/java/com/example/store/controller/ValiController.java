package com.example.store.controller;

import com.example.store.model.Vali;
import com.example.store.service.ValiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/valis")
public class ValiController {
    @Autowired
    private ValiService valiService;

    // Hiển thị trang danh sách vali
    @GetMapping("")
    public String showValiList(Model model) {
        List<Vali> valis = valiService.getAllValis();
        model.addAttribute("valis", valis);
        return "valis";
    }

    // API endpoint để lấy tất cả vali
    @GetMapping("/api")
    @ResponseBody
    public List<Vali> getAllValisApi() {
        return valiService.getAllValis();
    }

    // API endpoint để lấy vali theo id
    @GetMapping("/api/{id}")
    @ResponseBody
    public Vali getValiById(@PathVariable Long id) {
        return valiService.getValiById(id);
    }

    // Lọc vali theo thương hiệu
    @GetMapping("/brand/{brand}")
    public String getValisByBrand(@PathVariable String brand, Model model) {
        List<Vali> valis = valiService.getValisByBrand(brand);
        model.addAttribute("valis", valis);
        model.addAttribute("currentBrand", brand);
        return "valis";
    }

    // Lọc vali theo khoảng giá
    @GetMapping("/price")
    public String getValisByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            Model model) {
        List<Vali> valis = valiService.getValisByPriceRange(minPrice, maxPrice);
        model.addAttribute("valis", valis);
        return "valis";
    }

    // Hiển thị trang chi tiết vali
    @GetMapping("/{id}")
    public String showValiDetail(@PathVariable Long id, Model model) {
        Vali vali = valiService.getValiById(id);
        model.addAttribute("vali", vali);
        return "vali-detail";
    }
}