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
import backgroundResmanager from '../resources/img/vegetables-set-left-black-slate.jpg'


let checkRes = true;
const RestaurantManagerDetail = () => {

    // const [restaurantId, setRestaurantId] = useState();

    const { restaurantId } = useParams();
    const [restaurant, setRestaurant] = useState(null);
    const [user,] = useContext(MyUserContext);
    const [check, setCheck] = useState(false);
    const nav = useNavigate();
    const [statsRevenue, setStatsRevenue] = useState({ labels: [], data: [] });
    if (restaurantId !== null) {
        cookie.save("restaurant", restaurantId);
    }

    useEffect(() => {
        function daysInMonth(month, year) {
            return new Date(year, month, 0).getDate();
        }
        const currentDate = new Date();

        // Lấy năm hiện tại
        const currentYear = currentDate.getFullYear();

        // Lấy tháng hiện tại (từ 0 đến 11, nên bạn có thể cộng thêm 1 để có giá trị tháng từ 1 đến 12)
        const currentMonth = currentDate.getMonth() + 1;
        const countDateInMonth = daysInMonth(currentMonth, currentYear);
        let fromDate = `${currentYear}-${currentMonth}-1`
        let toDate = `${currentYear}-${currentMonth}-${countDateInMonth}`
        // console.log(fromDate, toDate);
        const loadStatsRevenue = async () => {
            try {
                console.log(restaurantId);
                let e = `${endpoints['get-stats-revenue']}?restaurantId=${restaurantId}&fromDate=${fromDate}&toDate=${toDate}`;
                let res = await authApi().get(e);
                console.log(e)

                const statsData = res.data;
                const labels = statsData.map((stat) => stat[1]);
                const data = statsData.map((stat) => stat[2]);

                setStatsRevenue({ labels, data });
                console.log(statsData);
            } catch (e) {
                console.log(e);
            }
        };
        loadStatsRevenue();
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

    const data1 = {
        labels: statsRevenue.labels,
        datasets: [
            {
                label: "Doanh thu (VND)",
                data: statsRevenue.data,
                backgroundColor: [
                    "red",
                    "blue",
                    "yellow",
                    "green",
                    "purple",
                    "orange",
                ],
            },
        ],
    };

    return <>
        <div className="dasboard_big" >
            <ToastContainer />
            <div className="dasboard_1">
                <RestaurantManagerConpoment />
            </div>
            <div className="dasboard_2"  >

                {restaurant !== null ? <>
                    <h1 className="text-center text-primary mt-2">Nhà Hàng {restaurant.restaurantName}</h1>
                    {/* <div className="mt-5" style={{ maxHeight: '350px', maxWidth: '500px', margin: 'auto auto', display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
                        <Image style={{ width: '100%', margin: 'auto auto', borderRadius: '5px' }} src={restaurant.avatar} alt="avatar" />
                    </div> */}
                    <div className="mt-4" style={{ width: '50%', margin: 'auto' }}>
                        
                        <Bar data={data1} />
                        <h5 className="text-center">Doanh Thu Trong Tháng Này</h5>
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'center', marginTop: '0px' }}>
                        <div style={{ width: '7%', display: 'flex', justifyContent: 'center', marginTop: '0px' }}>
                            {/* <input type="file" /> */}
                        </div>
                    </div>
                    {/* <div style={{ marginTop: '15px' }}>
                        <h5 className="text-center"><i className="fa-solid fa-location-dot"></i> Địa Chỉ: {restaurant.location}</h5>
                    </div> */}
                    <div>
                        {/* <GoogleMapAPI location={restaurant.location} /> */}
                    </div>
                </> : <></>}


            </div>
        </div>
    </>
}
export default RestaurantManagerDetail;