import { useEffect, useState } from "react";
import { Link, useNavigate, useParams, useSearchParams } from "react-router-dom";
import Apis, { authApi, endpoints } from "../configs/Apis";
import MySpinner from "../layout/MySpinner";
import { Button } from "react-bootstrap";
import RestaurantManagerConpoment from "../layout/RestaurantManagerComponent";
import cookie from "react-cookies";
import { Bar, Chart, Line, Pie } from "react-chartjs-2";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { useContext } from "react";
import { MyUserContext } from "../App";
import { ToastContainer, toast } from "react-toastify";


let checkRes = true;
const RestaurantManagerDetail = () => {

    // const [restaurantId, setRestaurantId] = useState();

    const { restaurantId } = useParams();
    const [restaurant, setRestaurant] = useState(null);
    const [user,] = useContext(MyUserContext);
    const [check, setCheck] = useState(false);
    const nav = useNavigate();
    if (restaurantId !== null) {
        cookie.save("restaurant", restaurantId);
    }

    useEffect(() => {
        const checkrestaurantAndUser = async () => {
            try{
                let url = `${endpoints['check-restaurant-user']}?userId=${user.userId}&restaurantId=${restaurantId}`;
            let res = await authApi().get(url);
            if(res.status === 200){
                // setCheck(true);
                loadRestaurant();
            }
            }catch(e){
                if(checkRes){
                    toast.error(e.request.responseText);
                    checkRes = false;
                }
                setTimeout(() => nav("/"), 1500);
            }
        }
        const loadRestaurant = async () => {
                try {
                    let { data } = await Apis.get(endpoints['restaurant_detail'](restaurantId));
                    setRestaurant(data);
                    console.log(data)
    
    
                } catch (error) {
                    console.log(error);
                }
            
        }
        checkrestaurantAndUser();
        
        // console.log(restaurant.avatar);
    }, [restaurantId])
    return <>
        <div className="dasboard_big" >
        <ToastContainer />
            <div className="dasboard_1">
                <RestaurantManagerConpoment />
            </div>
            <div className="dasboard_2" >
                <h1 className="text-center">Trang chủ quản lý</h1>
            </div>
        </div>
    </>
}
export default RestaurantManagerDetail;