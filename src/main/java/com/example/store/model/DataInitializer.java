package com.example.store.model;

import com.example.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) { // Mở lại điều kiện
            Product product1 = new Product();
            product1.setName("Vali Samsonite");
            product1.setDescription("Vali cỡ lớn với khóa an toàn và bánh xe xoay 360 độ.");
            product1.setPrice(159.99);
            product1.setQuantity(15);
            product1.setImageUrl("public/images/vali1.jpg");

            Product product2 = new Product();
            product2.setName("Vali American Tourister");
            product2.setDescription("Vali xách tay tiện lợi, phù hợp cho chuyến đi ngắn.");
            product2.setPrice(89.99);
            product2.setQuantity(15);
            product2.setImageUrl("https://example.com/vali2.jpg");

            Product product3 = new Product();
            product3.setName("Vali Tumi");
            product3.setDescription("Vali cao cấp với chất liệu chống trầy xước.");
            product3.setPrice(199.99);
            product3.setQuantity(15);
            product3.setImageUrl("https://example.com/vali3.jpg");

            Product product4 = new Product();
            product4.setName("Balo Dell");
            product4.setDescription("Balo cho laptop với nhiều ngăn chứa đồ.");
            product4.setPrice(49.99);
            product4.setQuantity(15);
            product4.setImageUrl("https://example.com/balo1.jpg");

            Product product5 = new Product();
            product5.setName("Balo The North Face");
            product5.setDescription("Balo du lịch chống nước và siêu nhẹ.");
            product5.setPrice(89.99);
            product5.setQuantity(15);
            product5.setImageUrl("https://example.com/balo2.jpg");

            Product product6 = new Product();
            product6.setName("Balo Herschel");
            product6.setDescription("Balo thời trang phong cách trẻ trung.");
            product6.setPrice(69.99);
            product6.setQuantity(15);
            product6.setImageUrl("https://example.com/balo3.jpg");

            Product product7 = new Product();
            product7.setName("Túi Dior");
            product7.setDescription("Túi đựng mỹ phẩm tiện dụng và nhỏ gọn.");
            product7.setPrice(24.99);
            product7.setQuantity(15);
            product7.setImageUrl("https://example.com/phukien4.jpg");

            Product product8 = new Product();
            product8.setName("Túi Nike");
            product8.setDescription("Túi đeo chéo thể thao, phong cách năng động.");
            product8.setPrice(29.99);
            product8.setQuantity(15);
            product8.setImageUrl("https://example.com/phukien1.jpg");

            Product product9 = new Product();
            product9.setName("Túi Louis Vuitton");
            product9.setDescription("Túi xách du lịch cao cấp và sang trọng.");
            product9.setPrice(499.99);
            product9.setQuantity(15);
            product9.setImageUrl("https://example.com/phukien9.jpg");

            Product product10 = new Product();
            product10.setName("Túi Adidas");
            product10.setDescription("Túi du lịch thể thao, rộng rãi và chắc chắn.");
            product10.setPrice(39.99);
            product10.setQuantity(15);
            product10.setImageUrl("https://example.com/phukien7.jpg");

            Product product11 = new Product();
            product11.setName("Túi HP");
            product11.setDescription("Túi bảo vệ laptop với lớp đệm chống sốc.");
            product11.setPrice(34.99);
            product11.setQuantity(15);
            product11.setImageUrl("https://example.com/phukien2.jpg");

            Product product12 = new Product();
            product12.setName("Gối Cabeau");
            product12.setDescription("Gối cổ mềm mại, thoải mái cho mọi chuyến bay.");
            product12.setPrice(19.99);
            product12.setQuantity(20);
            product12.setImageUrl("https://example.com/phukien5.jpg");

            Product product13 = new Product();
            product13.setName("Dây Samsonite");
            product13.setDescription("Dây đeo vali bền chắc, dễ nhận diện hành lý.");
            product13.setPrice(9.99);
            product13.setQuantity(50);
            product13.setImageUrl("https://example.com/phukien3.jpg");

            Product product14 = new Product();
            product14.setName("Bộ khóa TSA Lock");
            product14.setDescription("Bộ khóa vali an toàn tiêu chuẩn TSA.");
            product14.setPrice(12.99);
            product14.setQuantity(50);
            product14.setImageUrl("https://example.com/phukien8.jpg");

            Product product15 = new Product();
            product15.setName("Bao trùm");
            product15.setDescription("Bao trùm vali trong suốt chống nước, chống bụi bẩn, chống xước.");
            product15.setPrice(14.99);
            product15.setQuantity(50);
            product15.setImageUrl("https://example.com/phukien6.jpg");

            productRepository.saveAll(Arrays.asList(
                    product1, product2, product3, product4, product5,
                    product6, product7, product8, product9, product10,
                    product11, product12, product13, product14, product15
            ));
        }
    }
}
