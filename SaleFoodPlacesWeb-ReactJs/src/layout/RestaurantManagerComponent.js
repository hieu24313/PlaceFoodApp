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
            <li><Link to={urlIndex} className="btn"><i class="fa fa-home"></i> Trang Chủ</Link></li>
            <hr />
            <li ><Link to={urlCategory} className="btn"><i class="fa-solid fa-bars"></i> Danh Mục</Link></li>
            <hr />
            <li><Link to={urlFoodItems} className="btn"><i class="fa-solid fa-utensils"></i>  Món Ăn</Link></li>
            <hr />
            <li><Link to={urlPromotion} className="btn"><i class="fa-solid fa-tags fa-flip-vertical"></i> Khuyến Mãi</Link></li>
            <hr />
            <li><Link to={urlRevenus} className="btn"><i class="fa-solid fa-sack-dollar"></i> Doanh Thu</Link></li>
            <hr />
            {/* <li ><Link to="" className="btn">Combo</Link></li>
            <hr /> */}
            <li className="dasboard_ul_lastchild"><Link to="/restaurantmanager" className="btn"> <i class="fa">&#xf190;</i> Quay Lại</Link></li>

        </ul>
    </>
}
export default RestaurantManagerConpoment;