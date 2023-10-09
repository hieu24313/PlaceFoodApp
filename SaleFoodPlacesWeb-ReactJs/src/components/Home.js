import { useContext, useEffect, useState } from "react";
import { Alert, Button, Card, Carousel, Col, Form, Row } from "react-bootstrap";
import Apis, { authApi, endpoints } from "../configs/Apis";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import MySpinner from "../layout/MySpinner";
import '../resources/css/Home.css'
import { MyCartContext } from "../App";
import cookie from "react-cookies";
import { MDBInput } from "mdb-react-ui-kit";
import { ToastContainer, toast } from "react-toastify";

const Home = () => {

    const [foodItems, setFoodItems] = useState([]);
    const [, cartDispatch] = useContext(MyCartContext);
    const [q] = useSearchParams();
    const [restaurant, setRestaurant] = useState([]);

    // const currentPage = useLocation();
    const [kw, setKw] = useState("");
    const [fromPrice, setFromPrice] = useState("");
    const [toPrice, setToPrice] = useState("");
    const [page, setPage] = useState("");
    const nav = useNavigate();
    const notify = (x) => toast(x);
    const [promo_Food, setPromo_Food] = useState([]);
    const [pageLoad, setPageLoad] = useState(false);
    // const []


    const search = (evt) => {
        evt.preventDefault();
        let find = "/?"
        if (kw !== "") {
            find += `kw=${kw}&`
        }
        if (fromPrice !== "") {
            find += `formPrice=${fromPrice}&`
        }
        if (toPrice !== "") {
            find += `toPrice=${toPrice}`
        }
        // if (page !== "") {
        //     find += `page=${page}`
        // }
        nav(find)
    }



    useEffect(() => {
        // const newPrice = (data) => {

        // }

        const loadFood_Promotion = async () => {
            try {
                let res = await Apis.get(endpoints['fooditems-promotion']);
                console.log(res.data[0][2]); //lấy được id của food khuyến mãi
                setPromo_Food(res.data);
                console.log(res.data)
                console.log("ở đây ")
            } catch (e) {
                console.error(e);
            }
        }

        const loadFoodItems = async () => {
            try {
                let e = `${endpoints['fooditems']}?`;
                let nameFoodItem = q.get("kw");
                let fromPriceFood = q.get("formPrice")
                let toPriceFood = q.get("toPrice")
                // let page = q.get("page")

                if (nameFoodItem !== null) {
                    e += `kw=${nameFoodItem}&`;
                }
                if (fromPriceFood !== null) {
                    e += `formPrice=${fromPriceFood}&`;
                }
                if (toPriceFood !== null) {
                    e += `toPrice=${toPriceFood}&`;
                }
                // if (page !== null) {
                //     e += `page=${page}`;
                // }

                let res = await Apis.get(e);
                setFoodItems(res.data);
                let data = res.data;
                data.forEach(item => {
                    item.hasPromotion = false; // Thêm thuộc tính mới với giá trị mặc định
                    item.oldPrice = 1;
                });
                console.log(data)
                if (promo_Food.length > 0) {
                    for (let pf of promo_Food) {
                        // console.log(1)
                        let id = pf[1]['foodId']['foodId'];
                        // console.log(id)
                        const foundFood = data.findIndex(item => item.foodId === id);
                        if (foundFood !== -1) {
                            // console.log(2)
                            let type = pf[2].promotionTypeId.promotionTypeId; //loại khuyến mãi
                            let price = data[foundFood].price; // giá ban đầu
                            // console.log(pf[2].pricePromotion);
                            if (type === 1) { // giảm theo %
                                // console.log(21)
                                if (price - (pf[2].pricePromotion * price / 100) >= 0) {
                                    data[foundFood].price = price - (pf[2].pricePromotion * price / 100);
                                } else {
                                    data[foundFood].price = 0;
                                }

                                data[foundFood].hasPromotion = true;
                                if (data[foundFood].oldPrice === 1) {
                                    data[foundFood].oldPrice = price;
                                }
                            }
                            else if (type === 2) { //giảm theo giá tiền
                                // console.log(22)
                                if (price - pf[2].pricePromotion >= 0) {
                                    data[foundFood].price = price - pf[2].pricePromotion;
                                } else {
                                    data[foundFood].price = 0;
                                }

                                data[foundFood].hasPromotion = true;
                                if (data[foundFood].oldPrice === 1) {
                                    data[foundFood].oldPrice = price;
                                }
                            }
                        }
                    }
                }

                console.log("xong for?")
                console.log(data)
                setFoodItems([]);
                setFoodItems(data);
                console.log(res.data)
            } catch (ex) {
                console.error(ex);
            }
        }

        const loadRestaurant = async () => {
            try {
                let e = `${endpoints['restaurant']}?`;
                let restaurantName = q.get("kw");
                // let location = q.get("location");
                if (page !== null) {
                    e += `page=${page}&`;
                }
                if (restaurantName !== null) {
                    e += `restaurantName=${restaurantName}&`;
                }
                // if (location !== null) {
                //     e += `location=${location}`;
                // }
                let res = await Apis.get(e);

                setRestaurant(res.data);
                // console.log(res.data)
            } catch (ex) {
                console.error(ex);
            }
        }

        loadFood_Promotion();
        loadFoodItems();
        loadRestaurant();
        // setTimeout(() => newPrice(foodItems), 1500);
        // console.log(1)
    }, [pageLoad])

    setTimeout(() => setPageLoad(true), 1500)

    // const hasAvatar = promo_Food[0][1].hasOwnProperty('foodId');
    // console.log("kiểm tra " + hasAvatar); // true

    if (foodItems === null) {
        return <div style={{ marginLeft: 50 + "%", marginTop: 20 + "%" }}><MySpinner style={{ marginLeft: 50 + "%", marginTop: 20 + "%" }} /></div>

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
        toast.success("Thêm vào giỏ hàng thành công!!!");
    }
    // if (foodItems.length === 0 && restaurant.length)
    //     return <Alert variant="info" className="mt-2">Không có sản phẩm nào!</Alert>

    return <>
        <div>
            <div>
                <ToastContainer />
                <Form onSubmit={search} className="mt-3 mb-2 form_find_name" inline>
                    <Row className="find_first">
                        <Col xs="auto" className="input1">
                            <MDBInput
                                type="text"
                                value={kw}
                                onChange={e => setKw(e.target.value)}
                                label="Tên món ăn, nhà hàng" name="kw"
                                className="f mr-sm-2 btn-find-header f mr-sm-2 input_find"
                            />
                        </Col>
                    </Row>
                </Form>
            </div>
            <div className="find_carousel">
                <Carousel data-bs-theme="dark" className="carousel_edit">
                    {/* {restaurant === null ? <MySpinner /> : <> */}
                    {Object.values(restaurant).map(r => {
                        let url = `/restaurant_detail/${r.restaurantId}`
                        return <Carousel.Item className="carosel_item">
                            <h1 className="text-center text-danger">{r.restaurantName}</h1>
                            <Link to={url}>
                                <img
                                style={{borderRadius: '5px 5px 5px 5px'}}
                                    className="carol_img d-block w-100"
                                    src={r.avatar}
                                    alt={r.restaurantName}
                                /></Link>
                            <Carousel.Caption className="caption_res">
                                <div className="carousel_info_home">
                                    <h5>{r.restaurantName}</h5>
                                    <p>{r.location}</p>
                                </div>
                            </Carousel.Caption>
                        </Carousel.Item>
                    })}
                    {/* </>} */}


                </Carousel>
            </div>
        </div>
        <div >
            {/* <h5>Tìm Kiếm</h5> */}
            <Form onSubmit={search}>
                <div className="form_timkiem">
                    <div className="form_input_find_all">

                        {/* <Form.Control type="text" value={kw} onChange={e => setKw(e.target.value)} label="Nhập ten mon an..." name="kw" className="f mr-sm-2" /> */}
                        <MDBInput className="f mr-sm-2 input_find" wrapperClass='mb-4' value={kw} onChange={e => setKw(e.target.value)} label='Nhập tên món ăn hoặc nhà hàng' name="kw" size='lg' id='form3' type='text' />
                        {/* <Form.Control type="number" value={fromPrice} onChange={e => setFromPrice(e.target.value)} placeholder="Nhập giá từ..." name="fromPrice" className="f mr-sm-2" /> */}
                        <MDBInput className="f mr-sm-2 input_find" wrapperClass='mb-4' value={fromPrice} onChange={e => setFromPrice(e.target.value)} label='Nhập giá từ' name="fromPrice" size='lg' id='form3' type='number' />
                        {/* <Form.Control type="number" value={toPrice} onChange={e => setToPrice(e.target.value)} placeholder="Nhập nhập giá tối đa..." name="toPrice" className="f mr-sm-2" /> */}
                        <MDBInput className="f mr-sm-2 input_find" wrapperClass='mb-4' value={toPrice} onChange={e => setToPrice(e.target.value)} label='Nhập nhập giá tối đa' size='lg' name="toPrice" id='form3' type='number' />
                        <Button className="btn-find raise" type="submit">Tìm</Button>
                    </div>
                </div>
            </Form>
        </div>
        <div className="home">

            {/* <div className="find">

                <Form onSubmit={search} className="mt-3 mb-2 form form_find_all" inline>
                    <Row>

                    </Row>
                </Form>
            </div> */}
            <div className="fooditems">
                <Row>
                    {foodItems.length === 0 ? <Alert variant="info" className="mt-2">Không có sản phẩm nào!</Alert> : <>
                        {foodItems === null ? <div style={{ marginLeft: 50 + "%", marginTop: 20 + "%" }}><MySpinner style={{ marginLeft: 50 + "%", marginTop: 20 + "%" }} /></div> : <>
                            {foodItems.map(f => {
                                let url = `/fooddetail/${f.foodId}`;
                                let id = `price${f.foodId}`
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
                                                {f.hasPromotion === true ? <>
                                                    <sub style={{ color: 'red', fontSize: 18 + 'px' }}><s>{f.oldPrice} VNĐ </s></sub>
                                                    <Card.Text id={id} className="text-danger" style={{ fontSize: 25 + "px" }}>

                                                        Giảm giá: {f.price} VNĐ
                                                    </Card.Text>

                                                </> : <>
                                                    <Card.Text id={id} className="text-danger" style={{ fontSize: 25 + "px" }}>
                                                        {f.price} VNĐ
                                                    </Card.Text>
                                                </>}

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
            </div>
        </div>
    </>
}
export default Home;