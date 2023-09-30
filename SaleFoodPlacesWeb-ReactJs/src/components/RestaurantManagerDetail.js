import { useEffect, useState } from "react";
import { Link, useParams, useSearchParams } from "react-router-dom";
import Apis, { authApi, endpoints } from "../configs/Apis";
import MySpinner from "../layout/MySpinner";
import '../resources/css/RestaurantManagerDetail.css';
import { Button } from "react-bootstrap";

const RestaurantManagerDetail = () => {

    // const [restaurantId, setRestaurantId] = useState();
    const { restaurantId } = useParams();
    const [restaurant, setRestaurant] = useState(null);

    // let e = document.Query.querySelector(".contain");
    // e.style.backgroundImage = restaurant.avatar

    useEffect(() => {
        const loadRestaurant = async () => {
            try {
                // console.log("chà chà" + restaurantId);
                let { data } = await Apis.get(endpoints['restaurant_detail'](restaurantId));
                setRestaurant(data);
                console.log(data)


            } catch (error) {
                console.log(error);
            }
        }
        loadRestaurant();
        // console.log(restaurant.avatar);
    }, [restaurantId])

    return <>
        <div className="dasboard_big">
            <div className="dasboard_1">
                <ul className="dasboard_ul">
                    <h3 className="dasboard_title">Quản Lý</h3>
                    <li><Link className="btn">Tổng Quát</Link></li>
                    <li><Link className="btn">Tổng Quát</Link></li>
                    <li><Link className="btn">Tổng Quát</Link></li>
                    <li><Link className="btn">Tổng Quát</Link></li>
                </ul>
            </div>
            <div className="dasboard_2">
                <h1>Tổng Quát</h1>
            </div>
        </div>
    </>
}
export default RestaurantManagerDetail;