import { useEffect, useState } from "react";
import MySpinner from "../layout/MySpinner";
import Apis, { endpoints } from "../configs/Apis";
import { MDBBreadcrumb, MDBBreadcrumbItem, MDBBtn, MDBCard, MDBCardBody, MDBCardImage, MDBCardText, MDBCol, MDBContainer, MDBIcon, MDBListGroup, MDBListGroupItem, MDBProgress, MDBProgressBar, MDBRow, MDBTypography } from "mdb-react-ui-kit";
import { Link, useParams } from "react-router-dom";
import '../resources/css/RestaurantDetails.css';
import { Alert, Button, Col, Row } from "react-bootstrap";
import { Card } from "react-bootstrap/esm";

const RestaurantDetail = () => {

    const [restaurant, setRestaurant] = useState(null);
    const [foodItems, setFoodItems] = useState(null);
    const { restaurantId } = useParams();
    const [categories, setCategories] = useState();
    const [loading, setLoading] = useState(false);

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

            loadRestaurant();
            loadCategories();
            loadFoodItems();

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
        let { data } = await Apis.get(urlfooditem);

        setFoodItems(data);
        setLoading(false);
    }

    const order = (food) => {

    }

    if (restaurant === null) {
        return <MySpinner />
    }


    return <>

        {/* {Object.values(restaurant).map(r => {
        return 
    })} */}

        <section style={{ backgroundColor: '#eee' }}>
            <MDBContainer className="py-5">
                <MDBRow>
                    <MDBCol>
                        <h1 className="text-center text-info">Chi tiết nhà hàng</h1>
                        {/* <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4">
              <MDBBreadcrumbItem>
                <a href='#'>Home</a>
              </MDBBreadcrumbItem>
              <MDBBreadcrumbItem>
                <a href="#">User</a>
              </MDBBreadcrumbItem>
              <MDBBreadcrumbItem active>User Profile</MDBBreadcrumbItem>
            </MDBBreadcrumb> */}
                    </MDBCol>
                </MDBRow>

                <MDBRow>
                    <MDBCol lg="4">
                        <MDBCard className="mb-4">
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
                                    <MDBBtn>Theo Dõi</MDBBtn>
                                    <MDBBtn outline className="ms-1">Nhắn Tin</MDBBtn>
                                </div>
                            </MDBCardBody>
                        </MDBCard>

                        {/* <MDBCard className="mb-4 mb-lg-0">
              <MDBCardBody className="p-0">
                <MDBListGroup flush className="rounded-3">
                  <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                    <MDBIcon fas icon="globe fa-lg text-warning" />
                    <MDBCardText>https://restaurant.restaurant</MDBCardText>
                  </MDBListGroupItem>
                  {/* <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                    <MDBIcon fab icon="github fa-lg" style={{ color: '#333333' }} />
                    <MDBCardText>mdbootstrap</MDBCardText>
                  </MDBListGroupItem> 
                  <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                    <MDBIcon fab icon="twitter fa-lg" style={{ color: '#55acee' }} />
                    <MDBCardText>@{restaurant.restaurantName}</MDBCardText>
                  </MDBListGroupItem>
                  <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                    <MDBIcon fab icon="instagram fa-lg" style={{ color: '#ac2bac' }} />
                    <MDBCardText>{restaurant.restaurantName}</MDBCardText>
                  </MDBListGroupItem>
                  <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                    <MDBIcon fab icon="facebook fa-lg" style={{ color: '#3b5998' }} />
                    <MDBCardText>{restaurant.restaurantName}</MDBCardText>
                  </MDBListGroupItem>
                </MDBListGroup>
              </MDBCardBody>
            </MDBCard> */}
                    </MDBCol>
                    <MDBCol lg="8">
                        <MDBCard className="mb-4">
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
                                {/* <MDBCard className="mb-4 mb-md-0">
                  <MDBCardBody className="info_res_body_res_detail">
                    <MDBCardText className="mb-4">Món ăn nổi bật</MDBCardText>
                    <MDBCardText className="mb-1" style={{ fontSize: '.77rem' }}>Web Design</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={80} valuemin={0} valuemax={100} />
                    </MDBProgress>

                    <MDBCardText className="mt-4 mb-1" style={{ fontSize: '.77rem' }}>Website Markup</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={72} valuemin={0} valuemax={100} />
                    </MDBProgress>

                    <MDBCardText className="mt-4 mb-1" style={{ fontSize: '.77rem' }}>One Page</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={89} valuemin={0} valuemax={100} />
                    </MDBProgress>

                    <MDBCardText className="mt-4 mb-1" style={{ fontSize: '.77rem' }}>Mobile Template</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={55} valuemin={0} valuemax={100} />
                    </MDBProgress>

                    <MDBCardText className="mt-4 mb-1" style={{ fontSize: '.77rem' }}>Backend API</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={66} valuemin={0} valuemax={100} />
                    </MDBProgress>
                  </MDBCardBody>
                </MDBCard> */}
                            </MDBCol>


                            {/* <MDBCol md="6">
                <MDBCard className="mb-4 mb-md-0">
                  <MDBCardBody>
                    <MDBCardText className="mb-4"><span className="text-primary font-italic me-1">assigment</span> Project Status</MDBCardText>
                    <MDBCardText className="mb-1" style={{ fontSize: '.77rem' }}>Web Design</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={80} valuemin={0} valuemax={100} />
                    </MDBProgress>

                    <MDBCardText className="mt-4 mb-1" style={{ fontSize: '.77rem' }}>Website Markup</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={72} valuemin={0} valuemax={100} />
                    </MDBProgress>

                    <MDBCardText className="mt-4 mb-1" style={{ fontSize: '.77rem' }}>One Page</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={89} valuemin={0} valuemax={100} />
                    </MDBProgress>

                    <MDBCardText className="mt-4 mb-1" style={{ fontSize: '.77rem' }}>Mobile Template</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={55} valuemin={0} valuemax={100} />
                    </MDBProgress>

                    <MDBCardText className="mt-4 mb-1" style={{ fontSize: '.77rem' }}>Backend API</MDBCardText>
                    <MDBProgress className="rounded">
                      <MDBProgressBar width={66} valuemin={0} valuemax={100} />
                    </MDBProgress>
                  </MDBCardBody>
                </MDBCard>
              </MDBCol> */}
                        </MDBRow>
                    </MDBCol>
                </MDBRow>
                {/* <div className="body_fooditem_res_detai"> */}
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
                                    // 


                                    //  <a href="/" class="cate_res_detail btn btn-2">Hover</a>
                                })}
                        </>


                    }

                </div>
                <Row className="food_item_res_retail">
                    {foodItems === null ? <MySpinner /> : <>
                        {foodItems.length === 0 ? <Alert>Không có sản phẩm nào đang được bán ở đây!</Alert> : <>
                            {foodItems.map(f => {
                                let url = `/fooddetail/${f.foodId}`;
                                return <Col xs={12} md={3} className="m-3 mt-2 mb-2">
                                    <Card className="mt-3" style={{ width: '18rem' }}>
                                        <Link to={url}><Card.Img variant="top" src={f.avatar} /></Link>
                                        <Card.Body>
                                            <div className="flex" >
                                                <div>
                                                    <Card.Title>{f.foodName}</Card.Title>
                                                    <Card.Text>
                                                        {f.price} VNĐ
                                                    </Card.Text>
                                                </div>
                                                <div className="description" >
                                                    <Card.Text>{f.description}</Card.Text>
                                                </div>
                                            </div>
                                            <div className="btn_all">
                                                <Button onClick={() => { order(f) }} variant="success">ADD TO CART</Button>
                                                <Link to={url} variant="primary" className="btn-food btn btn-primary">Xem chi tiết</Link>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                </Col>

                            })}
                        </>}

                    </>}
                </Row>
                {/* </div> */}
            </MDBContainer>
        </section>
    </>
}
export default RestaurantDetail;