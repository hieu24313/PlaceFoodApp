import { Nav } from "react-bootstrap";
import { Link } from "react-router-dom";

const ProfileComponents = () => {
    return <>
        <div className="contain_info_1">
            <Nav variant="tabs" defaultActiveKey="/home">
                <Nav.Item className="nav-link text-success choose">
                    <Link to="/profile" >Thông Tin Người Dùng</Link>
                </Nav.Item>
                <Nav.Item className="nav-link text-success choose">
                    <Link to="/register_restaurant" >Đăng Ký Nhà Hàng</Link>
                </Nav.Item>
                <Nav.Item className="nav-link text-success choose">
                    <Link to="/receipt" >Lịch Sử Mua Hàng</Link>
                </Nav.Item>
                
                <Nav.Item className="nav-link text-success choose">
                    <Link to="/changepassword" >Đổi Mật Khẩu</Link>
                </Nav.Item>
            </Nav>
        </div>
    </>
}
export default ProfileComponents;