import { Nav } from "react-bootstrap";
import { Link } from "react-router-dom";
import { MyUserContext } from "../App";
import { useContext } from "react";

const ProfileComponents = () => {

    const [user,] = useContext(MyUserContext);
    console.log(user)
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

                {user.roleId.roleId === 2 ? <>
                    <Nav.Item className="nav-link text-success choose">
                    <Link to="/restaurantmanager" >Quản Lý Nhà Hàng</Link>
                    </Nav.Item>
                </> : null}
            </Nav>
        </div>
    </>
}
export default ProfileComponents;