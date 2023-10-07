import { Link } from "react-router-dom";
import cookie from "react-cookies";
import '../resources/css/RestaurantManagerComponent.css';

const RestaurantManagerConpoment = () => {

    const receivedValue = cookie.load("restaurant"); //restaurantId
    let urlIndex = `/restaurantManagerDetail/${receivedValue}`
    let urlFoodItems = `/food_manager/${receivedValue}`
    let urlPromotion = `/promotion/${receivedValue}`
    let urlRevenus = `/revenus/${receivedValue}`
    let urlCategory = `/category/${receivedValue}`

    return <>
        <ul className="dasboard_ul" >
            <h3 className="dasboard_title">Quản Lý</h3>
            <li><Link to={urlIndex} className="btn">Trang Chủ</Link></li>
            <hr />
            <li ><Link to={urlCategory} className="btn">Danh Mục</Link></li>
            <hr />
            <li><Link to={urlFoodItems} className="btn">Món Ăn</Link></li>
            <hr />
            <li><Link to={urlPromotion} className="btn">Khuyễn Mãi</Link></li>
            <hr />
            <li><Link to={urlRevenus} className="btn">Doanh Thu</Link></li>
            <hr />
            <li ><Link to="" className="btn">Combo</Link></li>
            <hr />
            <li className="dasboard_ul_lastchild"><Link to="/restaurantmanager" className="btn">Quay Lại</Link></li>

        </ul>
    </>
}
export default RestaurantManagerConpoment;