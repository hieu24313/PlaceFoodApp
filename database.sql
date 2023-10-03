CREATE DATABASE  IF NOT EXISTS `springfoodplacesweb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `springfoodplacesweb`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: springfoodplacesweb
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories_food`
--

DROP TABLE IF EXISTS `categories_food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories_food` (
  `categoryfood_id` int NOT NULL AUTO_INCREMENT,
  `categoryname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`categoryfood_id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `categories_food_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories_food`
--

LOCK TABLES `categories_food` WRITE;
/*!40000 ALTER TABLE `categories_food` DISABLE KEYS */;
INSERT INTO `categories_food` VALUES (16,'Äáº·c sáº£n Äá»a phÆ°Æ¡ng',25,0),(17,'Signature Food',25,1),(18,'Warm Food',25,1),(19,'Signature Food',19,1),(20,'Warm Food',19,1),(21,'Snack',19,1),(22,'Dessert',20,1),(23,'Signature Food',20,1),(24,'Signature Food',20,0),(25,'Juice',20,1),(26,'Juice',21,1),(27,'Signature Food',21,1),(28,'Pot',21,1),(29,'Healthy Food',23,1),(30,'Diet Food',23,1),(31,'Juice',23,1),(32,'Dessert',24,1),(33,'Signature Food',24,1),(34,'Rice Bowl',24,1),(35,'Cake',25,1),(36,'Tea',25,1),(37,'Cake',27,1),(38,'Signature Food',27,1),(39,'Ice Cream',27,1),(40,'Warm Food',28,1),(41,'Signature Food',28,1),(42,'Signature Food',29,1),(43,'Ice Cream',29,1),(44,'Pot',29,1),(45,'Dessert',31,1),(46,'Juice',31,1),(47,'Cake',32,1),(48,'Ice Cream',32,1),(49,'Salad',33,1),(50,'Signature Food',33,1),(51,'Warm Food',33,1),(52,'ChiÃªn',35,0);
/*!40000 ALTER TABLE `categories_food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatmessages`
--

DROP TABLE IF EXISTS `chatmessages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatmessages` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int DEFAULT NULL,
  `receiver_id` int DEFAULT NULL,
  `message_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`message_id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `chatmessages_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `chatmessages_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatmessages`
--

LOCK TABLES `chatmessages` WRITE;
/*!40000 ALTER TABLE `chatmessages` DISABLE KEYS */;
/*!40000 ALTER TABLE `chatmessages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combo`
--

DROP TABLE IF EXISTS `combo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo` (
  `combo_id` int NOT NULL AUTO_INCREMENT,
  `combo_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `price_combo` decimal(10,2) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`combo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combo`
--

LOCK TABLES `combo` WRITE;
/*!40000 ALTER TABLE `combo` DISABLE KEYS */;
/*!40000 ALTER TABLE `combo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combo_fooditems`
--

DROP TABLE IF EXISTS `combo_fooditems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo_fooditems` (
  `combo_fooditems_id` int NOT NULL AUTO_INCREMENT,
  `food_id` int DEFAULT NULL,
  `combo_id` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`combo_fooditems_id`),
  KEY `food_id` (`food_id`),
  KEY `combo_id` (`combo_id`),
  CONSTRAINT `combo_fooditems_ibfk_1` FOREIGN KEY (`food_id`) REFERENCES `fooditems` (`food_id`),
  CONSTRAINT `combo_fooditems_ibfk_2` FOREIGN KEY (`combo_id`) REFERENCES `combo` (`combo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combo_fooditems`
--

LOCK TABLES `combo_fooditems` WRITE;
/*!40000 ALTER TABLE `combo_fooditems` DISABLE KEYS */;
/*!40000 ALTER TABLE `combo_fooditems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  `food_id` int DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`comment_id`),
  KEY `user_id` (`user_id`),
  KEY `restaurant_id` (`restaurant_id`),
  KEY `food_id` (`food_id`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`),
  CONSTRAINT `comments_ibfk_3` FOREIGN KEY (`food_id`) REFERENCES `fooditems` (`food_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (6,35,33,64,'Nghỉ bán dùm',NULL,'2023-09-10 11:30:31',NULL,1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `follow_id` int NOT NULL AUTO_INCREMENT,
  `user_id_index` int DEFAULT NULL,
  `restaurant_id_index` int DEFAULT NULL,
  PRIMARY KEY (`follow_id`),
  KEY `restaurant_id` (`restaurant_id_index`),
  KEY `user_id` (`user_id_index`),
  CONSTRAINT `restaurant_ibfk_1` FOREIGN KEY (`restaurant_id_index`) REFERENCES `restaurants` (`restaurant_id`),
  CONSTRAINT `user_id_ibfk_1` FOREIGN KEY (`user_id_index`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (1,3,19),(3,4,19),(8,3,20);
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fooditems`
--

DROP TABLE IF EXISTS `fooditems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fooditems` (
  `food_id` int NOT NULL AUTO_INCREMENT,
  `food_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `categoryfood_id` int DEFAULT NULL,
  `shelflife_id` int DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '1',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`food_id`),
  KEY `categoryfood_id` (`categoryfood_id`),
  KEY `shelflife_id` (`shelflife_id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `fooditems_ibfk_1` FOREIGN KEY (`categoryfood_id`) REFERENCES `categories_food` (`categoryfood_id`),
  CONSTRAINT `fooditems_ibfk_2` FOREIGN KEY (`shelflife_id`) REFERENCES `shelf_life` (`shelflife_id`),
  CONSTRAINT `fooditems_ibfk_3` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fooditems`
--

LOCK TABLES `fooditems` WRITE;
/*!40000 ALTER TABLE `fooditems` DISABLE KEYS */;
INSERT INTO `fooditems` VALUES (20,'Bún Đậu Mắm Tôm','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694314215/qo5jy2k43kv54erueztz.jpg',20,10,19,150000.00,1,'Bún đậu ăn 1 mẹt tính tiền 2 mẹt',1),(21,'Bún Bò Huế','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694314303/dhvqkprmzalwetvvqakw.jpg',20,11,19,50000.00,1,'Bún Bò Huế hương vị Sài Thành',1),(22,'Trà Sữa Matcha','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694314345/zzg5fzkgwlbatwfkpsp5.jpg',19,10,19,25000.00,1,'Trà sữa matcha con giáp thứ 13',1),(23,'Snack Tá Lả','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694314401/opbdyyia2mtizkljjnkj.jpg',21,11,19,10000.00,1,'Chỉ từ 10000 với hơn 20 loại snack',1),(24,'Bột Chiên Xù','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694314717/gxw82tx1j3yzczrynq3h.jpg',23,12,20,30000.00,1,'Bột Chiên Xù Lăn Cá Lóc',1),(25,'Bánh Mỳ Chả Cá','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694314808/yxxsepvybn5yz0h1nf30.jpg',23,13,20,15000.00,1,'Thương hiệu chả cá Má Hải từ Phú Quốc',1),(26,'Trà Đào','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694314797/jrj9uycebio2bkdzdlop.jpg',25,12,20,27000.00,1,'Đào nhập khẩu từ siêu thị Coopmart',1),(27,'Kem Ký 7 Màu','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694314909/m4f4sxewktyfpsv9oau1.jpg',22,13,20,40000.00,1,'Chọn màu phong thủy theo yêu cầu của khách',1),(28,'Trà Trái Cây Nhiệt Đới','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694315148/mztpinikf91bjgeyt0ha.jpg',26,15,21,26000.00,1,'Hương vị trái cây từ Đồng Tháp ngây ngất lòng người',1),(29,'Cá Lóc Kho Tộ','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694315248/cqsoveysn9tuxahjuddx.jpg',27,14,21,52000.00,1,'Cá được chính tay chị chủ bắt dưới ruộng mang lên',1),(30,'Lẫu Chả Cá Dưa Chuột','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694315361/o0zma7uqfwkgwzpgnkfr.jpg',28,14,21,70000.00,1,'Ấm lòng đoàn viên ngày Tết',1),(31,'Cơm Trộn Huỳnh Như','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694315346/wtgfdvwkn8p1saokht56.jpg',27,14,21,35000.00,1,'Công thức gia truyền 7 đời',1),(32,'Cánh Gà Chiên Mắm Tỏi','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694315856/yovywxgiymkps6uydqyy.jpg',30,16,23,69000.00,1,'Gà Việt Nam được nuôi theo nền công nghiệp Mỹ',1),(33,'Salad Bò Kobe','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694315909/kyuvcp9xqdipzu0dzpxa.jpg',30,17,23,90000.00,1,'Bò Kobe thơm ngon',1),(34,'Cháo Yến Mạch','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694315949/eykdcocfkrrcbuufdju5.jpg',30,17,23,44000.00,1,'Yến Mạch từ Ý',1),(35,'Trà Hoa Hồng','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694316004/ym3vxuelv1zcrnbwyi5x.jpg',31,17,23,10000.00,1,'Hoa Hồng Hungary',1),(36,'Cơm Chiên Dương Châu','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694316567/adek91cyl2exumpwlzvj.jpg',34,18,24,25000.00,1,'Cơm Tấm hương vị thành phố',1),(37,'Cơm Trộn Hàn Quốc','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694316619/le2oa9mug6cvjrduev11.jpg',34,19,24,45000.00,1,'Cơm Trộn được đầu bếp có chứng chỉ ở Việt Nam trộn',1),(38,'Bánh Tiramisu','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694316678/qjofabzah2f3xonzdoew.jpg',32,18,24,36000.00,1,'Bánh Tiramisu nhập khẩu chớ không phải của quán tự làm',1),(39,'Tôm Hùm Đại Dương','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694316786/u56kfiybphggzi4vsxom.jpg',33,18,24,500000.00,1,'Tôm Hùm dành cho giới quý tộc thượng lưu',1),(40,'Trà Chanh Mật Ong','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694316989/sygytm02szfawfwbd3ql.jpg',36,21,25,25000.00,1,'Mật ong nguyên chất từ rừng U Minh giải nhiệt mùa hè',0),(41,'Bánh Canh Cua Huỳnh Đế','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694317134/ihcz3zw1mvziapjkjwjp.jpg',17,20,25,100000.00,1,'Cua Huỳnh Đế được nuôi ngoài tự nhiên chắc thịt',1),(42,'Hủ Tiếu Trộn Bò','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694317460/igbkuthwbfzf9bgx8y2h.jpg',18,22,25,45000.00,1,'Hơi mặn không ăn được cũng ráng ăn ha',1),(43,'Màn Thầu Trung Hoa','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694317591/cruaz2ztgzw99taq7ypk.jpg',17,21,25,30000.00,1,'Hơi khô do bảo quản chưa tốt',1),(44,'Bánh Dâu Trộn Thanh Long','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694317679/cd5ijf9yj59regqvpvjf.jpg',35,20,25,49000.00,1,'Như tên gọi là sự harmony giữa 2 loại trái cây',0),(45,'Bánh Trung Thu Thập Cẩm','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694317956/dve5z00s0qdxijkbwkcv.jpg',37,23,27,50000.00,1,'Bánh làm bằng quy trình thủ công 100%',0),(46,'Bánh Trung Thu Trứng Muối','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694317991/ruk9hkvctkukefdyekuv.jpg',37,23,27,40000.00,1,'Trứng Muối hàng tuyển chất lượng cao',0),(47,'Kem Dưa Hấu','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318049/gmacqxw2aivgmylybt52.jpg',39,24,27,19000.00,1,'Dưa hấu nhập khẩu từ Mai An Tiêm',1),(48,'Cá Viên Chiên Nước Mắm','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318091/vyctixi7v9nspd6vmyrp.jpg',38,24,27,62000.00,1,'Nước mắm nguyên chất Phú Quốc chất lượng',1),(49,'Bánh Mỳ Ốp La 2 Trứng','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318312/d2ziq2ozlpissjwbfbdl.jpg',41,25,28,35000.00,1,'Ốp la theo yêu cầu của khách',1),(50,'Ếch Chiên Giòn','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318390/vqc7tbxeyyp9kbhcpxd0.jpg',40,25,28,44000.00,1,'Ếch hàng tuyển',1),(51,'Chim Cút Chiên Bơ','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318430/cyizejrq9k2wwh8ohgtp.jpg',41,26,28,35000.00,1,'Bơ béo ăn cùng chim cút cực đã pepsi ơi',1),(52,'Kem Mâm Xôi Dừa Khô','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318565/qznj37ibtyhufoymnh3n.jpg',43,27,29,22000.00,1,'Đóng băng mùa đông cùng mâm xôi',1),(53,'Lẫu Gà Lá Chanh','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318619/wflvngpl9hl8y6cxwp6j.jpg',44,27,29,58000.00,1,'Lá chanh miễn phí ăn ít lấy ít, nhiều lấy nhiều',1),(54,'Chè Dưỡng Nhan','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318657/iaj5vm06xeno82fbyi70.jpg',42,28,29,25000.00,1,'Không cần phẫu thuật thẩm mĩ',1),(55,'Sinh Tố Thốt Nốt','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318844/tkdmlkl1bxiiusq12lxl.jpg',45,30,31,70000.00,1,'Thốt Nốt ngọt nước',1),(56,'Soda Chanh','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318887/sytdhvm0qsvgbhivqatt.jpg',46,29,31,50000.00,1,'Chanh chua cân nhắc trước khi dùng',1),(57,'Trà Sữa Trân Châu Đường Thốt Nốt','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694318940/gzp2wl6a1zeyf9kiin4c.jpg',46,29,31,51000.00,1,'Vâng là đường thốt nốt không phải đường đen',1),(58,'Bánh Kem Matcha','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694319168/q06kd0opyk2kyivbvzbw.jpg',47,31,32,78000.00,1,'Matcha chất lượng từ Tây Nguyên',1),(59,'Kem Dưa Lưới','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694319213/pxoswa14hujvsmp7dzd0.jpg',48,31,32,14000.00,1,'Dưa Lưới nhập khẩu EU',1),(60,'Bánh Kem Thạch Dừa','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694319264/vimdu5eu0iadyxplywiq.jpg',47,32,32,66000.00,1,'Bánh Kem từ bột rau câu thử nghiệm, cân nhắc trước khi mua',1),(61,'Tré Trộn','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694319452/u1nezswkrfilrs5zgm0t.jpg',50,34,33,150000.00,1,'Tré trộn mận xoài cóc ổi',1),(62,'Gỏi tôm tái chanh','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694319501/zv1wmc9t3y2xvx1cjh5u.jpg',49,33,33,37000.00,1,'Hết biết mô tả gì, ăn đi rồi biết',1),(63,'Vịt Quay Bắc Kinh','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694319548/rsjiwzeovkrhwzgdhm0w.jpg',51,33,33,92000.00,1,'Công nghệ lò quay từ Trung Hoa',1),(64,'Gà Tiềm Thuốc Bắc','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694319609/eiglfitph5i3gjm7ts59.jpg',51,34,33,200000.00,1,'Thuốc được bốc từ các thầy lang uy tín thế giới',1),(65,'Salad Mít Trộn','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694319685/popflsbi4cjzmwdat9b1.jpg',49,33,33,55000.00,1,'Mít nhà trồng cùng nước sốt đậm vị khuấy động vị giác',1),(66,'testdemo','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694397340/aihhn7nplcg4bm5duya9.png',52,35,35,1111.00,1,'1111',0);
/*!40000 ALTER TABLE `fooditems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  `notification_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `notification_date` date DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`notification_id`),
  KEY `user_id` (`user_id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `notifications_ibfk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `promotion_id` int NOT NULL AUTO_INCREMENT,
  `promotion_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `price_promotion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `promotion_type_id` int DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`promotion_id`),
  KEY `promotion_type_id` (`promotion_type_id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `promotion_ibfk_1` FOREIGN KEY (`promotion_type_id`) REFERENCES `promotion_type` (`promotion_type_id`),
  CONSTRAINT `promotion_ibfk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` VALUES (1,'Trung thu',NULL,NULL,NULL,NULL,NULL,1),(2,'Tết nguyên đán',NULL,NULL,NULL,NULL,null,1),(3,'111',NULL,NULL,NULL,NULL,NULL,1),(4,'222',NULL,NULL,NULL,NULL,NULL,1),(5,'333',NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion_fooditems`
--

DROP TABLE IF EXISTS `promotion_fooditems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion_fooditems` (
  `promotion_fooditems_id` int NOT NULL AUTO_INCREMENT,
  `food_id` int DEFAULT NULL,
  `promotion_id` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`promotion_fooditems_id`),
  KEY `food_id` (`food_id`),
  KEY `promotion_id` (`promotion_id`),
  CONSTRAINT `promotion_fooditems_ibfk_1` FOREIGN KEY (`food_id`) REFERENCES `fooditems` (`food_id`),
  CONSTRAINT `promotion_fooditems_ibfk_2` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`promotion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion_fooditems`
--

LOCK TABLES `promotion_fooditems` WRITE;
/*!40000 ALTER TABLE `promotion_fooditems` DISABLE KEYS */;
INSERT INTO `promotion_fooditems` VALUES (1,66,1,1),(2,65,1,1),(3,66,2,1),(4,65,2,1),(5,64,1,1),(6,64,2,1),(7,64,3,1);
/*!40000 ALTER TABLE `promotion_fooditems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion_type`
--

DROP TABLE IF EXISTS `promotion_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion_type` (
  `promotion_type_id` int NOT NULL AUTO_INCREMENT,
  `promotion_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`promotion_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion_type`
--

LOCK TABLES `promotion_type` WRITE;
/*!40000 ALTER TABLE `promotion_type` DISABLE KEYS */;
INSERT INTO `promotion_type` VALUES (1,'Theo %',1),(2,'Theo Giá Tiền',1);
/*!40000 ALTER TABLE `promotion_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_detail`
--

DROP TABLE IF EXISTS `receipt_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_detail` (
  `receiptdetail_id` int NOT NULL AUTO_INCREMENT,
  `fooditem_id` int DEFAULT NULL,
  `receipt_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unitPrice` decimal(10,2) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`receiptdetail_id`),
  KEY `fooditem_id` (`fooditem_id`),
  KEY `receipt_id` (`receipt_id`),
  CONSTRAINT `receipt_detail_ibfk_1` FOREIGN KEY (`fooditem_id`) REFERENCES `fooditems` (`food_id`),
  CONSTRAINT `receipt_detail_ibfk_2` FOREIGN KEY (`receipt_id`) REFERENCES `receipts` (`receipt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_detail`
--

LOCK TABLES `receipt_detail` WRITE;
/*!40000 ALTER TABLE `receipt_detail` DISABLE KEYS */;
INSERT INTO `receipt_detail` VALUES (19,57,10,1,51000.00,51000.00),(20,58,10,3,78000.00,234000.00),(21,60,10,1,66000.00,66000.00),(22,61,10,2,150000.00,300000.00),(23,51,11,1,35000.00,35000.00),(24,53,11,1,58000.00,58000.00),(25,56,11,5,50000.00,250000.00),(26,59,11,10,14000.00,140000.00),(27,46,12,1,40000.00,40000.00),(28,66,12,4,1111.00,4444.00),(29,64,13,2,200000.00,400000.00),(30,65,13,3,55000.00,165000.00),(31,66,13,3,1111.00,3333.00),(32,61,14,2,150000.00,300000.00),(33,62,14,4,37000.00,148000.00),(34,61,15,1,150000.00,150000.00),(35,62,15,3,37000.00,111000.00),(36,65,15,2,55000.00,110000.00),(37,64,16,2,200000.00,400000.00),(38,65,16,2,55000.00,110000.00),(39,62,17,3,37000.00,111000.00),(40,65,18,4,55000.00,220000.00),(41,66,19,7,1111.00,7777.00),(42,63,20,3,92000.00,276000.00),(43,64,20,3,200000.00,600000.00),(44,65,20,3,55000.00,165000.00),(45,61,21,4,150000.00,600000.00),(46,62,21,4,37000.00,148000.00),(47,52,22,6,22000.00,132000.00),(48,53,22,5,58000.00,290000.00),(49,61,23,4,150000.00,600000.00),(50,62,23,6,37000.00,222000.00),(51,63,24,3,92000.00,276000.00),(52,64,24,3,200000.00,600000.00),(53,65,24,4,55000.00,220000.00),(54,52,25,6,22000.00,132000.00),(55,59,26,3,14000.00,42000.00),(56,58,27,4,78000.00,312000.00),(57,59,27,2,14000.00,28000.00),(58,62,27,14,37000.00,518000.00),(59,58,29,3,78000.00,234000.00),(60,59,29,4,14000.00,56000.00),(61,39,30,19,500000.00,9500000.00),(62,59,30,24,14000.00,336000.00),(63,30,31,1,70000.00,70000.00),(64,51,31,1,35000.00,35000.00),(65,52,31,10,22000.00,220000.00),(66,53,31,22,58000.00,1276000.00);
/*!40000 ALTER TABLE `receipt_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_status`
--

DROP TABLE IF EXISTS `receipt_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_status` (
  `status_receipt_id` int NOT NULL AUTO_INCREMENT,
  `status_receipt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`status_receipt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_status`
--

LOCK TABLES `receipt_status` WRITE;
/*!40000 ALTER TABLE `receipt_status` DISABLE KEYS */;
INSERT INTO `receipt_status` VALUES (1,'Chưa xác nhận',1),(2,'Đã Xác nhận',1);
/*!40000 ALTER TABLE `receipt_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipts`
--

DROP TABLE IF EXISTS `receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipts` (
  `receipt_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `receipt_date` datetime DEFAULT NULL,
  `total_payment` decimal(10,2) DEFAULT NULL,
  `status_receipt_id` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`receipt_id`),
  KEY `user_id` (`user_id`),
  KEY `status_receipt_id` (`status_receipt_id`),
  CONSTRAINT `receipts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `status_receipt_id_ibfk_1` FOREIGN KEY (`status_receipt_id`) REFERENCES `receipt_status` (`status_receipt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipts`
--

LOCK TABLES `receipts` WRITE;
/*!40000 ALTER TABLE `receipts` DISABLE KEYS */;
INSERT INTO `receipts` VALUES (10,3,'2023-09-10 11:24:05',651000.00,2,1),(11,30,'2023-09-10 11:25:28',483000.00,2,1),(12,3,'2023-09-11 08:56:15',44444.00,2,1),(13,1,'2023-09-12 16:44:28',568333.00,2,1),(14,39,'2023-09-12 16:47:02',448000.00,2,1),(15,3,'2023-09-12 19:54:16',371000.00,2,1),(16,3,'2023-09-12 19:55:17',510000.00,2,1),(17,3,'2023-09-12 20:01:22',111000.00,2,1),(18,41,'2023-09-19 16:38:26',220000.00,2,1),(19,41,'2023-09-19 16:43:46',7777.00,2,1),(20,3,'2023-09-26 16:12:39',1041000.00,2,1),(21,3,'2023-09-26 17:04:51',748000.00,2,1),(22,NULL,'2023-09-26 17:14:37',422000.00,1,1),(23,NULL,'2023-09-26 17:17:28',822000.00,1,1),(24,NULL,'2023-09-26 17:18:55',1096000.00,1,1),(25,NULL,'2023-09-26 17:20:46',132000.00,1,1),(26,1,'2023-09-27 15:55:45',42000.00,2,1),(27,NULL,'2023-09-27 16:27:39',858000.00,1,1),(29,NULL,'2023-09-30 09:26:31',290000.00,1,1),(30,3,'2023-09-30 13:15:42',9836000.00,2,1),(31,3,'2023-10-01 16:27:13',1601000.00,2,1);
/*!40000 ALTER TABLE `receipts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_status`
--

DROP TABLE IF EXISTS `restaurant_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant_status` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `restaurant_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_status`
--

LOCK TABLES `restaurant_status` WRITE;
/*!40000 ALTER TABLE `restaurant_status` DISABLE KEYS */;
INSERT INTO `restaurant_status` VALUES (1,'Online',1),(2,'Offline',1),(3,'Banned',1);
/*!40000 ALTER TABLE `restaurant_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurants`
--

DROP TABLE IF EXISTS `restaurants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurants` (
  `restaurant_id` int NOT NULL AUTO_INCREMENT,
  `restaurant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `confirmation_status` tinyint(1) DEFAULT NULL,
  `map_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `restaurant_status` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`restaurant_id`),
  KEY `restaurant_status` (`restaurant_status`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `restaurants_ibfk_1` FOREIGN KEY (`restaurant_status`) REFERENCES `restaurant_status` (`status_id`),
  CONSTRAINT `restaurants_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurants`
--

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;
INSERT INTO `restaurants` VALUES (19,'A Mà','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694307377/m9ghsputjx4gtgxh7qcn.png','97 Võ Văn Tần, Quận 3, TP. HCM',1,'97 Võ Văn Tần, Quận 3, TP. HCM',3,2,1),(20,'Mười Khó','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694307463/hxh9n6vaufw1wztinqxg.png','27 Trần Quốc Thảo, Quận 3, TP. HCM',1,'27 Trần Quốc Thảo, Quận 3, TP. HCM',3,1,1),(21,'Bùi Viện','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694310757/my2c2nzq4v1cexr1snsk.png','158 Bùi Viện, Quận 1, TP. HCM',1,'158 Bùi Viện, Quận 1, TP. HCM',3,2,1),(22,'Landmark Reactjs','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694310933/tvqyrkqhzbvlbl1cgohx.png','720A Đ. Điện Biên Phủ, Quận Bình Thạnh, Đồng Tháp',0,'720A Đ. Điện Biên Phủ, Quận Bình Thạnh, Đồng Tháp',3,2,1),(23,'Honestly','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694311139/ffh4jffipolabfygemqk.png','65 Trần Xuân Soạn, Quận 7, TP. HCM',1,'65 Trần Xuân Soạn, Quận 7, TP. HCM',3,1,1),(24,'Anyway','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694311219/xknywvpqbh87rpnghwb4.png','702 Nguyễn Văn Linh, Quận 7, TP. HCM',1,'702 Nguyễn Văn Linh, Quận 7, TP. HCM',3,1,1),(25,'SpringMVC','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694311292/msz0kwhwuhl8hmqqiorv.png','Long Mỹ, Hậu Giang',1,'Long Mỹ, Hậu Giang',3,2,1),(26,'ReactNative','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694311327/xjxjb7kw5boymopdh5w6.png','Cai Lậy, Tiền Giang',0,'Cai Lậy, Tiền Giang',3,2,1),(27,'Dimensity 1080','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694311456/l4f1zwtlmkjhrk8l6hvb.png','Las Vegas, Nevada, New York',1,'Las Vegas, Nevada, New York',3,1,1),(28,'ZFlip3','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694311565/jr7oatfd1ryigxe3o0xf.png','Đường 30/4, Thủ Dầu Một, Bình Dương',1,'Đường 30/4, Thủ Dầu Một, Bình Dương',3,2,1),(29,'Cùng Học Lập Trình','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694311704/twpxmhub52b8bxovpmvo.png','Đường Huỳnh Thiên Lộc, Quận Tân Phú, TP. HCM',1,'Đường Huỳnh Thiên Lộc, Quận Tân Phú, TP. HCM',3,1,1),(30,'Lâu Đài Tình Ái','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694311783/aipfjzp3jni87fymjcod.png','KDC Nhơn Đức, Huyện Nhà Bè, TP. HCM',0,'KDC Nhơn Đức, Huyện Nhà Bè, TP. HCM',3,2,1),(31,'TwillioAPI','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694311856/xck8f1cpnff2mdxx1yya.png','KDC Nhơn Đức, Huyện Nhà Bè, TP. HCM',1,'KDC Nhơn Đức, Huyện Nhà Bè, TP. HCM',3,2,1),(32,'BeHappySis','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694312251/g63mjhqjhuclowxqkqtx.png','Khu đô thị Phú Mỹ Hưng, Quận 7, TP. HCM',1,'Khu đô thị Phú Mỹ Hưng, Quận 7, TP. HCM',33,2,1),(33,'Mike Music','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694312156/vjcnceejxa6uzvogzcja.png','20 Hồ Văn Huê, Quận Phú Nhuận, TP. HCM',1,'20 Hồ Văn Huê, Quận Phú Nhuận, TP. HCM',34,2,1),(34,'Hieu','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694312156/vjcnceejxa6uzvogzcja.png','371',1,'',3,2,0),(35,'testdemo','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694312156/vjcnceejxa6uzvogzcja.png','123',1,'',3,2,0),(36,'123','https://res.cloudinary.com/dhwuwy0to/image/upload/v1694312156/vjcnceejxa6uzvogzcja.png','123',0,NULL,41,2,1),(37,'123','https://res.cloudinary.com/dyfzuigha/image/upload/v1695892941/lleqk34473dviwhp5t45.jpg','123',0,NULL,3,2,1);
/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_Admin',1),(2,'ROLE_RestaurantManager',1),(3,'ROLE_User',1),(4,'ROLE_SupAdmin',1);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shelf_life`
--

DROP TABLE IF EXISTS `shelf_life`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shelf_life` (
  `shelflife_id` int NOT NULL AUTO_INCREMENT,
  `shelflife_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`shelflife_id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `shelf_life_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shelf_life`
--

LOCK TABLES `shelf_life` WRITE;
/*!40000 ALTER TABLE `shelf_life` DISABLE KEYS */;
INSERT INTO `shelf_life` VALUES (10,'Spring Festival','2023-09-01','2023-09-30',19,1),(11,'Summer Festival','2023-09-10','2023-10-10',19,1),(12,'Autumn Festival','2023-09-01','2023-09-21',20,1),(13,'Winter Festival','2023-10-10','2023-10-31',20,1),(14,'Tet Holiday','2024-01-01','2024-01-31',21,1),(15,'Merry Christmas','2023-12-01','2023-12-31',21,1),(16,'8/3 Holiday','2024-03-01','2024-03-10',23,1),(17,'Valentine','2024-02-14','2024-03-14',23,1),(18,'Golden Week','2024-04-29','2024-05-05',24,1),(19,'10/3','2024-03-01','2024-03-20',24,1),(20,'Spring Festival','2024-01-01','2024-03-31',25,1),(21,'Summer Festival','2024-04-01','2024-06-30',25,1),(22,'Autumn Festival','2024-07-01','2024-08-31',25,1),(23,'Trung Thu','2023-08-04','2023-10-10',27,1),(24,'Easter','2024-03-01','2024-03-31',27,1),(25,'Thanksgiving','2023-09-30','2023-10-31',28,1),(26,'Indepence Day','2023-09-02','2023-09-20',28,1),(27,'Winter Festival','2023-12-01','2023-12-01',29,1),(28,'Spring Festival','2024-02-02','2024-03-31',29,1),(29,'Spring Festival','2024-01-01','2024-02-01',31,1),(30,'Autumn Festival','2024-07-07','2024-08-08',31,1),(31,'Valentine','2023-12-14','2024-02-14',32,1),(32,'Golden Week','2024-04-29','2024-05-05',32,1),(33,'Summer Festival','2024-04-01','2024-05-02',33,1),(34,'Winter Festival','2023-09-30','2023-12-31',33,1),(35,'CÃ¡ÂºÂ£ ngÃÂ y','2023-01-01','2023-11-11',35,0),(36,'sadasd','2023-11-11','2023-12-12',35,0),(37,'time1','2023-10-01','2024-01-20',35,0);
/*!40000 ALTER TABLE `shelf_life` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phonenumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `otp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phonenumber_UNIQUE` (`phonenumber`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Nguyễn Minh','Hiếu','0359505026','hieu','$2a$10$CSDHLcIJV/hsMU4GgOl5L.5KtQSNUhhQTFL9q55xV4S1MhFd25uAe',4,'https://res.cloudinary.com/dyfzuigha/image/upload/v1695806482/qjjjflv5obsdlbfwgnwy.jpg','đasa',1,'d@123','1'),(3,'Trương Nguyễn Minh','Thái','0359505027','thai','$2a$10$10A4Ndpodu5rkCvw1IIB4e.FL8ny3W6wdxyHaGJFrjH03JXewIei.',2,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png','Gia Lai',1,'thai@gmail.com','1'),(4,'Trần Đăng ','Tuấn','0123','tuan','$2a$12$2DOmWbQRqqULw3PqmjacPO22EbCOgTLmrbW6FmWbv1/KzASdsnpnq',1,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png','HCM',1,NULL,'0'),(24,'Lê Thị Quỳnh','Như','01210','nhu','$2a$10$juE6Ysn8tpt1AApnPi8UvOzub2oqPV4C.N5vymEkS3/cuuSbV5z3K',2,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png','Đồng Tháp',1,'nhu.ltq@gmail.com','0'),(25,'Lê Hoài','Vũ','02222','vu','$2a$10$i1SN.uNDTb/s4gvhOkkjoO54265VEblv17Mz8nEadlkp.kX0YgReC',2,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png',NULL,1,'viet@gmail.com','0'),(30,'Mai Văn','Thành','0126','thanh','$2a$10$hU2eXi28feCNXviGcXPV4ekHBjF614drFuRYVvkfm3Px9iQ/uNWVO',2,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png','Hậu Giang',1,'thanh@iphonepromax.com','0'),(31,'Nguyễn Thị Phương','Anh','0129','anh','$2a$10$q9t0qJmJ1iJjiMBgJk6Bcey/p0KQq4AQ/4pfraFdQVaQ9YThpvPM2',2,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png','Đồng Nai',1,'tr@gmail.com','0'),(32,'Đỗ ','Việt','0156','viet','$2a$10$dsaeTLdjB52K0q.LC9zw.Oqjvf3PeVgBGmvLmi86tw22AcINbRBsi',2,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png','Quận Cam',1,'phuon@gmail.com','0'),(33,'Thiều Bảo','Trâm','0147','tram','$2a$10$P0B4BxUCQDuryifbMJ7XouLzmz.Mzrc2yje0mGBcUS2IsjHRAUi4i',2,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png','Nam Định',1,'tram.tb@gmail.com','0'),(34,'Mai Âm','Nhạc','0128','nhac','$2a$10$eFB9CJ1UlQf58UVVBXwMHeuwklMz7RG1E/nRq2XV/wXWuAPTcxs26',2,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png','Mỹ Tho',1,'mai.an@gmail.com','0'),(35,'Trương Nguyễn Minh','Thái',NULL,'2051052125thai@ou.edu.vn','$2a$10$uBIvAS/6AoFm4WcLvwkzCOx576IKKBllxAVr0KiTsINdIQ5lMkADC',3,'https://res.cloudinary.com/dhwuwy0to/image/upload/v1694390209/zzhhg64tabwsenwkaqte.png','null',1,'2051052125thai@ou.edu.vn','0'),(38,'Hieu','Nguyen','123','hieu123','$2a$10$ys3u9x73SGRUWAHO/OblXu/ZzINF2UVsO9SPuv4O3LPv3AsXdDIDe',3,'https://res.cloudinary.com/dyfzuigha/image/upload/v1694511752/pespwbgjafxp3bjskwwj.jpg','Vietnam',1,'hieu@123','0'),(39,'Nguyễn Minh','Hiếu','0123456','2051050138hieu@ou.edu.vn','$2a$10$wwV1G3vljQQ.uLEcAqQszunC19KTNLvR29YUul4wr4Xfv8gcdGZOO',3,'https://lh3.googleusercontent.com/a/ACg8ocKtKQ9uoE9k4i0Lre5zQsSs9XKzEX10UuI95kfXj9MW=s96-c','371 Nguyễn Kiệm',1,'2051050138hieu@ou.edu.vn','0'),(40,NULL,NULL,NULL,'123','$2a$10$RW6vXGn1V8W2wxdlqqZZu.LJ7ZfKCPC6mo6oiETehRoms5lD0afYO',3,NULL,NULL,1,NULL,'0'),(41,'nguyen','hieu','0359','nro881234@gmail.com','$2a$10$woIcXTC.PvCOkWutWFsbkulWbqbBVSS3K8NgCKjqQrV61z1SlOHUa',3,'https://lh3.googleusercontent.com/a/ACg8ocIc43dcJfBfOTxMnLDFekxwPG1zcitzYdaGLUmz6gAI=s96-c','Thái',1,'nro881234@gmail.com','0'),(42,'Nguyễn Minh','Hiếu',NULL,'hieu24313@gmail.com','$2a$10$dWeXsNlr73pTRB8C5I0mnu4MuILsjyBF1KLbENXAs1RDXt5QP40MO',3,'https://lh3.googleusercontent.com/a/ACg8ocK3CZDs05H0WemLDIxEqn2JzGbfe1bPwSbsnRLQnxDLcvA=s96-c',NULL,1,'hieu24313@gmail.com','0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-03 15:07:31
