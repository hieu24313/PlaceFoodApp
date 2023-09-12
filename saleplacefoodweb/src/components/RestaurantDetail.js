import { useContext, useEffect, useState } from "react";
import MySpinner from "../layout/MySpinner";
import Apis, { authApi, endpoints } from "../configs/Apis";
import { MDBBreadcrumb, MDBBreadcrumbItem, MDBBtn, MDBCard, MDBCardBody, MDBCardImage, MDBCardText, MDBCol, MDBContainer, MDBIcon, MDBListGroup, MDBListGroupItem, MDBProgress, MDBProgressBar, MDBRow, MDBTypography } from "mdb-react-ui-kit";
import { Link, useParams } from "react-router-dom";
import '../resources/css/RestaurantDetails.css';
import { Alert, Button, Col, Row } from "react-bootstrap";
import { Card } from "react-bootstrap/esm";
import cookie from "react-cookies";
import { MyCartContext, MyUserContext } from "../App";
import GoogleMapReact from 'google-map-react';
import { ToastContainer, toast } from "react-toastify";

const RestaurantDetail = () => {

    const [restaurant, setRestaurant] = useState(null);
    const [foodItems, setFoodItems] = useState(null);
    const { restaurantId } = useParams();
    const [categories, setCategories] = useState();
    const [loading, setLoading] = useState(false);
    const [cart, cartDispatch] = useContext(MyCartContext);
    const [user,] = useContext(MyUserContext);
    const [checkFollow, setCheckFollow] = useState(false);
    const [loading1, setLoading1] = useState(false);
    const notify = (x) => toast(x);

    useEffect(() => {
        try {
            const loadRestaurant = async () => {
                // console.log("chà chà" + restaurantId);
                let { data } = await Apis.get(endpoints['restaurant_detail'](restaurantId));
                setRestaurant(data);
            }

            const loadFoodItems = async () => {
                let urlfooditem = `${endpoints["fooditems"]}?restaurantId=${restaurantId}`
                // form.append("restaurantId", restaurantId);
                let { data } = await Apis.get(urlfooditem);

                setFoodItems(data);
            }

            const loadCategories = async () => {
                let urlCate = `${endpoints["categories"]}?restaurantId=${restaurantId}`
                // form.append("restaurantId", restaurantId);
                let { data } = await Apis.get(urlCate);

                setCategories(data);
            }

            const loadCheckFollow = async () => {
                try {
                    // let form = new FormData();
                    // form.append("userId", user.userId);
                    // form.append("restaurantId", restaurantId);
                    console.log(user.userId)
                    let e = `${endpoints["check-follow"]}?userId=${user.userId}&restaurantId=${restaurantId}`
                    let { data } = await authApi().get(e);
                    console.log(data);
                    setCheckFollow(true);
                }
                catch (err) {
                    console.log(err);
                }
            }
            try {
                loadCheckFollow();
                loadRestaurant();
                loadCategories();
                loadFoodItems();
            } catch (err) {
                console.log(err);
            }

        } catch (ex) {
            console.log(ex)
        }


    }, [restaurantId])

    const loadFoodItems_cate = async (cateid) => {
        setLoading(true);
        let urlfooditem = `${endpoints["fooditems"]}?restaurantId=${restaurantId}`
        if (cateid > -1)
            urlfooditem += `&cateFoodId=${cateid}`
        // form.append("restaurantId", restaurantId);
        try {
            let { data } = await Apis.get(urlfooditem);
            setFoodItems(data);
            setLoading(false);
        } catch (err) {
            console.log(err)
        }



    }



    const order = (foodItem) => {
        cartDispatch({
            "type": "inc",
            "payload": 1
        })


        let cart = cookie.load("cart") || null;
        if (cart == null)
            cart = {};

        if (foodItem.foodId in cart) { // sản phẩm có trong giỏ
            cart[foodItem.foodId]["quantity"] += 1;
        } else { // sản phẩm chưa có trong giỏ
            cart[foodItem.foodId] = {
                "foodId": foodItem.foodId,
                "foodName": foodItem.foodName,
                "quantity": 1,
                "unitPrice": foodItem.price
            }
        }

        cookie.save("cart", cart);
        notify("Thêm vào giỏ hàng thành công!!!");
    }

    const follow = async () => {
        let btn = document.getElementById("btn_follow");
        btn.disabled = true;
        try {
            let form = new FormData();
            form.append("userId", user.userId);
            form.append("restaurantId", restaurantId);
            let res = await authApi().post(endpoints["follow"], form);
            // console.log(res);
            if (res.status === 201) {
                setCheckFollow(true);
                notify("Theo dõi thành công!!");
            } else {
                setCheckFollow(false);
                notify("Hủy theo dõi thành công!!");
            }
        } catch (err) {
            console.log(err);
        }
        setTimeout(() => {
            setLoading1(false);
            btn.disabled = false;
        }, 1000)

    }

    /*Google map*/

    const defaultProps = {
        center: {
            lat: 10.8166161,
            lng: 106.675992
        },
        zoom: 20
    };

    const AnyReactComponent = ({ text }) => {
        <div>{text}</div>;
    }

    const handleApiLoaded = (map, maps) => {
        // use map and maps objects
    };

    if (restaurant === null) {
        return <MySpinner />
    }


    return <>

        <section style={{ backgroundColor: '#eee' }}>
            <ToastContainer />
            <MDBContainer className="py-5">
                <MDBRow>
                    <MDBCol>
                        <h1 className="text-center text-info">Chi tiết nhà hàng</h1>
                    </MDBCol>
                </MDBRow>

                <MDBRow>
                    <MDBCol lg="4" className="">
                        <MDBCard className="mb-4 box_infor_detail_res">
                            <MDBCardBody className="text-center info_res_body_res_detail">
                                <MDBCardImage
                                    src={restaurant.avatar}
                                    alt="avatar"
                                    className="rounded-circle"
                                    style={{ width: '150px' }}
                                    fluid />
                                <p className="text-muted mb-1">{restaurant.location}</p>
                                {/* <p className="text-muted mb-4">{restaurant.location}</p> */}
                                {/* {console.log(restaurant)} */}
                                <div className="d-flex justify-content-center mb-2">
                                    {/* {loading1 === true ? <MySpinner /> : <> */}
                                    <MDBBtn id="btn_follow" onClick={follow}>{checkFollow === true ? "Hủy Theo Dõi" : "Theo Dõi"}</MDBBtn>
                                    {/* </>} */}

                                    {/* {checkFollow === false ? <MDBBtn onClick={follow}>Theo Dõi</MDBBtn> : <MDBBtn onClick={follow}>Hủy Theo Dõi</MDBBtn>} */}
                                    <MDBBtn outline className="ms-1">Nhắn Tin</MDBBtn>
                                </div>
                            </MDBCardBody>
                        </MDBCard>

                    </MDBCol>
                    <MDBCol lg="8">
                        <MDBCard className="mb-4 box_infor_detail_res">
                            <MDBCardBody className="info_res_body_res_detail">
                                <MDBRow>
                                    <MDBCol sm="3" className="name_res_detail">
                                        <MDBCardText>Tên Nhà Hàng</MDBCardText>
                                    </MDBCol>
                                    <MDBCol sm="9">
                                        <MDBCardText className="text-muted">{restaurant.restaurantName}</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                                <hr />
                                <MDBRow>
                                    <MDBCol sm="3">
                                        <MDBCardText>Email</MDBCardText>
                                    </MDBCol>
                                    <MDBCol sm="9">
                                        <MDBCardText className="text-muted">Restaurant@restaurant.com</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                                <hr />
                                <MDBRow>
                                    <MDBCol sm="3">
                                        <MDBCardText>Số điện thoại</MDBCardText>
                                    </MDBCol>
                                    <MDBCol sm="9">
                                        <MDBCardText className="text-muted">(097) 234-5678</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                                <hr />
                                <MDBRow>
                                    <MDBCol sm="3">
                                        <MDBCardText>Mobile</MDBCardText>
                                    </MDBCol>
                                    <MDBCol sm="9">
                                        <MDBCardText className="text-muted">(098) 765-4321</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                                <hr />
                                <MDBRow>
                                    <MDBCol sm="3" className="name_res_detail">
                                        <MDBCardText>Địa Chỉ</MDBCardText>
                                    </MDBCol>
                                    <MDBCol sm="9">
                                        <MDBCardText className="text-muted">{restaurant.location}</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                            </MDBCardBody>
                        </MDBCard>

                        <MDBRow>
                            <MDBCol md="6">

                            </MDBCol>
                        </MDBRow>
                    </MDBCol>
                </MDBRow>
                <div className="cate_res_detail_parent">

                    {categories === null || categories === undefined ? <MySpinner /> :
                        <>
                            {loading === true ? <MySpinner /> : <Button onClick={() => { loadFoodItems_cate(-1) }} className="btncate btncate-2 cate_res_detail">Toàn Bộ</Button>}

                            {
                                categories.map(c => {
                                    let id = `${c.categoryfoodId}`
                                    return <>
                                        {loading === true ? <MySpinner /> : <Button onClick={() => { loadFoodItems_cate(id) }} className="btncate btncate-2 cate_res_detail">{c.categoryname}</Button>}
                                    </>
                                })}
                        </>


                    }

                </div>
                <Row className="food_item_res_retail">
                    {foodItems === null ? <MySpinner /> : <>
                        {foodItems.length === 0 ? <Alert>Không có sản phẩm nào đang được bán ở đây!</Alert> : <>
                            {foodItems.map(f => {
                                let url = `/fooddetail/${f.foodId}`;
                                return <Col xs={12} md={4} >
                                    <Card className="mt-3 food_item">
                                        <Link to={url}><Card.Img variant="top" className="w-100" src={f.avatar} /></Link>
                                        <Card.Body>
                                            <div className="flex" style={{ height: 100 + "px" }}>
                                                <div className="w-70">
                                                    <Card.Title>{f.foodName}</Card.Title>
                                                </div>
                                                <div className="description" >
                                                    <Card.Text>{f.description}</Card.Text>
                                                </div>
                                            </div>
                                            <div>
                                                <Card.Text className="text-danger" style={{ fontSize: 25 + "px" }}>
                                                    {f.price} VNĐ
                                                </Card.Text>
                                            </div>
                                            <div className="btn_all">
                                                <Button onClick={() => { order(f) }} className="raise" variant="success">ADD TO CART</Button>
                                                <Link to={url} variant="primary" className="btn-food btn btn-primary raise">Xem chi tiết</Link>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                </Col>

                            })}
                        </>}

                    </>}
                </Row>
                <div>
                    <div>
                    </div>
                    <hr />
                    <div className="form_googlemap">

                        <div className="map">
                            <GoogleMapReact
                                bootstrapURLKeys={{ key: "AIzaSyDWTx7bREpM5B6JKdbzOvMW-RRlhkukmVE" }}
                                defaultCenter={defaultProps.center}
                                defaultZoom={defaultProps.zoom}
                            >
                                <AnyReactComponent
                                    lat={59.955413}
                                    lng={30.337844}
                                    text="My Marker"
                                />
                            </GoogleMapReact>
                        </div>
                        <div className="text_map">
                            <span>Nhà hàng của chúng tôi là một nơi tuyệt vời để thưởng thức ẩm thực đặc biệt. Với không gian hiện đại và tinh tế, vị trí tiện lợi tại trung tâm thành phố, cùng với thực đơn đa dạng và đội ngũ đầu bếp tài năng, chúng tôi đảm bảo mang đến cho bạn một trải nghiệm ẩm thực không thể quên. Hãy đến và tham gia cùng chúng tôi để tận hưởng những khoảnh khắc đáng nhớ bên gia đình và bạn bè.</span>
                        </div>

                        {/* <GoogleMapReact
                        
                            bootstrapURLKeys={{ key: "AIzaSyDc7PnOq3Hxzq6dxeUVaY8WGLHIePl0swY"}}
                            defaultCenter={defaultProps.center}
                            defaultZoom={defaultProps.zoom}
                            yesIWantToUseGoogleMapApiInternals
                            onGoogleApiLoaded={({ map, maps }) => handleApiLoaded(map, maps)}
                        >
                            <AnyReactComponent
                                lat={59.955413}
                                lng={30.337844}
                                text="My Marker"
                            />
                        </GoogleMapReact> */}
                    </div>
                </div>

            </MDBContainer>
        </section>

    </>
}
export default RestaurantDetail;