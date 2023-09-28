import { useEffect, useState } from 'react';
import '../resources/css/RestaurantManager.css';

import { MDBBtn, MDBCard, MDBCardBody, MDBCardImage, MDBCardText, MDBCardTitle } from 'mdb-react-ui-kit';
import { useContext } from 'react';
import { MyUserContext } from '../App';
import Apis, { endpoints } from '../configs/Apis';
import { Link, Navigate } from 'react-router-dom/dist';

const RestaurantManager = () => {
    const [pageNumber, setPageNumber] = useState(null);
    const [user,] = useContext(MyUserContext);
    const [restaurant, setRestaurant] = useState([]);
    const items = [];

    for (let i = 1; i <= pageNumber; i++) {
        items.push(<div><Link onClick={() => loadRestaurantByPageNumber(i)} key={i}>{i}</Link></div>);
    }

    const loadRestaurantByPageNumber = async (p) => {
        let form = new FormData();
        form.append("current_user_UserId", user.userId);
        let e = `${endpoints['restaurant-manager']}?`;
        if (p === "pageAll") {
            // form.delete("page");
            e += "pageAll="
            // form.append("pageAll", "1111");
        }
        else {
            e += `page=${p}`
            // form.append("page", p);
        }
        let res = await Apis.get(e, form);
        console.log(res.data)
        // setPageNumber(res.data)
        setRestaurant(res.data)
    }

    useEffect(() => {
        const loadCountRestaurant = async () => {
            let form = new FormData();
            form.append("current_user_UserId", user.userId);
            let res = await Apis.get(endpoints['count-restaurant'], form);
            // console.log(res.data)
            setPageNumber(res.data)
        }
       

        const loadRestaurant = async () => {
            let form = new FormData();
            form.append("current_user_UserId", user.userId);
            let res = await Apis.get(endpoints['restaurant-manager'], form);
            // console.log(res.data)
            // setPageNumber(res.data)
            setRestaurant(res.data)
        }
        loadCountRestaurant();
        loadRestaurant();

        //xuat restaurant len giao dien
    }, [user.userId])

    if (user === null) {
        return <Navigate to="/" />;
    }


    return <>
        <div>
            <h1 className="text-center text-primary"> Quản Lý Nhà Hàng</h1>
        </div>
        <div className="restaurants">
            {Object.values(restaurant).map(r => {
                return <div className="restaurant">
                <MDBCard>
                    <MDBCardImage src={r.avatar} position='top' alt={r.restaurantName} />
                    <MDBCardBody className="restaurant_body_card">
                        <MDBCardTitle>{r.restaurantName}</MDBCardTitle>
                        <MDBCardText>
                            {r.location}
                        </MDBCardText>
                        <MDBBtn className="btncate btncate-2 cate_res_detail btn">Quản Lý</MDBBtn>
                    </MDBCardBody>
                </MDBCard>
            </div>
            })}
            
        </div>
        <div>
            {pageNumber == null ? null :
                <>
                    <div className="pagenumber">
                        <div><Link onClick={() => loadRestaurantByPageNumber("pageAll")} >Tất Cả Nhà Hàng</Link></div>
                        {items}
                    </div>
                </>
            }
        </div>
    </>
}
export default RestaurantManager;