# PlaceFoodApp
Đồ án nghành.
Công nghệ sử dụng: Java Spring MVC, ReactJs.
Database: Mysql
Hướng dẫn cài đặt
Project được thực hiện bằng Java Spring MVC và ReactJs
Số lượng thành viên: 1
Đầu tiên tiến hành cái đặt các trình IDE và biến môi trường bao gồm:
- Apache NetBeans IDE 16
- Visual Studio Code
- JDK 14
- Node package manager(NPM): hệ thống quản lý gói cho JavaScript để run phần ReactJs
- MySQL workbench 8.0 CE
Clone project từ github: https://github.com/hieu24313/PlaceFoodApp.git
Trong project gồm 2 phần là Java và ReactJs được chia ra thành 2 thư mục.
Thiết lập twilio key: 
Mở theo đường dẫn: ..\PlaceFoodApp\SpringFoodPlacesWeb\src\main\resources
Sau đó mở file config.properties copy nội dùng các key sau dấu “=” từ file key.txt bên cạnh thư mục proect và dán vào phần twillio key trong file config.
Ngoài ra mở file database.config từ đường dẫn : PlaceFoodApp\SpringFoodPlacesWeb\src\main\resources để thiết lập cấu hình database phù hợp như username và password của database.
Phần database 
Mở MySQL workbench 8.0 CE sau đó chạy phần code sql trong file database.sql để tạo cở sở dữ liệu.
Phần ReactJs
Sử dụng visual studio code mở thư mục có tên: SaleFoodPlacesWeb-ReactJs.
Chọn view -> terminal để mở màn hình terminal: gõ lệnh npm install để tải toàn bộ dependencies cần thiết về. Sau đó gõ npm strart để chạy project.
Phần Java
Sử dụng Apache NetBeans IDE 16 mở thư mục: SpringFoodPlacesWeb.
Sau đó build để tải các dependencies cần thiết về, sau khi đã tải xong toàn bộ dependencies thì run project.
Các user có sẵn trong cở sở dữ liệu:

Role	SupAdmin	Quản Trị Viên	Quản Lý Nhà Hàng	Người Dùng
Username	admin	tuan	hieu	nhu
Password	123	123	123	123

* Lưu ý quan trọng: 
Có một chức năng cần có key bên ngoài nhưng vì lý do bảo mật nên khi public project key sẽ tự động đổi dẫn đến hoạt động không chính xác, hiện tại chức năng đang bị là twilio - dịch vụ gửi sms. Vì vậy key sẽ được để trong file key.txt bên cạnh file project. Nếu key này vẫn không dùng được thì vui lòng liên hệ  2051050138hieu@ou.edu.vn để lấy key mới.



ĐỀ TÀI: ĐỊA ĐIỂM ĂN UỐNG
- Đăng nhập (các vai trò người quản trị, người dùng cá nhân và quản lý nhà hàng).
- Đăng ký vai trò người dùng cá nhân hoặc cửa hàng ăn uống (có avatar). Đăng ký 
người dùng là một cửa hàng cần được xác nhận của người quản trị thì tài khoản mới 
được kích hoạt.
- Người dùng các nhân được phép thực hiện tra cứu món ăn linh hoạt theo tên, giá, loại 
thức ăn và cửa hàng mong muốn.
- Người dùng được phép đặt món ăn trực tiếp qua hệ thống.
- Cho phép người dùng theo dõi cửa hàng, mỗi khi cửa hàng đăng menu thức ăn hoặc 
đăng món ăn mới thì người dùng sẽ nhận được email hoặc sms thông báo. 
- Cửa hàng được phép đăng món ăn hoặc menu thức ăn theo từng buổi, mỗi món ăn có 
thề thiết thiết lập thời điểm bán trong ngay hoặc trạng thái món ăn (còn hay hết). Cửa 
hàng được phép xác nhận đơn hàng và ghi nhận giao hàng khi nhận đơn đặt từ khách 
hàng.
- Người dùng được phép bình luận và đánh giá món ăn và các cửa hàng.
- Cửa hàng được phép xem thống kê doanh thu các sản phẩm, danh mục sản phẩm theo 
tháng, quý và năm.
- Người quản trị có thể xem thống kê tần suất bán hàng, tổng sản phẩm kinh doanh của 
các cửa hàng theo tháng, quý, năm.
