import { Nav } from "react-bootstrap";
import { Link } from "react-router-dom";

const ProfileComponents = () => {
    return <>
        <div className="contain_info_1">
            <Nav variant="tabs" defaultActiveKey="/home">
                <Nav.Item className="nav-link text-success choose">
                    <Link to="/profile" >User Info</Link>
                </Nav.Item>
                <Nav.Item className="nav-link text-success choose">
                    <Link to="/changepassword" >Change Password</Link>
                </Nav.Item>
                <Nav.Item className="nav-link text-success choose">
                    <Link to="/receipt" >Order History</Link>
                </Nav.Item>
                <Nav.Item className="nav-link text-success choose">
                    <Link to="/register_restaurant" >Register Restaurant</Link>
                </Nav.Item>
            </Nav>
        </div>
    </>
}
export default ProfileComponents;