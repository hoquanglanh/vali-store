package com.example.store.repository;
import com.example.store.model.Vali;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ValiRepository extends JpaRepository<Vali, Long> {
    // Tìm vali theo thương hiệu
    List<Vali> findByBrand(String brand);

    // Tìm vali theo khoảng giá
    List<Vali> findByPriceBetween(double minPrice, double maxPrice);

    // Tìm vali theo size
    List<Vali> findBySize(String size);

    // Tìm vali theo chất liệu
    List<Vali> findByMaterial(String material);

    // Custom query để tìm vali bán chạy nhất
    @Query("SELECT v FROM Vali v ORDER BY v.quantity DESC LIMIT 10")
    List<Vali> findTopSelling();
}