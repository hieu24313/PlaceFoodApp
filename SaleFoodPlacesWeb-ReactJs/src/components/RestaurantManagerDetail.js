import { useEffect, useState } from "react";
import { Link, useNavigate, useParams, useSearchParams } from "react-router-dom";
import Apis, { authApi, endpoints } from "../configs/Apis";
import MySpinner from "../layout/MySpinner";
import { Button, Card, Col, Image, Row } from "react-bootstrap";
import RestaurantManagerConpoment from "../layout/RestaurantManagerComponent";
import cookie from "react-cookies";
import { Bar, Chart, Line, Pie } from "react-chartjs-2";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { useContext } from "react";
import { MyUserContext } from "../App";
import { ToastContainer, toast } from "react-toastify";
import GoogleMapAPI from "./GoogleMapComponent/GoogleMapAPI";


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
            try {
                let url = `${endpoints['check-restaurant-user']}?userId=${user.userId}&restaurantId=${restaurantId}`;
                let res = await authApi().get(url);
                if (res.status === 200) {
                    // setCheck(true);
                    loadRestaurant();
                }
            } catch (e) {
                if (checkRes) {
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

                {restaurant !== null ? <>
                    <h1 className="text-center text-primary">Nhà Hàng {restaurant.restaurantName}</h1>
                    <div style={{ maxHeight: '350px', maxWidth: '500px', margin: 'auto auto', display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
                        <Image style={{ width: '100%', margin: 'auto auto', borderRadius: '5px' }} src={restaurant.avatar} alt="avatar" />
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'center', marginTop: '0px' }}>
                        <div style={{ width: '7%', display: 'flex', justifyContent: 'center', marginTop: '0px' }}>
                            <input type="file" />
                        </div>
                    </div>
                    <div>
                        <h2>Địa Chỉ: {restaurant.location}</h2>

                    </div>
                    <div>
                        <GoogleMapAPI location={restaurant.location} />
                    </div>
                </> : <></>}


            </div>
        </div>
    </>
}
export default RestaurantManagerDetail;